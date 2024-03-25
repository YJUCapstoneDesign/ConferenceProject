import React, { useRef, useState, useEffect } from "react"
import Janus from "janus-gateway"

/*
기존 cloneElement => render props를 이용한 renderChildren으로 변경
*/
const JanusVideoRoom = ({ renderChildren, janus, roomId }) => {
  useEffect(() => {
    if (janus) {

    }
  }, [janus])
  return (
    <div className="janus-video-room">
      {renderChildren ? renderChildren({ janus, roomId }) : null}
      {/* {children &&
        children.length &&
        children.map((child, i) => (
          React.cloneElement(child, { janus: janus, key: i })
        ))
      }
      {children &&
        !children.length &&
        React.cloneElement(children, { janus: janus })
      } */}
    </div>
  );
}

export default JanusVideoRoom;