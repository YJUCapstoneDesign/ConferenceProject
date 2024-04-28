import React from "react";
import { createRoot } from "react-dom/client";
import App from "./App.jsx";
import "./tailwind.css";

// react 18 + Not supported ReactDom.render
// createRoot use for react 18
createRoot(document.getElementById('root')).render(
    <App />
  );
