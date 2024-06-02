import React from 'react';

const ChatArea = () => (
  <div className="flex-1">
    <ChatMessages />
    <ChatInput />
  </div>
);

const ChatMessages = () => (
  <div className="overflow-y-auto p-4">
    <IncomingMessage message="Fuck You!!" imageUrl="https://placehold.co/200x/ffa8e4/ffffff.svg?text=ʕ•́ᴥ•̀ʔ&font=Lato" />
    <OutgoingMessage message="..." imageUrl="https://placehold.co/200x/b7a8ff/ffffff.svg?text=ʕ•́ᴥ•̀ʔ&font=Lato" />
  </div>
  
);

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

const ChatInput = () => (
  <div className="fixed bottom-0 left-0 right-0 bg-gray-100 p-4 flex items-center">
    <input type="text" className="flex-1 rounded-full px-4 py-2 border border-gray-300 focus:outline-none" placeholder="Type a message..." />
    <button className="bg-indigo-600 text-white rounded-full px-4 py-2 ml-3">
      Send
    </button>
  </div>
);

const ChatApp = () => (
  <div className="flex">
    <ChatArea />
  </div>
);

export default ChatApp;
