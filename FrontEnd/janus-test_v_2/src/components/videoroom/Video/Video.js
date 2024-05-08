import React, { useRef, useEffect, useState } from "react";
import './video.css';
const Video = (props) => {

  const sizeUp = props.sizeUp;
  const videoRef = useRef();
  useEffect(() => {
    if (props.stream) {
      videoRef.current.srcObject = props.stream;
    }
  }, [props.stream]);

  const onClick = (e) => {
    e.preventDefault();
    if (!props.onClick) return;
    props.onClick(videoRef.current.srcObject, props.username);
    // props.onClick(videoRef.current.srcObject); // props.username 제거 
    // props.onClick();
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
          style={{backgroundColor: "black"}}
          controls={props.onClick ? false : true}
        />
      </div>
      <div className="text-black text-center">{props.username}</div>
    </>
  );
};

export default Video;