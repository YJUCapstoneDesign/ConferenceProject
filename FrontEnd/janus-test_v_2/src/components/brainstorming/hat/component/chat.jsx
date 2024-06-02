import React, { useState, useEffect } from 'react';

const IncomingMessage = ({ message, imageUrl }) => (
  <div className="flex items-start mb-4">
    <img src={imageUrl} alt="User Avatar" className="w-10 h-10 rounded-full mr-3" />
    <div className="bg-gray-200 rounded-lg px-4 py-2 max-w-sm">
      <p className="text-gray-700">{message}</p>
    </div>
  </div>
);

const OutgoingMessage = ({ message, imageUrl }) => (
  <div className="flex items-start justify-end mb-4">
    <div className="bg-indigo-600 text-white rounded-lg px-4 py-2 max-w-sm">
      <p className="text-white">{message}</p>
    </div>
    <img src={imageUrl} alt="User Avatar" className="w-10 h-10 rounded-full ml-3" />
  </div>
);

const ChatArea = () => {
  const [messages, setMessages] = useState([]);
  const [messageInput, setMessageInput] = useState('');
  const [socket, setSocket] = useState(null);

  useEffect(() => {
    const ws = new WebSocket('ws://localhost:8080/hat');

    ws.onopen = () => {
      console.log('WebSocket 연결됨');
    };

    ws.onmessage = (event) => {
      const message = event.data;
      setMessages(prevMessages => [...prevMessages, message]); 
    };

    setSocket(ws);

    return () => {
      ws.close();
    };
  }, []);

  const handleMessageSubmit = (e) => {
    e.preventDefault();
    if (messageInput.trim() !== '') {
      socket.send(messageInput);
      setMessageInput('');
    }
  };

  const handleInputChange = (e) => {
    setMessageInput(e.target.value);
  };

  return (
    <div className="flex-1">
      <div className="overflow-y-auto p-4">
        {messages.map((message, index) => {
          const isOutgoing = message.startsWith('You:'); 
          return (
            <div key={index} className={isOutgoing ? "flex items-end justify-end mb-4" : "flex items-start justify-start mb-4"}>
              {isOutgoing ? (
                <OutgoingMessage message={message} imageUrl="Your Image URL" />
              ) : (
                <IncomingMessage message={message} imageUrl="Opponent's Image URL" />
              )}
            </div>
          );
        })}
      </div>
      <form className="fixed bottom-0 left-0 right-0 bg-gray-100 p-4 flex items-center" onSubmit={handleMessageSubmit}>
        <input
          type="text"
          value={messageInput}
          onChange={handleInputChange}
          className="flex-1 rounded-full px-4 py-2 border border-gray-300 focus:outline-none"
          placeholder="메시지를 입력하세요..."
        />
        <button type="submit" className="bg-indigo-600 text-white rounded-full px-4 py-2 ml-3">
          보내기
        </button>
      </form>
    </div>
  );
};

export default ChatArea;
