import Janus from "janus-gateway";


export function subscribeRemoteFeed(janus, opaqueId, room, id, pvtId, display, audio, video, callback) {
  let remoteFeed = null;

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
        "feed": id,
        "private_id": pvtId,
        "data": true
      };

      if (Janus.webRTCAdapter.browserDetails.browser === "safari" &&
        (video === "vp9" || (video === "vp8" && !Janus.safariVp8))) {
        if (video)
          video = video.toUpperCase()
        subscribe["offer_video"] = false;
      }

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
      Janus.log("Janus says this WebRTC PeerConnection (feed #" + remoteFeed.rfindex + ") is " + (on ? "up" : "down") + " now");
    },

    onlocalstream: function (stream) {
      // The subscriber stream is recvonly, we don't expect anything here
    },

    // TODO: Modify this function remove jquery and use react refs
    onremotetrack: function (track, mid, on, metadata) {
      Janus.debug(
        "Remote feed #" + remoteFeed.rfindex +
        ", remote track (mid=" + mid + ") " +
        (on ? "added" : "removed") +
        (metadata ? " (" + metadata.reason + ") " : "") + ":", track
      );
      let stream = null;
      // If we're here, a new track was added
      if (track.kind === "audio") {
        // New audio track: create a stream out of it, and use a hidden <audio> element
        stream = new MediaStream([track]);
        Janus.log("Created remote audio stream:", stream);
        // Janus.attachMediaStream($('#remotevideo' + remoteFeed.rfindex + '-' + mid).get(0), stream);

      } else {

        stream = new MediaStream([track]);
        Janus.log("Created remote video stream:", stream);
        // Janus.attachMediaStream($('#remotevideo' + remoteFeed.rfindex + '-' + mid).get(0), stream);
        // Note: we'll need this for additional videos too
        // if (!bitrateTimer[remoteFeed.rfindex]) {
        //   $('#curbitrate' + remoteFeed.rfindex).removeClass('hide').removeClass('hide');
        //   bitrateTimer[remoteFeed.rfindex] = setInterval(function () {
        //     if (!$("#videoremote" + remoteFeed.rfindex + ' video').get(0))
        //       return;
        //     // Display updated bitrate, if supported
        //     let bitrate = remoteFeed.getBitrate();
        //     $('#curbitrate' + remoteFeed.rfindex).text(bitrate);
        //   }, 1000);
        // }

      }
      callback(remoteFeed, "onremotetrack", stream);
    },

    oncleanup: function () {
      callback(remoteFeed, "oncleanup");
    }
  })

  return remoteFeed;
}