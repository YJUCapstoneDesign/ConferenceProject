import Janus from 'janus-gateway';
// publisher helper function

export function publishToRoom(janus, opaqueId, room, pin, username, isPublisher, callback) {
  let sfutest = null; // plugin handle이 저장될 변수
  let mystream = null; // 사용자의 미디어 스트림이 저장될 변수
  let localTracks = {};

  console.log("### isPublisher", isPublisher)
  // console.log("callback", typeof callback)

  // Janus 객체가 없으면 종료
  if (!janus) {
    return;
  }

  janus.attach({
    plugin: "janus.plugin.videoroom",
    opaqueId: opaqueId,

    // 성공적으로 plugin handle을 받았을 때 호출되는 콜백 함수
    success: function (pluginHandle) {
      sfutest = pluginHandle;

      Janus.log("  -- This is a publisher/manager");

      if (pin) {
        var register = {
          request: "join",
          room: room,
          ptype: "publisher",
          pin: pin,
          display: username || ""
        };
      } else {
        var register = {
          request: "join",
          room: room,
          ptype: "publisher",
          display: username || ""
        };
      }

      // plugin handle에 메시지를 전송
      sfutest.send({ message: register });
    },

    // plugin handle을 받지 못했을 때 호출되는 콜백 함수
    error: function (error) {
      Janus.log("  -- Error attaching plugin...", error);
      callback(sfutest, "error", error);
    },

    consentDialog: function (on) {
      Janus.debug("Consent dialog should be " + (on ? "on" : "off") + " now");

    },

    mediaState: function (medium, on) {
      Janus.log("Janus " + (on ? "started" : "stopped") + " receiving our " + medium);
    },

    webrtcState: function (on) {
      Janus.log("Janus says our WebRTC PeerConnection is " + (on ? "up" : "down") + " now");

    },

    onmessage: function (msg, jsep) {
      Janus.debug(" ::: Got a message (publisher) :::");
      Janus.debug(msg);

      Janus.log("Got message", msg);

      const event = msg.videoroom;
      if (event != undefined && event != null) {
        if (event === "joined") {
          callback(sfutest, "joined", msg)
        } else if (event === "destroyed") {
          Janus.warn("The room has been destroyed!");
          callback(sfutest, "destroyed", event);
        } else if (event === "event") {
          if (msg.error !== undefined && msg.error !== null) {
            callback(sfutest, "error", msg);
          } else if (msg.publishers !== undefined && msg.publishers !== null) {
            callback(sfutest, "publishers", msg);
          } else if (msg["leaving"] !== undefined && msg["leaving"] !== null) {
            callback(sfutest, "leaving", msg);
          } else if (msg["unpublished"] !== undefined && msg["unpublished"] !== null) {
            sfutest.hangup();
            callback(sfutest, "unpublished", msg)
          }
        }
      }

      if (jsep !== undefined && jsep !== null) {
        Janus.debug("Handling SDP as well...");
        Janus.debug(jsep);
        sfutest.handleRemoteJsep({ jsep: jsep });
        // Check if any of the media we wanted to publish has
        // been rejected (e.g., wrong or unsupported codec)
        var audio = msg["audio_codec"];
        if (mystream && mystream.getAudioTracks() && mystream.getAudioTracks().length > 0 && !audio) {
          // Audio has been rejected
          Janus.log("Our audio stream has been rejected, viewers won't hear us");
        }
        var video = msg["video_codec"];
        if (mystream && mystream.getVideoTracks() && mystream.getVideoTracks().length > 0 && !video) {
          Janus.log("Our video stream has been rejected, viewers won't see us");
        }
      }
    },

    onlocaltrack: function (track, on) {
      Janus.debug("Local track " + (on ? "added" : "removed") + ":", track);
      let trackId = track.id.replace(/[{}]/g, "");

      if (!on) {
        // Track removed, get rid of the stream and the rendering
        let stream = localTracks[trackId];

        if (stream) {
          try {
            let tracks = stream.getTracks();
            for (let i in tracks) {
              let mst = tracks[i];
              if (mst !== null && mst !== undefined)
                mst.stop();
            }
          } catch (e) { }
        }

        delete localTracks[trackId];
        return;
      }
      // If we're here, a new track was added
      let stream = localTracks[trackId];
      if (stream) {
        // We've been here already
        return;
      }
      if (track.kind === "audio") {
        // We ignore local audio tracks, they'd generate echo anyway
      } else {
        stream = new MediaStream([track]);
        localTracks[trackId] = stream;
        Janus.log("Created local stream:", stream);
        Janus.log(stream.getTracks());
        Janus.log(stream.getVideoTracks());
        callback(sfutest, "onlocaltrack", stream);
      }
    },

    onremotetrack: function (track, mid, on) {
      // The publisher stream is sendonly, we don't expect anything here
    },

    oncleanup: function () {
      Janus.log(" ::: Got a cleanup notification: we are unpublished now :::");
      callback(sfutest, "oncleanup");
    }
  }
  );

  return sfutest;
}


export function publishOwnFeed(sfutest, useAudio) {
  let tracks = [];

  if (useAudio)
    tracks.push({ type: 'audio', capture: true, recv: false });
  tracks.push({
    type: 'video', capture: true, recv: false,
    // We may need to enable simulcast or SVC on the video track
    simulcast: false,
  });

  console.log("::: tracks :::", tracks)

  sfutest.createOffer(
    {
      tracks: tracks,


      success: function (jsep) {
        Janus.debug("Got publisher SDP!", jsep);
        let publish = { request: "configure", audio: useAudio, video: true };

        sfutest.send({ message: publish, jsep: jsep });
      },
      error: function (error) {
        Janus.error("WebRTC error:", error);
        if (useAudio) {
          publishOwnFeed(sfutest, false);
        } else {
          Janus.log("Error publishing feed: " + error);
        }
      }
    });
}


export function unpublishOwnFeed(sfutest) {
  // Unpublish our stream
  var unpublish = { "request": "unpublish" };
  sfutest.send({ "message": unpublish });
  // sfutest.hangup();
}