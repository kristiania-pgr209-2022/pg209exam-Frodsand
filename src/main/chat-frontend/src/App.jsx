import * as React from "react";
import './App.css'
import {useEffect, useState} from "react";


function ChatList({activeUser}){
    const [messages, setMessages] = useState([]);

    useEffect(() => {
        (async () => {
            if(activeUser != null){
                const response = await fetch(`/api/chat/${activeUser.id}`);
                if (response.ok){
                    setMessages(await response.json());
                }
                else {
                    console.log("error - useeffect in chatlist");
                }
            }
        })()
    }, [activeUser]);


    if(messages != null){
        return (
            <div>
                Latest message:
                {
                    messages.map(m =>
                        <div>{m.subject}</div>
                    )
                }

            </div>
        )
    } else{
        console.log("error - messages is null");
    }

    return(
        <div>
            no messages
        </div>
    )
}

function UserList({setActiveUser}){
    const [isLoading, setIsLoading] = useState(true);
    const [users, setUsers] = useState([]);

    useEffect(() => {
        (async () => {
                const response = await fetch("/api/chat/user");
                if (response.ok){
                    setUsers(await response.json());
                    setIsLoading(false);
                }
        })()
    }, []);

    if (isLoading){
        return <div>Loading...</div>
    }

    function handleOnClick(input){
        console.log("hallo hallo");
        setActiveUser(input);
    }

    return users.map(u => <button value={u} onClick={(e) => handleOnClick(u)}>{u.username}</button>)
}

function SendMessage({activeUser}){

    const senderId = activeUser.id;

    const [receiverId, setReceiverId] = useState("");
    const [subject, setSubject] = useState("");
    const [messageBody, setMessageBody] = useState("");

    async function handleOnSubmit(e){
        e.preventDefault();
        await fetch("/api/chat/messages", {
            method: "post",
            body: JSON.stringify({senderId, receiverId, subject, messageBody}),
            headers: {
                "Content-Type": "application/json"
            },
        });
    }


    return(
        <>
            <form onSubmit={handleOnSubmit}>
                <div>
                    <label>
                        TO: {" "}
                        <input
                            type={""}
                            value={receiverId}
                            onChange={(e) => setReceiverId(e.target.value)}
                        />


                    </label>
                </div>
                <div>
                    <label>
                        Subject: {" "}
                        <input
                            type={"text"}
                            value={subject}
                            onChange={(e) => setSubject(e.target.value)}
                        />
                    </label>
                </div>
                <div>
                    <label>
                        Body: {" "}
                        <input
                            type={"text"}
                            value={messageBody}
                            onChange={(e) => setMessageBody(e.target.value)}
                        />
                    </label>
                </div>
                <div>
                    <button>Submit</button>
                </div>
            </form>
        </>
    )
}



function App() {
    const [activeUser, setActiveUser] = useState(null);
    console.log("active user", activeUser);

    return (
    <>
        <div className={"app"}>
            <h1>
                Messages
            </h1>
            <UserList activeUser={activeUser} setActiveUser={setActiveUser}/>
            <SendMessage/>
            <ChatList activeUser={activeUser}/>
        </div>
    </>
  )
}

export default App
