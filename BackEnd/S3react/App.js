import React, { useState, useRef } from "react";
import { ReactMic } from "react-mic";

const RecordPage = () => {
  const [isRecording, setIsRecording] = useState(false);
  const [audioBlob, setAudioBlob] = useState(null);
  const audioRef = useRef(null);

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
        body: audioBlob, // FormData 대신 오디오 Blob 직접 전송
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
      <h1>녹음 페이지</h1>
      <div>
        <ReactMic
          record={isRecording}
          onStop={onStop}
          onData={onData}
          mimeType="audio/wav"
        />
      </div>
      <div>
        <button onClick={startRecording} disabled={isRecording}>
          녹음 시작
        </button>
        <button onClick={stopRecording} disabled={!isRecording}>
          녹음 중지
        </button>
        <button onClick={uploadAudio} disabled={!audioBlob}>
          파일 업로드
        </button>
      </div>
      {audioBlob && (
        <div>
          <h2>녹음된 오디오</h2>
          <audio ref={audioRef} controls>
            <source src={URL.createObjectURL(audioBlob)} type="audio/wav" />
            Your browser does not support the audio element.
          </audio>
        </div>
      )}
    </div>
  );
};

export default RecordPage;
