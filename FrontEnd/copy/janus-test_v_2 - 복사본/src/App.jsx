const React = require("react");
const { default: VideoPage } = require("./components/pages/VideoPage");
const { default: MindmapPage } = require("./components/mindmap/node");
const { default: BoardPage } = require("./components/whiteboard/board");
const { default: SwotPage } = require("./components/brainstorming/swot/canvas");
const { default: HatPage } = require("./components/brainstorming/hat/canvas");
const { Routes, Route } = require("react-router-dom");

const App = () => {
  return (
    <Routes>
      <Route path="/" element={<VideoPage />} />
      <Route path="/mindmap" element={<MindmapPage />} />
      <Route path="/whiteboard" element={<BoardPage />} />
      <Route path="/swot" element={<SwotPage />} />
      <Route path="/hat" element={<HatPage />} />
    </Routes>
  );
};

module.exports = App;
