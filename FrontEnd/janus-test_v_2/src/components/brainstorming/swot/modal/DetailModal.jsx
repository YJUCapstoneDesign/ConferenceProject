import React, { useState } from "react";
import ModalPortal from "./ModalPortal";

const DetailModal = ({ onClose, title, content, area, updateTile, deleteTile }) => {
  const [editTitle, setEditTitle] = useState(title);
  const [editContent, setEditContent] = useState(content);

  const handleUpdate = () => {
    updateTile(editTitle, editContent);
    onClose();
  };

  const handleDelete = () => {
    deleteTile();
    onClose();
  };

  return (
    <ModalPortal>
      <div className="h-screen w-full fixed bg-[#00000066] left-0 top-0 flex justify-center items-center bg-main-black bg-opacity-70">
        <div className="container w-[300px] h-[325px] bg-white border-2 rounded-lg p-5">
          <div className="w-full h-full">
            <h1 className="text-2xl font-bold text-center text-gray-500">Detail</h1>
            <div className="p-1">
              <input
                className="block w-full h-9 p-2 text-gray-900 border border-gray-300 rounded-lg bg-gray-50 text-xs focus:ring-blue-500 focus:border-blue-500 "
                value={editTitle}
                onChange={(e) => setEditTitle(e.target.value)}
              />
              <textarea
                className="mt-3 block p-2.5 w-full text-sm text-gray-900 bg-gray-50 rounded-lg border border-gray-300 focus:ring-blue-500 focus:border-blue-500"
                value={editContent}
                onChange={(e) => setEditContent(e.target.value)}
              />  
            </div>
          </div>
          <button onClick={handleUpdate} className="flex-1 text-white bg-gradient-to-r from-indigo-500 to-indigo-700 hover:bg-gradient-to-br focus:ring-4 focus:outline-none focus:ring-indigo-300 shadow-lg shadow-indigo-500/50 dark:shadow-lg  font-medium rounded-lg text-sm px-5 py-2.5 text-center mb-3">Update</button>
          <button onClick={handleDelete} className="flex-1 text-white bg-gradient-to-r from-red-400 via-red-500 to-red-600 hover:bg-gradient-to-br focus:ring-4 focus:outline-none focus:ring-red-300 shadow-lg shadow-red-500/50 dark:shadow-lg  font-medium rounded-lg text-sm px-5 py-2.5 text-center mb-3">Delete</button>
          <button onClick={onClose} className="flex-1 text-white bg-gradient-to-r from-red-400 via-red-500 to-red-600 hover:bg-gradient-to-br focus:ring-4 focus:outline-none focus:ring-red-300 shadow-lg shadow-red-500/50 dark:shadow-lg  font-medium rounded-lg text-sm px-5 py-2.5 text-center">Close</button>
        </div>
      </div>
    </ModalPortal>
  );
};

export default DetailModal;