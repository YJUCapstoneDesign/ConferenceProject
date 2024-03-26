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
      {renderChildren ? renderChildren({ janus }) : null}
    </div>
  );
}

export default JanusVideoRoom;