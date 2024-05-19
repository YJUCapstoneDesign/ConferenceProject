import React, { useState, useRef } from "react";
import { ReactMic } from "react-mic";
import "./RecordPage.css";
import start from "../icon/start.svg";
import stop from "../icon/stop.svg";
import file from "../icon/file.svg";

const RecordPage = () => {
  const [isRecording, setIsRecording] = useState(false);
  const [audioBlob, setAudioBlob] = useState(null);
  const audioRef = useRef(null);

  // 버튼 하나로 통합해서 함수 하나의 버튼에서 두 가지 함수가 동작하도록 했음
  const toggleRecording = () => {
    if (isRecording) {
      stopRecording();
    } else {
      startRecording();
    }
  };

  const startRecording = () => {
    setIsRecording(true);
  };

  const stopRecording = () => {
    setIsRecording(false);
  };

  const onData = (recordedBlob) => {
    console.log("chunk of real-time data is: ", recordedBlob);
  };

  const onStop = (recordedBlob) => {
    console.log("recordedBlob is: ", recordedBlob);
    setAudioBlob(recordedBlob.blob);
  };

  const uploadAudio = async () => {
    // WAV 오디오 데이터를 직접 서버로 전송
    try {
      const response = await fetch("http://localhost:8080/upload", {
        method: "POST",
        body: audioBlob,
      });

      if (response.ok) {
        console.log("File uploaded successfully.");
      } else {
        console.error("Failed to upload file:", response.statusText);
      }
    } catch (error) {
      console.error("Error uploading file:", error);
    }
  };

  return (
    <div>
      <div>
        <ReactMic
          record={isRecording}
          onStop={onStop}
          onData={onData}
          mimeType="audio/wav"
        />
      </div>
      <div>
        {/* 버튼 하나로 줄임. 하나의 버튼으로 녹음 시작과 중지 조작  */}
        <button onClick={toggleRecording} className="outline-none focus:outline-none border px-3 py-3 bg-white flex items-center min-w-1 rounded-3xl mb-3">
          {isRecording ? (
            <img src={stop} alt="Stop Recording" width="24" height="24"/> 
          ) : (
            <img src={start} alt="Start Recording" width="24" height="24"/>
          )}
        </button>
        <button onClick={uploadAudio} disabled={!audioBlob} className="outline-none focus:outline-none border px-3 py-3 bg-white flex items-center min-w-1 rounded-3xl">
          <img src={file} alt="File Upload" width="24" height="24" />
        </button>
      </div>
      {audioBlob && (
        <div className="">
          {/* <h2>녹음된 오디오</h2> */}
          <audio ref={audioRef} controls className="audio">
            <source src={URL.createObjectURL(audioBlob)} type="audio/wav" className="audio"/>
            Your browser does not support the audio element.
          </audio>
        </div>
      )}
    </div>
  );
};

export default RecordPage;
