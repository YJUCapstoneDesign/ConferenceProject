const express = require('express');
const http = require('http');
const WebSocket = require('ws');

const app = express();
const server = http.createServer(app);
const wss = new WebSocket.Server({ server });

// 웹 소켓 연결 시
wss.on('connection', (ws) => {
    // 클라이언트로부터 메시지 수신 시
    ws.on('message', (message) => {
        // 연결된 모든 클라이언트에게 메시지 전송
        wss.clients.forEach((client) => {
            if (client.readyState === WebSocket.OPEN) {
                client.send(message);
            }
        });
    });
});

server.listen(8080, () => {
    console.log('웹 소켓 서버가 8080번 포트에서 실행 중입니다.');
});
