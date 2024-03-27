// 임시로 만든 곳
// 아무 의미 없음
  // const remoteFeedCallback = (_remoteFeed, eventType, data) => {
  //   setRemoteFeed(_remoteFeed);


  //   if (eventType === "onremotetrack") {
  //     mystream = data; // data == stream

  //     Janus.log(" ::: Got a remote stream ::: ", mystream);


  //     const videoContainer = videoArea.current;
  //     const videoPlayer = videoContainer.querySelector(".janus-video-player")

  //     Janus.attachMediaStream(videoPlayer, mystream);
  //     if (_remoteFeed.webrtcStuff.pc.iceConnectionState !== "completed" &&
  //       _remoteFeed.webrtcStuff.pc.iceConnectionState !== "connected") {
  //       setPlayerState("Live");
  //     }
  //     var videoTracks = mystream.getVideoTracks();
  //     if (videoTracks === null || videoTracks === undefined || videoTracks.length === 0) {
  //       // setPlayerState("Error");
  //       setPlayerState("Paused");
  //     }
  //   } else if (eventType === "oncleanup") {
  //     setPlayerState("Paused");
  //   } else if (eventType === "error") {
  //     setPlayerState("Error");
  //   }
  // }

  // useEffect(() => {
  //   // feeds 무조건 1개 이상 들어 있음
  //   if (!janus || !room || !pubId || !pubPvtId) {
  //     return;
  //   }
  //   publishToRoom(janus, opaqueId, room, null, null, false,
  //     (_sfutest, eventType, data) => {
  //       setSfuTest(_sfutest);

  //       if (eventType === "joined") {
  //         if (data.publishers !== undefined && data.publishers !== null) {
  //           const list = data.publishers;
  //           if (list.length == 0) {
  //             return;
  //           }

  //           const publisher = list[0]
  //           const { display, audio_codec, video_codec } = publisher
  //           subscribeRemoteFeed(janus, opaqueId, room, pubPvtId, display, audio_codec, video_codec, feeds, remoteFeedCallback);
  //         }
  //       } else if (eventType === "publishers") {
  //         if (data.publishers !== undefined && data.publishers !== null) {
  //           // we are only consiering one publisher now
  //           const list = data.publishers;
  //           if (list.length == 0) {
  //             return;
  //           }

  //           const publisher = list[0]
  //           const { display, audio_codec, video_codec } = publisher;
  //           subscribeRemoteFeed(janus, opaqueId, room, pubPvtId, display, audio_codec, video_codec, feeds, remoteFeedCallback)
  //         }

  //       } else if (eventType === "leaving" || eventType === "unpublished") {
  //         if (remoteFeed !== null) {
  //           remoteFeed.detach();
  //           setRemoteFeed(null); // reset remoteFeed
  //         }
  //       }
  //     });
  // }, [janus, room, pubId, pubPvtId, feeds])

  // const playerElement = children ? children : <JanusPlayer />;