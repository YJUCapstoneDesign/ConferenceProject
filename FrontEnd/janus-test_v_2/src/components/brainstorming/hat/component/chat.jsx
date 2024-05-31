import React from 'react';

const ChatArea = () => (
  <div className="flex-1">
    <ChatHeader name="UNMUTE" />
    <ChatMessages />
  </div>
);

const ChatHeader = ({ name }) => (
  <header className="bg-white p-4 text-gray-700">
    <h1 className="text-2xl font-semibold">{name}</h1>
  </header>
);

const ChatMessages = () => (
  <div className="h-screen overflow-y-auto p-4 pb-36">
    <IncomingMessage message="Hey Bob, how's it going?" imageUrl="https://placehold.co/200x/ffa8e4/ffffff.svg?text=ʕ•́ᴥ•̀ʔ&font=Lato" />
    <OutgoingMessage message="Hi Alice! I'm good, just finished a great book. How about you?" imageUrl="https://placehold.co/200x/b7a8ff/ffffff.svg?text=ʕ•́ᴥ•̀ʔ&font=Lato" />
    <IncomingMessage message="That book sounds interesting! What's it about?" imageUrl="https://placehold.co/200x/ffa8e4/ffffff.svg?text=ʕ•́ᴥ•̀ʔ&font=Lato" />
    <OutgoingMessage message="It's about an astronaut stranded on Mars, trying to survive. Gripping stuff!" imageUrl="https://placehold.co/200x/b7a8ff/ffffff.svg?text=ʕ•́ᴥ•̀ʔ&font=Lato" />
    <IncomingMessage message="I'm intrigued! Maybe I'll borrow it from you when you're done?" imageUrl="https://placehold.co/200x/ffa8e4/ffffff.svg?text=ʕ•́ᴥ•̀ʔ&font=Lato" />
    <OutgoingMessage message="Of course! I'll drop it off at your place tomorrow." imageUrl="https://placehold.co/200x/b7a8ff/ffffff.svg?text=ʕ•́ᴥ•̀ʔ&font=Lato" />
    <IncomingMessage message="Thanks, you're the best!" imageUrl="https://placehold.co/200x/ffa8e4/ffffff.svg?text=ʕ•́ᴥ•̀ʔ&font=Lato" />
    <OutgoingMessage message="Anytime! Let me know how you like it. 😊" imageUrl="https://placehold.co/200x/b7a8ff/ffffff.svg?text=ʕ•́ᴥ•̀ʔ&font=Lato" />
    <IncomingMessage message="So, pizza next week, right?" imageUrl="https://placehold.co/200x/ffa8e4/ffffff.svg?text=ʕ•́ᴥ•̀ʔ&font=Lato" />
    <OutgoingMessage message="Absolutely! Can't wait for our pizza date. 🍕" imageUrl="https://placehold.co/200x/b7a8ff/ffffff.svg?text=ʕ•́ᴥ•̀ʔ&font=Lato" />
    <IncomingMessage message="Hoorayy!!" imageUrl="https://placehold.co/200x/ffa8e4/ffffff.svg?text=ʕ•́ᴥ•̀ʔ&font=Lato" />
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
  <div className="flex h-screen">
    <ChatArea />
  </div>
);

export default ChatApp;
