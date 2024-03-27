import Janus from "janus-gateway";


export function subscribeRemoteFeed(janus, opaqueId, room, pvtId, display, audio, video, feeds, callback) {
  let remoteFeed = null;
  let subscription = [];

  // feeds의 안의 streams 배열의 stream.id를 streams 배열에 넣어준다
  for (let f in feeds) {
    for (let i in feeds[f].streams) {
      let stream = feeds[f].streams[i];
      // If we're going to subscribe to a simulcast stream, ask for all available layers
      if (stream.type === "video" && Janus.webRTCAdapter.browserDetails.browser === "safari" &&
        ((stream.codec === "vp8" && !Janus.safariVp8))) {
        continue;
      }

      subscription.push({ feed: stream.id });
    }
  }

  janus.attach({
    plugin: "janus.plugin.videoroom",
    opaqueId: opaqueId,

    success: function (pluginHandle) {
      remoteFeed = pluginHandle;
      remoteFeed.simulcastStarted = false;

      Janus.log("Plugin attached! (" + remoteFeed.getPlugin() + ", id=" + remoteFeed.getId() + ")");
      Janus.log("  -- This is a subscriber");

      var subscribe = {
        "request": "join",
        "room": room,
        "ptype": "subscriber",
        "streams": subscription,
        "use_msid": true,
        "private_id": pvtId,
      };

      remoteFeed.videoCodec = video;
      remoteFeed.send({ "message": subscribe });
    },

    error: function (error) {
      Janus.error("  -- Error attaching plugin...", error);
      callback(remoteFeed, "error", error);
    },

    onmessage: function (msg, jsep) {
      Janus.debug(" ::: Got a message (subscriber) :::");
      Janus.debug(msg);

      if (jsep !== undefined && jsep !== null) {
        Janus.debug("SUBS: Handling SDP as well...");
        Janus.debug(jsep);

        remoteFeed.createAnswer(
          {
            jsep: jsep,
            // Add data:true here if you want to subscribe to datachannels as well
            // (obviously only works if the publisher offered them in the first place)
            media: { audioSend: false, videoSend: false },	// We want recvonly audio/video
            success: function (jsep) {
              Janus.debug("Got SDP!");
              Janus.debug(jsep);
              var body = { "request": "start", "room": room };
              remoteFeed.send({ "message": body, "jsep": jsep });
            },
            error: function (error) {
              Janus.error("WebRTC error:", error);
            }
          });
      }
    },

    webrtcState: function (on) {
      Janus.log("Janus says this WebRTC PeerConnection (feed #" + remoteFeed.rfid + ") is " + (on ? "up" : "down") + " now");
    },

    onlocaltrack: function (track, on) {
      // The subscriber stream is recvonly, we don't expect anything here
    },

    onremotetrack: function (track, mid, on, metadata) {
      Janus.debug("remote track (mid=" + mid + ") " +
        (on ? "added" : "removed") +
        (metadata ? " (" + metadata.reason + ") " : "") + ":", track
      );
      if (!on) {
        return;
      }

      let stream = null;
      // If we're here, a new track was added
      if (track.kind === "audio") {
        stream = new MediaStream([track]);
        // remoteFeed.remoteTracks[mid] = stream;
        Janus.log("Created remote audio stream:", stream);

      } else {

        stream = new MediaStream([track]);
        // remoteFeed.remoteTracks[mid] = stream;
        Janus.log("Created remote video stream:", stream);

      }
      remoteFeed.rfid = stream.id;
      remoteFeed.rfdisplay = stream.display;
      callback(remoteFeed, "onremotetrack", stream);
    },

    oncleanup: function () {
      callback(remoteFeed, "oncleanup");
    }
  })

  return remoteFeed;
}