import * as React from "react";
import './App.css'
import {useEffect, useState} from "react";


function MessagesReceivedList({activeUser}){
    const [messages, setMessages] = useState([]);

    useEffect(() => {
        (async () => {
            if(activeUser != null){
                const response = await fetch(`/api/chat/received/${activeUser.id}`);
                if (response.ok){
                    setMessages(await response.json());
                }
                else {
                    console.log("error - useEffect in MessagesReceivedList");
                }
            }
        })()
    }, [activeUser]);

    if(messages != null){
        return (
            <div>
                <h4>Messages received: </h4>
                {
                    messages.map(m =>
                        <div>
                            <div>To: {activeUser.username} - {activeUser.emailAddress}</div>
                            <div>Subject: {m.subject}</div>
                            <div>{m.messageBody} </div>
                            <div>From: <GetSender messageId={m.id}/></div>
                            --
                        </div>
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

function GetSender({messageId}){

    const [sender, setSender] = useState({});

    useEffect(() => {
        (async () => {
                const response = await fetch(`/api/chat/sender/${messageId}`);
                if (response.ok){
                    setSender(await response.json());
                }
                else {
                    console.log("error - useEffect in get sender");
                }

        })()
    }, [messageId]);

    return(
        <div>{sender.username} - {sender.emailAddress}</div>
    )

}

function GetReceiver({messageId}){

    const [receiver, setReceiver] = useState({});

    useEffect(() => {
        (async () => {
            const response = await fetch(`/api/chat/receiver/${messageId}`);
            if (response.ok){
                setReceiver(await response.json());
            }
            else {
                console.log("error - useEffect in get receiver");
            }

        })()
    }, [messageId]);

    return(
        <div>{receiver.username} - {receiver.emailAddress}</div>
    )

}

function MessagesSentList({activeUser}){
    const [messages, setMessages] = useState([]);

    useEffect(() => {
        (async () => {
            if(activeUser != null){
                const response = await fetch(`/api/chat/sent/${activeUser.id}`);
                if (response.ok){
                    setMessages(await response.json());
                }
                else {
                    console.log("error - useEffect in MessagesSentList");
                }
            }
        })()
    }, [activeUser]);


    if(messages != null){
        return (
            <div>
                <h4>Messages Sent: </h4>

                {
                    messages.map(m =>
                        <div>
                            <div>to: <GetReceiver messageId={m.id}/></div>
                            <div>Subject: {m.subject}</div>
                            <div>{m.messageBody} </div>
                            <div>From: {activeUser.username} - {activeUser.emailAddress}</div>
                            --
                        </div>

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

function UserList({setActiveUser, users, setUsers}){
    const [isLoading, setIsLoading] = useState(true);

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
        setActiveUser(input);
    }

    return  (
        <div>
            Sender:
            {
                users.map(u => <button value={u} onClick={(e) => handleOnClick(u)}>{u.username}</button>)
            }
        </div>

    )
}

function ReceiverList({users, setReceiver, setUsers}){
    const [isLoading, setIsLoading] = useState(true);

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
        setReceiver(input);
    }

    return  (
        <div>
            Receiver:
            {
                users.map(u => <button value={u} onClick={(e) => handleOnClick(u)}>{u.username}</button>)
            }
        </div>

    )
}

function SendMessage({activeUser, receiver}){

    const [subject, setSubject] = useState("");
    const [messageBody, setMessageBody] = useState("");

    async function handleOnSubmit(e){
        e.preventDefault();

        const inputMessage = {
            senderId: activeUser.id,
            receiverId: receiver.id,
            message: {
                subject: subject,
                messageBody: messageBody
            }
        }

            await fetch("/api/chat/messages", {
                method: "post",
                body: JSON.stringify(inputMessage),
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

function ActiveUserTitle({activeUser}){

    if(activeUser != null){
        return (
            <h3>
                Active user: {activeUser.username}
            </h3>
        )
    }

    return(
        <h3>
            Active user:
        </h3>
    )
}

function ActiveReceiverTitle({receiver}){

    if(receiver != null){
        return (
            <h3>
                You are now sending a message to: {receiver.username}
            </h3>
        )
    }

    return(
        <h3>
            You are now sending a message to:
        </h3>
    )
}

function App() {
    const [activeUser, setActiveUser] = useState(null);
    const [users, setUsers] = useState([]);
    const [activeReceiver, setActiveReceiver] = useState(null);

    return (
    <>
        <div className={"app"}>
            <h1>
                Messages
            </h1>
            <ActiveUserTitle activeUser={activeUser}/>
            <ActiveReceiverTitle receiver={activeReceiver}/>
            <UserList users={users} setUsers={setUsers} setActiveUser={setActiveUser}/>
            <ReceiverList users={users} setUsers={setUsers} setReceiver={setActiveReceiver}/>
            <SendMessage receiver={activeReceiver} activeUser={activeUser}/>
            <MessagesReceivedList activeUser={activeUser}/>
            <MessagesSentList activeUser={activeUser}/>
        </div>
    </>
  )
}

export default App
