const React = require("react");
const { default: VideoPage } = require("./components/pages/VideoPage");
const { default: MindmapPage } = require("./components/mindmap/node");
const { default: BoardPage } = require("./components/whiteboard/board");
const { default: SwotPage } = require("./components/brainstorming/swot/canvas");
const { default: HatPage } = require("./components/brainstorming/hat/canvas");
const { Routes, Route } = require("react-router-dom");
const { default: Scrollspy} = require("./components/scrollspy");
const { default: PassWord} = require("./components/page-in/password");
const { default: OurService} = require("./components/page-in/OurService");
const { default: Signin} = require("./components/page-in/signin");
const { default: Signup} = require("./components/page-in/signup");
const { default: RoomService} = require("./components/page-in/RoomService");
const { default: JanusTeam} = require("./components/page-in/JanusTeam");
const { default: EntranceRoom} = require("./components/page-in/EntranceRoom");
const { default: SelectRoom} = require("./components/page-in/SelectRoom");
const { default: CrazyPage } = require("./components/crazyeight/crazy");

const App = () => {
  return (
        <Routes>
          <Route path="/video" element={<VideoPage />} />
          <Route path="/mindmap" element={<MindmapPage />} />
          <Route path="/whiteboard" element={<BoardPage />} />
          <Route path="/swot" element={<SwotPage />} />
          <Route path="/hat" element={<HatPage />} />
          <Route path="/" element={<Scrollspy />} />
          <Route path="/Pass" element={<PassWord />} />
          <Route path="/OurService" element={<OurService />} />
          <Route path="Login" element={<Signin/>} />
          <Route path='signup' element={<Signup />} />
          <Route path='Room' element={<RoomService/>} />
          <Route path='/JanusTeam/:teamNumber' element={<JanusTeam/>} /> {/* 테스트 시 수정 필요함 /: 를 통해서 동적으로 뒤에 오는 페이지를 구현한다고 함*/}
          <Route path='/Entrance' element={<EntranceRoom/>} />
          <Route path='/Select' element={<SelectRoom/>} />
          <Route path='/Crazy' element={<CrazyPage/>} />
        </Routes>
  );
};

module.exports = App;
