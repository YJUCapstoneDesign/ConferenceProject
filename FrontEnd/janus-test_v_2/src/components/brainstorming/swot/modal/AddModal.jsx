import ModalPortal from "./ModalPortal";
import React, { useState } from "react";
import { updateArea } from "./utils/TileList";

const AddModal = ({ onClose, setDataList, sendWebSocketData }) => {
  const [title, setTile] = useState('');
  const [content, setContent] = useState(''); 

  const submitHandler = (e) => {
    e.preventDefault();

    let area = 0;
    area = parseInt(document.getElementById('area').value);
    setDataList((prev) => {
      const updatedList = updateArea(prev, title, content, area);
      sendWebSocketData(updatedList);
      return updatedList;
    });

    setTile('');
    setContent('');
    onClose();
  }

  return (
    <ModalPortal>
      <div className="h-screen w-full fixed bg-[#00000066] left-0 top-0 flex justify-center items-center bg-main-black bg-opacity-70">
        <div className="container w-[300px] h-auto bg-white border-2 rounded-lg p-5">
          <form onSubmit={submitHandler}>
            <select name="area" id="area" className="py-2 bg-gray-50 border border-gray-300 text-gray-500 text-sm rounded-lg focus:ring-indigo-500 focus:border-indigo-600 block w-full p-2.5 font-bold dark:placeholder-gray-400 dark:text-white dark:focus:ring-indigo-600 dark:focus:border-indigo-600">
              <option value={1}>STRENGTHS</option>
              <option value={2}>WEAKNESSES</option>
              <option value={3}>OPPORTUNITIES</option>
              <option value={4}>THREATS</option>
            </select>
            <input 
              className="block w-full h-9 p-2 text-gray-900 border border-gray-300 rounded-lg bg-gray-50 text-xs focus:ring-blue-500 focus:border-blue-500 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500 mt-2" 
              type="text" 
              placeholder="Title" 
              value={title}
              onChange={(e) => setTile(e.target.value)}
            />
            <textarea 
              className="mt-2 block p-2.5 w-full text-sm text-gray-900 bg-gray-50 rounded-lg border border-gray-300 focus:ring-blue-500 focus:border-blue-500 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
              name="content" 
              placeholder="Detailed technical description" 
              id="content" 
              cols="30" 
              rows="10" 
              value={content}
              onChange={(e) => setContent(e.target.value)}
            />
            <div className="flex justify-between gap-x-4 mt-4">
              <button onClick={onClose} className="flex-1 text-white bg-gradient-to-r from-red-400 via-red-500 to-red-600 hover:bg-gradient-to-br focus:ring-4 focus:outline-none focus:ring-red-300 dark:focus:ring-red-800 shadow-lg shadow-red-500/50 dark:shadow-lg dark:shadow-red-800/80 font-medium rounded-lg text-sm px-5 py-2.5 text-center">Cancel</button>
              <button type="submit" className="flex-1 text-white bg-gradient-to-r from-indigo-500 to-indigo-700 hover:bg-gradient-to-br focus:ring-4 focus:outline-none focus:ring-indigo-300 dark:focus:ring-indigo-800 shadow-lg shadow-indigo-500/50 dark:shadow-lg dark:shadow-indigo-800/80 font-medium rounded-lg text-sm px-5 py-2.5 text-center">Save</button>
            </div>
          </form>
        </div>  
      </div>
    </ModalPortal>
  );
};

export default AddModal;
