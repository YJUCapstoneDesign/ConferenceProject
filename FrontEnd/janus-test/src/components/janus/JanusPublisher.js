import React, { useRef, useState, useEffect, useCallback } from 'react';
import Janus from 'janus-gateway';
import { publishToRoom, publishOwnFeed, unpublishOwnFeed } from '../../utils/publisher';

const JanusPublisher = ({ janus, opaqueId, room, pin, username, setPubId, setPubPvtId, setFeeds, render }) => {
  const [playerState, setPlayerState] = useState("Ready");
  const [isMuted, setIsMuted] = useState(false);
  const [sfutest, setSfuTest] = useState(null);

  const videoArea = useRef(null);
  let mystream = null;
  const newFeeds = []; // 새로운 피드 정보를 임시 저장할 배열
  const feedStreams = {}; // id별 스트림을 저장할 객체

  useEffect(() => {
    if (janus && username) {
      publishToRoom(janus, opaqueId, room, pin, username, true,
        (_sfutest, eventType, data) => {
          setSfuTest(_sfutest);

          if (eventType == "joined") {
            const { id, private_id } = data; // 자신의 id와 private_id를 저장

            const list = data.publishers;

            for (let f in list) {
              if (list[f]["dummy"])
                continue;
              let id = list[f]["id"];
              let streams = list[f]["streams"];
              let display = list[f]["display"];
              for (let i in streams) {
                let stream = streams[i];
                stream["id"] = id;
                stream["display"] = display;
              }
              feedStreams[id] = streams;
              Janus.debug("  >> [" + id + "] " + display + ":", streams);

              newFeeds.push({ id, streams, display });
            }

            setFeeds(newFeeds);

            setPubId(id);
            setPubPvtId(private_id); // publisher의 id와 private_id를 저장

            // setPlayerState("Live");
            setPlayerState("Paused");
          } else if (eventType === "onlocaltrack") {
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
          } else if (eventType === "oncleanup") {
            setPlayerState("Paused");
            setIsMuted(false);
          } else if (eventType === "error") {
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
    console.log("onMuteClick");
    if (!sfutest.isAudioMuted()) {
      sfutest.muteAudio();
    }
    console.log("현재 Mute 상태", isMuted)
    console.log("현재 상태", playerState)
    setIsMuted(sfutest.isAudioMuted());
  }

  const onUnMuteClick = () => {
    console.log("onUnMuteClick");
    if (sfutest.isAudioMuted()) {
      sfutest.unmuteAudio();
    }

    console.log("현재 상태", playerState)
    setIsMuted(sfutest.isAudioMuted());
  }

  const onBandwidthChange = (bitrate) => {
    sfutest.send({ "message": { "request": "configure", "bitrate": bitrate } });
  }



  return (
    <div className="janus-publisher">
      <div className="janus-video">
        {render({
          videoRef: videoArea,
          isPublisher: true,
          status: playerState,
          isMuted,
          onStartClick,
          onStopClick,
          onMuteClick,
          onUnMuteClick,
          onBandwidthChange
        })}
      </div>
    </div>
  )
}

export default JanusPublisher;