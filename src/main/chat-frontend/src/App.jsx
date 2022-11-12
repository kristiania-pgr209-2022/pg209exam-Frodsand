import reactLogo from './assets/react.svg'
import * as React from "react";
import './App.css'
import {useEffect, useState} from "react";



function MessageList(){
    const [isLoading, setIsLoading] = useState(true);
    const [users, setUsers] = useState([]);


    useEffect(() => {
        (async () => {
            const response = await fetch("/api/chat");
            if (response.ok){
                setUsers(await response.json());
                setIsLoading(false);
            }
        })()
    }, []);

    if (isLoading){
        return <div>Loading...</div>
    }

    function handleOnClick(){

    }

    return users.map(u => <button onClick={handleOnClick}>u.name</button>)

}

function SendMessage(){

    const [subject, setSubject] = useState("");
    const [messageBody, setMessageBody] = useState("");

    async function handleOnSubmit(e){
        e.preventDefault();
        await fetch("/api/chat", {
            method: "post",
            body: JSON.stringify({subject, messageBody}),
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


function App() {
  return (
    <>
        <div className={"app"}>
            <h1>
                Messages
            </h1>
            <MessageList/>
            <SendMessage/>
        </div>
    </>
  )
}

export default App
