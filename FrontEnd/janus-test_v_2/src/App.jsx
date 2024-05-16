const React = require("react");
const { default: VideoPage } = require("./components/pages/VideoPage");
const { default: MindmapPage } = require("./components/mindmap/node");
const { default: BoardPage } = require("./components/whiteboard/board");
const { Routes, Route } = require("react-router-dom");

const App = () => {

  return (
    <Routes>
      <Route path="/" element={<VideoPage />} />
      <Route path="/mindmap" element={<MindmapPage />} />
      <Route path="/whiteboard" element={<BoardPage />} />
    </Routes>
  );
};

module.exports = App;
