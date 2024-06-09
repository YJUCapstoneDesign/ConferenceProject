const React = require("react");
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
const { default: AboutIn } = require("./components/page-in/AboutIn");
const { default: Mypage } = require("./components/page-in/Mypage");
const { default: VideoComponent } = require("./components/videoroom/VideoComponent");
const { default: MindMapPage } = require("./components/mindmap/MindMapPage");

const App = () => {
  return (
        <Routes>
          <Route path="/video/:teamNumber" element={<VideoComponent />} />
          <Route path='/mindmap/:teamNumber' element={<MindMapPage/>} />
          <Route path="/whiteboard/:teamNumber" element={<BoardPage />} />
          <Route path="/swot/:teamNumber" element={<SwotPage />} />
          <Route path="/hat/:teamNumber" element={<HatPage />} />
          <Route path="/" element={<Scrollspy />} />
          <Route path="/Pass" element={<PassWord />} />
          <Route path="/OurService" element={<OurService />} />
          <Route path="/signin" element={<Signin/>} />
          <Route path='/signup' element={<Signup />} />
          <Route path='Room' element={<RoomService/>} />
          <Route path='/JanusTeam/:teamNumber' element={<JanusTeam/>} />
          <Route path='/Entrance' element={<EntranceRoom/>} />
          <Route path='/Select' element={<SelectRoom/>} />
          <Route path='/Crazy/:teamNumber' element={<CrazyPage/>} />
          <Route path='/AboutIn' element={<AboutIn/>} />
          <Route path='/Mypage' element={<Mypage/>}/>
        </Routes>
  );
};

module.exports = App;
