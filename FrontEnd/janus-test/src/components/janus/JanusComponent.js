import React, { useRef, useState, useEffect } from "react";
import Janus from "janus-gateway";

const JanusComponent = ({ children, server }) => {
  const janusEl = useRef(null);
  const [janusInstance, setJanusInstance] = useState(null);

  useEffect(() => {

    Janus.init({
      debug: "all",
      callback: function () {
        if (!Janus.isWebrtcSupported()) {
          console.log("No WebRTC support... ");
          return;
        }

        const janus = new Janus({
          server: server,

          success: function () {
            console.log("Janus loaded");
            setJanusInstance(janus);
          },

          error: function (error) { // 에러가 발생했을 때 호출되는 콜백 함수
            Janus.error(error);
            setJanusInstance(null);
          },

          destroyed: function () { // 연결이 끊겼을 때 호출되는 콜백 함수
            console.log("Janus destroyed");
            setJanusInstance(null);
          },
        })
      }
    });
  }, [])

  return (
    <div ref={janusEl}>
      {children &&
        children.length &&
        children.map((child, i) => (
          React.cloneElement(child, { janus: janusInstance, key: i })
        ))
      }
      {children &&
        !children.length &&
        React.cloneElement(children, { janus: janusInstance })
      }
    </div>
  );

}

export default JanusComponent;