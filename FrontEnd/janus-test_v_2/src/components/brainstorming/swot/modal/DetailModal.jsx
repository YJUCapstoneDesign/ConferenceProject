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
        <div className="container w-[300px] h-[300px] bg-white border-2">
          <div className="w-full h-full">
            <h1 className="text-2xl font-bold text-center text-black">Detail</h1>
            <div className="p-4">
              <input
                className="w-full mb-2 p-2 border"
                value={editTitle}
                onChange={(e) => setEditTitle(e.target.value)}
              />
              <textarea
                className="w-full mb-2 p-2 border"
                value={editContent}
                onChange={(e) => setEditContent(e.target.value)}
              />
              <p className="text-center text-black">Area: {area}</p>
            </div>
          </div>
          <button onClick={handleUpdate} className="w-full py-2 bg-green-400">Update</button>
          <button onClick={handleDelete} className="w-full py-2 bg-red-400">Delete</button>
          <button onClick={onClose} className="w-full py-2 bg-blue-400">Close</button>
        </div>
      </div>
    </ModalPortal>
  );
};

export default DetailModal;