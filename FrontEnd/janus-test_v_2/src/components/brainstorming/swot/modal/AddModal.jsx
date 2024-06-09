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
      sendWebSocketData({ tiles: updatedList });
      return updatedList;
    });

    setTile('');
    setContent('');
    onClose();
  }

  return (
    <ModalPortal>
      <div className="h-screen w-full fixed bg-[#00000066] left-0 top-0 flex justify-center items-center bg-main-black bg-opacity-70">
        <div className="container w-[300px] h-auto bg-white border-2">
          <form onSubmit={submitHandler}>
            <select name="area" id="area" className="w-full py-2">
              <option value={1}>STRENGTHS</option>
              <option value={2}>WEAKNESSES</option>
              <option value={3}>OPPORTUNITIES</option>
              <option value={4}>THREATS</option>
            </select>
            <hr />
            <input 
              className="w-full" 
              type="text" 
              placeholder="Title" 
              value={title}
              onChange={(e) => setTile(e.target.value)}
            />
            <br />
            <textarea 
              className="w-full"
              name="content" 
              placeholder="상세 내용 기술" 
              id="content" 
              cols="30" 
              rows="10" 
              value={content}
              onChange={(e) => setContent(e.target.value)}
            />
            <div className="flex justify-between gap-x-10">
              <button onClick={onClose} className="w-1/2 py-2 bg-blue-400">Cancel</button>
              <button type="submit" className="w-1/2 py-2 bg-blue-400">Save</button>
            </div>
          </form>
        </div>
      </div>
    </ModalPortal>
  );
};

export default AddModal;
