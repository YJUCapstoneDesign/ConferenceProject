import React, { useRef, useState, useEffect, useCallback } from 'react';
import Janus from 'janus-gateway';
import { publishToRoom, publishOwnFeed, unpublishOwnFeed } from '../../utils/publisher';

const JanusPublisher = ({ janus, opaqueId, room, pin, username, setPubId, setPubPvtId, children }) => {
  const [playerState, setPlayerState] = useState("Ready");
  const [isMuted, setIsMuted] = useState(false);
  const [sfutest, setSfuTest] = useState(null);

  const videoArea = useRef(null);
  let mystream = null;

  useEffect(() => {
    if (janus && username) {
      publishToRoom(janus, opaqueId, room, pin, username, true,
        (_sfutest, eventType, data) => {
          setSfuTest(_sfutest);

          console.log("### eventType ", eventType);

          if (eventType == "joined") {
            const { id, private_id } = data;


            setPubId(id);
            setPubPvtId(private_id); // publisher의 id와 private_id를 저장

            setPlayerState("Paused");
          }else if(eventType === "onlocaltrack"){
              mystream = data;
              Janus.log(" ::: Got a local stream ::: ", mystream);
              const videoContainer = videoArea.current;
              const videoPlayer = videoContainer.querySelector(".janus-video-player")

              Janus.attachMediaStream(videoPlayer, mystream);
              if (_sfutest.webrtcStuff.pc.iceConnectionState !== "completed" &&
                  _sfutest.webrtcStuff.pc.iceConnectionState !== "connected") {
                  setPlayerState("Live");
              }
              var videoTracks = mystream.getVideoTracks();
              if (videoTracks === null || videoTracks === undefined || videoTracks.length === 0) {
                  setPlayerState("Error");
              }
          }else if(eventType === "oncleanup"){
              setPlayerState("Paused");
              setIsMuted(false);
          }else if(eventType === "error"){
              setPlayerState("Error");
              setIsMuted(false);
          }
        }
      );
    }
  }, [janus])

  const onStartClick = () => {
    publishOwnFeed(sfutest, true);
  }

  const onStopClick = () => {
    unpublishOwnFeed(sfutest);
    setPlayerState("Paused");
  }

  const onMuteClick = () => {
    if (!sfutest.isAudioMuted()) {
      sfutest.muteAudio();
    }

    setIsMuted(sfutest.isAudioMuted());
  }

  const onUnMuteClick = () => {
    if (sfutest.isAudioMuted()) {
      sfutest.unmuteAudio();
    }
    setIsMuted(sfutest.isAudioMuted());
  }

  const onBandwidthChange = (bitrate) => {
    sfutest.send({ "message": { "request": "configure", "bitrate": bitrate } });
  }

  const playerElement = children ? children : <JanusPlayer />;

  return (
    <div className="janus-publisher">
      <div className="janus-video">
        {React.cloneElement(playerElement, {
          ref: videoArea,
          isPublisher: true,
          status: playerState,
          isMuted: isMuted,
          onStart: onStartClick,
          onStop: onStopClick,
          onMute: onMuteClick,
          onUnmute: onUnMuteClick,
          onBandwidthChange: onBandwidthChange
        })}
      </div>
    </div>
  )
}

export default JanusPublisher;