const React = require("react");
const { default: VideoComponent } = require("./components/videoroom/VideoComponent");
const App = () => {

  return (
    <div className="App">
      <VideoComponent/>
    </div>
  );
};

module.exports = App;
