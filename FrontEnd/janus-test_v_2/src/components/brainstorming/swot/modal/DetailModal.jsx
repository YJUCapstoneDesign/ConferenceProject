import React from "react";
import ModalPortal from "./ModalPortal";

const DetailModal = ({onClose, title, content, area}) => {

  return (
    <ModalPortal>
    <div className="h-screen w-full fixed bg-[#00000066] left-0 top-0 flex justify-center items-center bg-main-black bg-opacity-70">
      <div className="container w-[300px] h-[300px] bg-white border-2">
        <div className="w-full h-full">
          <h1 className="text-2xl font-bold text-center">{title}</h1>
          <p className="text-center">{content}</p>
          <p className="text-center">{area}</p>
          </div>
          <button onClick={onClose} className="w-full py-2 bg-blue-400">Close</button>
      </div>
    </div>
  </ModalPortal>
  );
};

export default DetailModal;