import React, { useRef, useState, useEffect } from "react"
import Janus from "janus-gateway"

const JanusVideoRoom = ({ children, janus, roomId }) => {
  useEffect(() => {
    if (janus) {
      //TODO : This is a sample code. You should implement your own logic.
    }
  }, [janus])
  return (
    <div className="janus-video-room">
      {children &&
        children.length &&
        children.map((child, i) => (
          React.cloneElement(child, { janus: janus, key: i })
        ))
      }
      {children &&
        !children.length &&
        React.cloneElement(children, { janus: janus })
      }
    </div>
  );
}

export default JanusVideoRoom;