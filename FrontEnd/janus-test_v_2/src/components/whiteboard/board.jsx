import React, { useCallback, useEffect, useRef, useState } from 'react';
import _ from 'lodash';
import { Editor, Tldraw } from 'tldraw';
import 'tldraw/tldraw.css';

export default function StoreEventsExample() {
  const [editor, setEditor] = useState(null); // 에디터 상태
  const [socketData, setSocketData] = useState(); // 소켓 데이터 상태
  const ws = useRef(null); // 웹소켓 연결 상태 useRef로 선언

  // 웹소켓 연결 설정
  useEffect(() => {
    // 웹소켓 연결 설정
    ws.current = new WebSocket("ws://localhost:8080/app");

    // 웹소켓 메시지 수신 시 이벤트 핸들러 설정
    ws.current.onmessage = (message) => {
      setSocketData(message.data); // 소켓 데이터 상태 업데이트
    };

    // 컴포넌트 언마운트 시 웹소켓 연결 종료
    return () => {
      ws.current.close();
    };
  }, []);

  // 소켓 데이터 변경 시 처리
  useEffect(() => {
    if (socketData !== undefined) {
      try {
        // 소켓 데이터 파싱
        const parsedSocketData = JSON.parse(socketData);
        if (parsedSocketData.events && Array.isArray(parsedSocketData.events)) {
          if (editor) {
            // 에디터 스토어 데이터 변경
            editor.store.replaceStore(parsedSocketData.events);
          }
        }
      } catch (error) {
        console.error('Failed to parse socket data:', error);
      }
    }
  }, [socketData, editor]);

  // 웹소켓을 통해 데이터 전송하는 함수
  const sendWebSocketData = useCallback((data) => {
    if (ws.current && ws.current.readyState === WebSocket.OPEN) {
      // 웹소켓이 연결되어 있고 OPEN 상태일 때 데이터 전송
      ws.current.send(JSON.stringify(data));
    } else {
      // 연결되어 있지 않거나 OPEN 상태가 아닐 때 연결을 기다리고 데이터 전송
      ws.current.onopen = () => {
        ws.current.send(JSON.stringify(data));
      };
    }
  }, []);

  // 에디터 상태를 설정하는 콜백 함수
  const setAppToState = useCallback((editor) => {
    setEditor(editor);
  }, []);

  // 에디터 변경 이벤트 처리
  useEffect(() => {
    if (!editor) return;

    // 변경 이벤트 핸들러 함수
    function logChangeEvent(eventName) {
      sendWebSocketData({ event: eventName }); // 이벤트 발생 시 서버로 전송
    }

    const handleChangeEvent = (change) => {
      // 추가된 요소 처리
      if (change.changes.added) {
        for (const record of Object.values(change.changes.added)) {
          if (record.typeName === 'shape') {
            logChangeEvent(`created shape (${record.type})\n`);
          }
        }
      }

      // 업데이트된 요소 처리
      if (change.changes.updated) {
        for (const [from, to] of Object.values(change.changes.updated)) {
          if (from.typeName === 'instance' && to.typeName === 'instance' && from.currentPageId !== to.currentPageId) {
            logChangeEvent(`changed page (${from.currentPageId}, ${to.currentPageId})`);
          } else if (from.id.startsWith('shape') && to.id.startsWith('shape')) {
            let diff = _.reduce(from, (result, value, key) => _.isEqual(value, to[key]) ? result : result.concat([key, to[key]]), []);
            if (diff?.[0] === 'props') {
              diff = _.reduce(from.props, (result, value, key) => _.isEqual(value, to.props[key]) ? result : result.concat([key, to.props[key]]), []);
            }
            logChangeEvent(`updated shape (${JSON.stringify(diff)})\n`);
          }
        }
      }

      // 삭제된 요소 처리
      if (change.changes.removed) {
        for (const record of Object.values(change.changes.removed)) {
          if (record.typeName === 'shape') {
            logChangeEvent(`deleted shape (${record.type})\n`);
          }
        }
      }
    };

    // 에디터 스토어 리스너 설정
    const cleanupFunction = editor.store.listen(handleChangeEvent, { source: 'user'});

    return () => {
      cleanupFunction();
    };
  }, [editor, sendWebSocketData]);

  return (
    <div style={{ display: 'flex' }}>
      <div style={{ width: '100%', height: '100vh' }}>
        <Tldraw onMount={setAppToState} />
      </div>
    </div>
  );
}
