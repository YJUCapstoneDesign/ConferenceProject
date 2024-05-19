import React, { useState, useEffect, useRef } from "react";
import "./chatting.css"

const Chatting = (props) => {
  const [chatData, setChatData] = useState([]);
  const [inputChat, setInputChat] = useState("");
  const [seletecedFile, setSeletecedFile] = useState(null);

  const handleChange = (e) => {
    setInputChat(e.target.value);
  };

  // 일단 파일 선택시 Enter치면 파일 전송, 아니면 채팅 전송으로 만듬
  const activeEnter = (e) => {
    if (e.key === "Enter") {
      if (seletecedFile) {
        handleFileTransfer();
      } else {
        handleClick();
      }
    }
  };


  const saychat = (e) => {
    fetch('http://localhost:8080/api', { 
      method: 'POST',
      headers: {
        'Content-Type': 'text/plain',
      },
      body: inputChat,
    })
    .then((response) => response.text())
    .then((data) => {
      console.log(data); // 응답 데이터 처리
    })
    .catch((error) => {
      console.error('Error:', error);
    });
  };
  

  const handleClick = () => {
    props.sendChatData(inputChat);
    setChatData((prev) => [...prev, `나 : ${inputChat}`]);
    setInputChat("");
  };

  useEffect(() => {
    setChatData((prev) => [...prev, props.receiveChat]);
  }, [props.receiveChat]);

  const handleFileTransfer = () => {
    if (!seletecedFile) {
      alert("파일을 선택해주세요");
      return;
    }
    const file = seletecedFile;
    const chunkLength = 16384;

    const onReadAsDataURL = (event, text) => {
      var data = {}; // data object to transmit over data channel
      data.filename = file.name;
      if (event) text = event.target.result; // on first invocation

      if (text.length > chunkLength) {
        data.message = text.slice(0, chunkLength); // getting chunk using predefined chunk length
      } else {
        data.message = text;
        data.last = true;
      }
      props.transferFile(data); // use JSON.stringify for chrome!

      var remainingDataURL = text.slice(data.message.length);
      if (remainingDataURL.length)
        setTimeout(function () {
          onReadAsDataURL(null, remainingDataURL); // continue transmitting
        }, 500);
    };

    let fileReader = new FileReader();
    fileReader.readAsDataURL(file);
    fileReader.addEventListener("load", onReadAsDataURL);
  };

  const createFileChat = (data, filename, from) => {
    return (
      <>
        {from} :{" "}
        <a href={data} download={filename}>
          {filename}
        </a>
      </>
    );
  };

  useEffect(() => {
    if (!props.receiveFile) return;
    let filename = props.receiveFile.filename;
    let data = props.receiveFile.data;
    let from = props.receiveFile.from;
    console.log(props.receiveFile);
    setChatData((prev) => [...prev, createFileChat(data, filename, from)]);
  }, [props.receiveFile]);

  const handleSelectedFile = (e) => {
    setSeletecedFile(() => e.target.files[0]);
  };

  const renderChatData = chatData.map((c, i) => {
    // 조건문 or ?로 나와 다른 사람의 채팅을 구분해야함.
    return <p key={i} className=""> {c} </p>;
  });

  return (
    <>
    <div className="transform scale-0 group-hover:scale-100 absolute 
                  transition duration-500 ease-in-out origin-bottom min-w-10 new_chat">
      <div className="w-80 rounded-[7px] border bg-white border-blue-gray-200 chat_content">
        {renderChatData}  
      </div>
      {/*채팅 input*/}
      <div className="relative flex h-10 w-full min-w-[200px] max-w-[24rem]">
      {/* <button onClick={() => {
          handleClick();
        }} className="!absolute right-1 top-1 z-10 select-none rounded bg-pink-500 py-2 px-4 text-center align-middle font-sans text-xs font-bold uppercase text-white shadow-md shadow-pink-500/20 transition-all hover:shadow-lg hover:shadow-pink-500/40 focus:opacity-[0.85] focus:shadow-none active:opacity-[0.85] active:shadow-none peer-placeholder-shown:pointer-events-none peer-placeholder-shown:bg-blue-gray-500 peer-placeholder-shown:opacity-50 peer-placeholder-shown:shadow-none" data-ripple-light="true">Enter
        </button>  */}
      <label htmlFor="file" className="!absolute top-2 left-3 w-4 h-4 bg-white border-gray-300 rounded-xl flex font-normal text-center justify-center cursor-pointer">
        <div className="btn-upload">
          {/* 아이콘 수정 */}
        <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" style={{ fill: 'rgba(0, 0, 0, 1)' }}>
        <path d="M12 2C6.486 2 2 6.486 2 12s4.486 10 10 10 10-4.486 10-10S17.514 2 12 2zm5 11h-4v4h-2v-4H7v-2h4V7h2v4h4v2z"></path>
        </svg>
      </div>
      </label>  
      <input onChange={handleSelectedFile} type="file" name="file" id="file" className="hidden"></input>      
        <input
          type="text"
          value={inputChat}
          onChange={handleChange}
          onKeyDown={(e) => activeEnter(e)}
          className="appearance-none block w-80 bg-white text-gray-700 border border-gray-200 rounded-lg py-2 px-4 leading-tight break-all focus:outline-none transition duration-300 ease-in-out focus:ring-1 focus:ring-indigo-500 focus:border-indigo-500 pl-9"
          placeholder="Enter your message" 
          required
        />  
        <label htmlFor="file" className="!absolute top-2 left-72 w-4 h-4 bg-white border-gray-300 rounded-xl flex font-normal text-center justify-center cursor-pointer">
        <div className="btn-upload">
          {/* 아이콘 수정 */}
          <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" style={{ fill: 'rgba(0, 0, 0, 1)' }}>
          <path d="M8 12.052c1.995 0 3.5-1.505 3.5-3.5s-1.505-3.5-3.5-3.5-3.5 1.505-3.5 3.5 1.505 3.5 3.5 3.5zM9 13H7c-2.757 0-5 2.243-5 5v1h12v-1c0-2.757-2.243-5-5-5zm9.364-10.364L16.95 4.05C18.271 5.373 19 7.131 19 9s-.729 3.627-2.05 4.95l1.414 1.414C20.064 13.663 21 11.403 21 9s-.936-4.663-2.636-6.364z"></path>
          <path d="M15.535 5.464 14.121 6.88C14.688 7.445 15 8.198 15 9s-.312 1.555-.879 2.12l1.414 1.416C16.479 11.592 17 10.337 17 9s-.521-2.592-1.465-3.536z"></path>
          </svg>
      </div>
      </label> 
        <button onClick={saychat} className="hidden">말하기</button>    
      </div>
      </div>
    </>
  );
};

export default Chatting;