const React = require("react");
// const { default: VideoPage } = require("./components/pages/VideoPage");
const {
  default: JanusComponent,
} = require("./components/janus/JanusComponent");
const { default: JanusPlayer } = require("./components/janus/JanusPlayer");
const {
  default: JanusSubscriber,
} = require("./components/janus/JanusSubscriber");
const {
  default: JanusPublisher,
} = require("./components/janus/JanusPublisher");
const {
  default: JanusVideoRoom,
} = require("./components/janus/JanusVideoRoom");

// const { default: VideoPage } = require("./components/pages/VideoPage");
let username = "test_dude"
const App = () => {
  console.log(process.env.REACT_JANUS_SERVER); //테스트를 위해 자누스 서버 주소를 출력
  const [room, setRoom] = React.useState(5678);
  const [pubId, setPubId] = React.useState(null);
  const [pubPvtId, setPubPvtId] = React.useState(null);

  //  const [chatroom, setChatroom] = useState(null);

  return (
    <div className="App">
      <JanusComponent server={process.env.REACT_JANUS_SERVER}>
        <JanusVideoRoom>
          <JanusPublisher
            opaqueId="test12234"
            room={room}
            username={username}
            // setRoom={setRoom}

            setPubId={setPubId}
            setPubPvtId={setPubPvtId}
          >
            <JanusPlayer readyText="Something" />
          </JanusPublisher>
          <JanusSubscriber
            opaqueId="test12234"
            room={room}
            pubId={pubId}
            pubPvtId={pubPvtId}
          >
            <JanusPlayer readyText="Something" />
          </JanusSubscriber>
        </JanusVideoRoom>
        {/*         <JanusChat
          opaqueId="test12234"
          isPublisher={true}
          chatroom={chatroom}
          setChatroom={setChatroom}
          username="mainboi"
          display="Main guy 123"
        /> */}

        {/*         {chatroom ? (
          <JanusChat
            opaqueId="test12234"
            isPublisher={false}
            chatroom={chatroom}
            setChatroom={setChatroom}
            username="subboi"
            display="Sub guy 456"
          />
        ) : (
          <div></div>
        )} */}
      </JanusComponent>
    </div>
  );
};

module.exports = App;
