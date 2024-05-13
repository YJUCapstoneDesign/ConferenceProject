import React, { useState, useEffect, useRef } from "react";

const Chatting = (props) => {
  const [chatData, setChatData] = useState([]);
  const [inputChat, setInputChat] = useState("");
  const [seletecedFile, setSeletecedFile] = useState(null);

  const handleChange = (e) => {
    setInputChat(e.target.value);
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
    return <p key={i}> {c} </p>;
  });

  return (
    <>
      <div
        style={{
          border: "1px solid",
          overflow: "auto",
          minHeight: "500px",
        }}
      >
        {renderChatData}
      </div>
      <div className="relative flex h-10 w-full min-w-[200px] max-w-[24rem]">
      <label htmlFor="file" className="!absolute top-2 left-3 w-4 h-4 bg-white border-gray-300 rounded-xl flex font-normal text-center justify-center cursor-pointer">
        <div className="btn-upload"><svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" style={{ fill: 'rgba(0, 0, 0, 1)' }}>
        <path d="M12 2C6.486 2 2 6.486 2 12s4.486 10 10 10 10-4.486 10-10S17.514 2 12 2zm5 11h-4v4h-2v-4H7v-2h4V7h2v4h4v2z"></path>
        </svg>
      </div>
      </label>  
      <input onChange={handleSelectedFile} type="file" name="file" id="file" className="hidden"></input>
        <button onClick={() => {
          handleClick();
          handleFileTransfer();
        }} className="!absolute right-1 top-1 z-10 select-none rounded bg-indigo-500 py-2 px-4 text-center align-middle font-sans text-xs font-bold uppercase text-white shadow-md shadow-indigo-500/20 transition-all hover:shadow-lg hover:shadow-indigo-500/40 focus:opacity-[0.85] focus:shadow-none active:opacity-[0.85] active:shadow-none peer-placeholder-shown:pointer-events-none peer-placeholder-shown:bg-blue-gray-500 peer-placeholder-shown:opacity-50 peer-placeholder-shown:shadow-none" data-ripple-light="true">Enter
        </button>       
        <input
          type="text"
          value={inputChat}
          onChange={handleChange}
          className="peer h-full w-full rounded-[7px] border border-blue-gray-200 bg-transparent px-3 py-2.5 pr-20 font-sans text-sm font-normal text-blue-gray-700 outline outline-0 transition-all placeholder-shown:border placeholder-shown:border-blue-gray-200 placeholder-shown:border-t-blue-gray-200 focus:border-2 focus:border-indigo-500 focus:border-t-transparent focus:outline-0 disabled:border-0 disabled:bg-blue-gray-50"
          placeholder=""
          required
        />
        <label className="before:content[' '] after:content[' '] pointer-events-none absolute left-0 -top-1.5 flex h-full w-full select-none text-[11px] font-normal leading-tight text-blue-gray-400 transition-all before:pointer-events-none before:mt-[6.5px] before:mr-1 before:box-border before:block before:h-1.5 before:w-2.5 before:rounded-tl-md before:border-t before:border-l before:border-blue-gray-200 before:transition-all after:pointer-events-none after:mt-[6.5px] after:ml-1 after:box-border after:block after:h-1.5 after:w-2.5 after:flex-grow after:rounded-tr-md after:border-t after:border-r after:border-blue-gray-200 after:transition-all peer-placeholder-shown:text-sm peer-placeholder-shown:leading-[3.75] peer-placeholder-shown:text-blue-gray-500 peer-placeholder-shown:before:border-transparent peer-placeholder-shown:after:border-transparent peer-focus:text-[11px] peer-focus:leading-tight peer-focus:text-indigo-500 peer-focus:before:border-t-2 peer-focus:before:border-l-2 peer-focus:before:!border-indigo-500 peer-focus:after:border-t-2 peer-focus:after:border-r-2 peer-focus:after:!border-indigo-500 peer-disabled:text-transparent peer-disabled:before:border-transparent peer-disabled:after:border-transparent peer-disabled:peer-placeholder-shown:text-blue-gray-500">
        Enter your message
        </label>
      </div>
      <button onClick={saychat}>말하기</button>
    </>
  );
};

export default Chatting;