import { Tldraw } from 'tldraw';
import 'tldraw/tldraw.css';
import { useRef, useEffect, useState } from 'react';
import React from 'react';

function RealtimeTldraw() {
  const tldrawRef = useRef(null);
  const [socket, setSocket] = useState(null);

  let ws = useRef(null);

  useEffect(() => {
    // 웹소켓 연결
    ws.current = new WebSocket("ws://localhost:8080/app");

    ws.onopen = () => {
      console.log("WebSocket Connected");
    };

    ws.current.onmessage = (message) => {
      setSocket(message.data)
    };
    return () => {
      // 컴포넌트가 unmount될 때 웹소켓 연결 종료
      ws.close();
    };
  }, []);

  const handleTldrawChange = () => {
    // Tldraw 상태 변경 시 웹소켓을 통해 서버로 전송
    const data = tldrawRef.current.value;
    console.log(data)
    socket.send(JSON.stringify(data));
  };

  return (
    <div style={{ position: 'fixed', inset: 0 }}>
      <Tldraw ref={tldrawRef} onChange={handleTldrawChange} />
    </div>
  );
}

export default RealtimeTldraw;