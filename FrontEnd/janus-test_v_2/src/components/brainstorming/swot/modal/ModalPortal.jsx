import ReactDOM from "react-dom";
import React from "react";

const ModalPortal = ({ children }) => {
  const element = document.getElementById("modal-root");

  return ReactDOM.createPortal(children, element);
};

export default ModalPortal;