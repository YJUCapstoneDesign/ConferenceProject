import React, { useRef, useState, useEffect } from 'react';
import Janus from 'janus-gateway';
import { publishToRoom } from '../../utils/publisher';
import { subscribeRemoteFeed } from '../../utils/subscriber';

const JanusSubscriber = ({ janus, opaqueId, room, pubId, pubPvtId, feeds, render }) => {
  const videoRefs = useRef([]);
  const [playerStates, setPlayerStates] = useState(feeds.map(() => "Ready")); // feeds의 개수만큼 playerState를 저장
  const [sfutest, setSfuTest] = useState(null);
  const [remoteFeeds, setRemoteFeeds] = useState([]); // feeds의 개수만큼 remoteFeed를 저장

  console.log("::: Janus Feeds :::", feeds);


  useEffect(() => {
    // feeds 배열의 길이에 따라 videoRefs 배열을 업데이트
    videoRefs.current = feeds.map((_, i) => videoRefs.current[i] || React.createRef());
  }, [feeds.length])

  useEffect(() => {
    // Updated logic to handle multiple feeds appropriately.
    if (!janus || !room || !pubId || !pubPvtId || feeds.length === 0) {
      return;
    }

    // Processing each feed separately.
    feeds.forEach((feed, index) => {
      console.log("::: Janus Feed index:::", index);
      publishToRoom(janus, opaqueId, room, null, null, false, (_sfutest, eventType, data) => {
        setSfuTest(_sfutest);

        if (eventType === "joined" && data.publishers) {
          Janus.log("Successfully joined room with publishers: ", data.publishers);
          subscribeToFeed(data.publishers[0], index);
        } else if (eventType === "publishers" && data.publishers) {
          subscribeToFeed(data.publishers[0], index);
        } else if (eventType === "leaving" || eventType === "unpublished") {
          // Logic to detach and clean up specific feed.
          const feedToDetach = remoteFeeds[index];
          if (feedToDetach) {
            feedToDetach.detach();
            // Updating state to remove detached feed.
            setRemoteFeeds(currentFeeds => currentFeeds.map((f, i) => i === index ? null : f));
          }
        }
      });
    });
  }, [janus, room, pubId, pubPvtId, feeds]);

  // Extracted method to subscribe to a specific feed.
  const subscribeToFeed = (publisher, index) => {
    const { display, audio_codec, video_codec } = publisher;
    subscribeRemoteFeed(janus, opaqueId, room, pubPvtId, display, audio_codec, video_codec, feeds, (remoteFeed, eventType, data) => {
      if (eventType === "onremotetrack") {
        const videoRef = videoRefs.current[index];
        Janus.log(" ::: Got a remote stream ::: ", videoRefs);
        Janus.log(" ::: Got a remote stream ::: ", videoRef);

        if (videoRef) {
          const videoContainer = videoRef.current;
          const videoPlayer = videoContainer.querySelector(".janus-video-player")
          Janus.attachMediaStream(videoPlayer, data);
          // Update player status for specific feed.
          setPlayerStates(currentStates => currentStates.map((state, i) => i === index ? "Live" : state));
        }
      } else if (eventType === "oncleanup") {
        // Update player status to paused on cleanup for specific feed.
        setPlayerStates(currentStates => currentStates.map((state, i) => i === index ? "Paused" : state));
      } else if (eventType === "error") {
        // Handle error for specific feed.
        setPlayerStates(currentStates => currentStates.map((state, i) => i === index ? "Error" : state));
      }
    });
  };

  return (
    <div className="janus-subscriber">
      {feeds.map((feed, index) => (
        <div className="janus-video" key={index}>
          {render({
            videoRef: videoRefs.current[index],
            isPublisher: false,
            status: playerStates[index] // Pass individual player state.
          })}
        </div>
      ))}
    </div>
  );
}

export default JanusSubscriber;