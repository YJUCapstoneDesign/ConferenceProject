import React, { useRef, useEffect, useState } from "react";
import './video.css';
const Video = (props) => {
  const videoRef = useRef();
  useEffect(() => {
    if (props.stream) {
      videoRef.current.srcObject = props.stream;
    }
  }, [props.stream]);

  const onClick = (e) => {
    e.preventDefault();
    if (!props.onClick) return;
    props.onClick(videoRef.current.srcObject); // props.username 제거 
  };
  return (
    <>
      <div className="sub-box">
        <video
          id="video"
          className="video"
          autoPlay
          playsInline
          ref={videoRef}
          onClick={onClick}
          muted={props.muted}
          controls={props.onClick ? false : true}
        />
      </div>
      <div>{props.username}</div>
    </>
  );
};

export default Video;