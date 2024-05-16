import React, { createRef, useEffect, useState, useRef } from "react";
import { Janus } from "janus-gateway";
import Video from "./Video/Video";
import hark from "hark";
import './VideoComponent.css';
import Sidebar from "../Sidebar";
import Janusbutton from "../janusbutton";
import Chatting from "./chatting/chatting";

const useReference = () => {
  const [reference, setReference] = useState(() => createRef());
  return reference;
};

let myroom = 12341234; // demo room
let sfutest = null;
let username = "username-" + Janus.randomString(5); // 임시 유저네임
let receivedFileChunk = {};
let localTracks = {};

const VideoComponent = (props) => {
  const [mainStream, setMainStream] = useState({});
  const [feeds, setFeeds] = useState([]);
  const [myFeed, setMyFeed] = useState({});
  const [receiveChat, setReceiveChat] = useState("");
  const [activeVideo, setActiveVideo] = useState(true);
  const [activeAudio, setActiveAudio] = useState(true);
  const [activeSpeaker, setActiveSpeaker] = useState(false);
  const [activeSharing, setActiveSharing] = useState(false);
  const [receiveFile, setReceiveFile] = useState(null);

  const connectFeed = (feed) => {
    setFeeds((prevFeeds) => [...prevFeeds, feed]);
  };

  const disconnectFeed = (feed) => {
    setFeeds((prevFeeds) => prevFeeds.filter((f) => f.rfid !== feed.rfid));
  };

  const createSpeechEvents = (stream) => {
    if (!stream || stream == null || stream == undefined || stream.getAudioTracks().length === 0) return;
    let speechEvents = hark(stream, {});
    return speechEvents;
  };

  const handleMainStream = (stream, username) => {
    if (mainStream.username === username) return;
    setMainStream(() => {
      return {
        stream: stream,
        username: username,
      };
    });
  };


  useEffect(() => {
    // 윈도우 새로고침 방지
    window.addEventListener("beforeunload", function (e) {
      e.preventDefault();
      e.returnValue = "";
    });


    let servers = [
      // process.env.REACT_APP_JANUS_GATEWAY_HTTP,
      process.env.REACT_APP_JANUS_GATEWAY_HTTPS,
    ];
    let opaqueId = "videoroomtest-" + Janus.randomString(12); // 개인 식별
    let janus = null;
    let subscriber_mode = false; // true면 비디오 열어줌
    let mystream = null;
    if (getQueryStringValue("room") !== "")
      myroom = parseInt(getQueryStringValue("room"));

    let doSimulcast = true; // 동시 캐스트
    // let doSimulcast2 = false;

    Janus.init({
      debug: "all",
      // dependencies: Janus.useDefaultDependencies(),
      callback: function () {
        janus = new Janus({
          server: servers,
          success: function () {
            janus.attach({
              plugin: "janus.plugin.videoroom",
              opaqueId: opaqueId,
              success: function (pluginHandle) {
                sfutest = pluginHandle;
                Janus.log(
                  "Plugin attached! (" +
                  sfutest.getPlugin() +
                  ", id=" +
                  sfutest.getId() +
                  ")"
                );
                Janus.log("  -- This is a publisher/manager");

                // 자동 입장처리
                sfutest.send({
                  message: {
                    request: "join",
                    room: myroom,
                    ptype: "publisher",
                    display: username,
                  },
                });
              },
              error: function (cause) {
                // Error, can't go on...
                console.log("error", cause);
              },
              consentDialog: function (on) {
                // getusermedia 호출 되기전 true
                // 호출되고 false
                Janus.debug(
                  "Consent dialog should be " + (on ? "on" : "off") + " now"
                );
              },
              iceState: function (state) {
                Janus.log("ICE state changed to " + state);
              },
              mediaState: function (medium, on) {
                Janus.log(
                  "Janus " +
                  (on ? "started" : "stopped") +
                  " receiving our " +
                  medium
                );
              },
              webrtcState: function (on) {
                Janus.log(
                  "Janus says our WebRTC PeerConnection is " +
                  (on ? "up" : "down") +
                  " now"
                );
                if (!on) {
                  // 꺼짐 처리
                  return;
                }
              },
              onmessage: function (msg, jsep) {
                Janus.debug(" ::: Got a message (publisher) :::", msg);
                console.log("onmessage 수신", msg);
                var event = msg["videoroom"];
                Janus.debug("Event : " + event);
                if (event) {
                  if (event === "joined") {
                    setMyFeed(() => ({
                      id: msg["id"],
                      pvtid: msg["private_id"],
                    }));
                    // myid = msg["id"];
                    // mypvtid = msg["private_id"];
                    Janus.log(
                      "Successfully joined room " +
                      msg["room"] +
                      " with ID " +
                      myFeed.id
                    );
                    if (subscriber_mode) {
                      // 비디오 숨김
                    } else {
                      publishOwnFeed(true);
                    }

                    // 기존의 접속자 확인
                    if (msg["publishers"]) {
                      // 없으면 빈 리스트로 옴
                      let list = msg["publishers"];
                      Janus.debug(
                        "Got a list of available publishers/feeds:",
                        list
                      );
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
                        // feedStreams[id] = streams;
                        Janus.debug("  >> [" + id + "] " + display + ":", streams);
                        newRemoteFeed(id, display, streams);
                      }
                    }
                  } else if (event === "destroyed") {
                    // 룸 삭제 이벤트
                    Janus.warn("The room has been destroyed!");
                    alert("룸파괴");
                  } else if (event === "event") {
                    // 새로운 접속자가 있으면
                    if (msg["publishers"]) {
                      let list = msg["publishers"];
                      Janus.debug(
                        "Got a list of available publishers/feeds:",
                        list
                      );
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
                        // feedStreams[id] = streams;
                        Janus.debug("  >> [" + id + "] " + display + ":", streams);
                        newRemoteFeed(id, display, streams);
                      }
                    } else if (msg["leaving"]) {
                      var leaving = msg["leaving"];
                      Janus.log("Publisher left: " + leaving);
                      var remoteFeed = null;
                      for (var i = 0; i < feeds.length; i++) {
                        if (feeds[i] && feeds[i].rfid === leaving) {
                          remoteFeed = feeds[i];
                          break;
                        }
                      }
                      if (remoteFeed != null) {
                        // 나간 피드 처리
                        Janus.debug(
                          "Feed " +
                          remoteFeed.rfid +
                          " (" +
                          remoteFeed.rfdisplay +
                          ") has left the room, detaching"
                        );
                        // ++ 해당 비디오 닫아주는 코드
                        disconnectFeed(remoteFeed);
                        remoteFeed.detach();
                      }
                    } else if (msg["error"]) {
                      // 426 코드 방 X
                      alert(msg["error"]);
                    }
                  }
                }

                if (jsep) {
                  console.log("jsep =============", msg);
                  sfutest.handleRemoteJsep({ jsep: jsep });
                  var audio = msg["audio_codec"];
                  if (
                    mystream &&
                    mystream.getAudioTracks() &&
                    mystream.getAudioTracks().length > 0 &&
                    !audio
                  ) {
                    // 오디오 뮤트한 경우
                    console.log(
                      "Our audio stream has been rejected, viewers won't hear us"
                    );
                  }
                  var video = msg["video_codec"];
                  if (
                    mystream &&
                    mystream.getVideoTracks() &&
                    mystream.getVideoTracks().length > 0 &&
                    !video
                  ) {
                    // 비디오 가린경우
                    console.log(
                      "Our video stream has been rejected, viewers won't see us"
                    );
                  }
                }
              },
              onlocaltrack: function (track, on) {
                Janus.debug("Local track " + (on ? "added" : "removed") + ":", track);
                let trackId = track.id.replace(/[{}]/g, "");

                if (!on) {
                  return;
                }

                let stream = localTracks[trackId];
                Janus.debug(" ::: Got a local stream :::", stream);

                if (stream) {
                  setMyFeed((prev) => ({
                    ...prev,
                    stream: stream,
                  }));
                  return;
                }

                if (track.kind === "audio") {
                  
                } else {
                  stream = new MediaStream([track]);
                  localTracks[trackId] = stream;
                }

                setMyFeed((prev) => ({
                  ...prev,
                  stream: stream,
                }));

                if (
                  sfutest.webrtcStuff.pc.iceConnectionState !== "completed" &&
                  sfutest.webrtcStuff.pc.iceConnectionState !== "connected"
                ) {
                  // 아직 연결 중인 상태
                }

                // let videoTracks = stream.getVideoTracks();
                // if (!videoTracks || videoTracks.length === 0) {
                //   // 웹캠 없는 경우 비디오 숨김처리
                // } else {
                //   // 비디오 보여줌
                // }
              },

              onremotetrack: function (track, mid, on, metadata) {
                // console.log("onremotetrack", track, mid, added, metadata);
              },

              ondataopen: function (data) {
                console.log("data channel opened");
              },
              ondata: function (data) {
                // empty
                console.log("내가받은메시지====\n", data);
              },
              oncleanup: function () {
                // 피어커넥션 플러그인 닫혔을 때
                Janus.log(
                  " ::: Got a cleanup notification: we are unpublished now :::"
                );
                mystream = null;
              },
            });
          },
        });
      },
      error: function (error) {
        Janus.error(error);
      },
      destroyed: function () {
        // I should get rid of this
        console.log("destroyed");
      },
    });

    function publishOwnFeed(useAudio) {
      let tracks = [];

      if (useAudio) {
        tracks.push({
          type: 'audio', capture: true, recv: false,
        });
      }

      tracks.push({
        type: 'video', capture: true, recv: false,
        // We may need to enable simulcast or SVC on the video track
        simulcast: doSimulcast,
      });

      tracks.push({ type: 'data' }); // ondata used for chat


      sfutest.createOffer({
        tracks: tracks,

        success: function (jsep) {
          Janus.debug("Got publisher SDP!", jsep);
          let publish = { request: "configure", audio: useAudio, video: true };

          sfutest.send({ message: publish, jsep: jsep, });
        },
        error: function (error) {
          Janus.error("WebRTC error:", error);
          if (useAudio) {
            publishOwnFeed(false); // 오디오 꺼서 다시 보냄
          } else {
            // // 오디오 켜서 다시 보낼 수도 있음 publishOwnFeed(true);
            alert("WebRTC error... " + JSON.stringify(error));
            publishOwnFeed(true);
          }
        },
      });
    }

    function newRemoteFeed(id, display, streams) {
      // A new feed has been published, create a new plugin handle and attach to it as a subscriber
      // 새 피드가 구독되었으므로, 새 플러그인 만들고 remote 연결

      let remoteFeed = {};
      janus.attach({
        plugin: "janus.plugin.videoroom",
        opaqueId: opaqueId,
        success: function (pluginHandle) {
          remoteFeed = pluginHandle;
          remoteFeed.remoteTracks = {};
          remoteFeed.remoteVideos = 0;
          remoteFeed.simulcastStarted = false;
          remoteFeed.svcStarted = false;
          Janus.log("Plugin attached! (" + remoteFeed.getPlugin() + ", id=" + remoteFeed.getId() + ")");
          Janus.log("  -- This is a subscriber");

          let subscription = [];

          for (let i in streams) {
            let stream = streams[i];
            // If the publisher is VP8/VP9 and this is an older Safari, let's avoid video
            if (stream.type === "video" && Janus.webRTCAdapter.browserDetails.browser === "safari" &&
              ((stream.codec === "vp9" && !Janus.safariVp9) || (stream.codec === "vp8" && !Janus.safariVp8))) {
              toastr.warning("Publisher is using " + stream.codec.toUpperCase +
                ", but Safari doesn't support it: disabling video stream #" + stream.mindex);
              continue;
            }
            subscription.push({
              feed: stream['id'],	// This is mandatory
              mid: stream['mid']		// This is optional (all streams, if missing)
            });
            // FIXME Right now, this is always the same feed: in the future, it won't
            remoteFeed.rfid = stream['id'];
            remoteFeed.rfdisplay = stream['display'];
          }


          let subscribe = {
            request: "join",
            room: myroom,
            ptype: "subscriber",
            streams: subscription,
            private_id: myFeed.mypvtid,
          };

          remoteFeed.send({ message: subscribe });
        },
        error: function (error) {
          Janus.error("  -- Error attaching plugin...", error);
        },
        onmessage: function (msg, jsep) {
          Janus.debug(" ::: Got a message (subscriber) :::", msg);
          var event = msg["videoroom"];
          Janus.debug("Event: " + event);
          if (msg["error"]) {
            console.log(msg["error"]);
          } else if (event) {
            if (event === "attached") {
              // remoteFeed.rfid = msg["id"];
              // remoteFeed.rfdisplay = msg["display"];

              connectFeed(remoteFeed);
              Janus.log(
                "Successfully attached to feed " +
                remoteFeed.rfid +
                " (" +
                remoteFeed.rfdisplay +
                ") in room " +
                msg["room"]
              );
            } else if (event === "event") {
              var substream = msg["substream"];
              var temporal = msg["temporal"];
              if (
                (substream !== null && substream !== undefined) ||
                (temporal !== null && temporal !== undefined)
              ) {
                if (!remoteFeed.simulcastStarted) {
                  remoteFeed.simulcastStarted = true;
                  // addSimulcastButtons(remoteFeed.rfindex, remoteFeed.videoCodec === "vp8" || remoteFeed.videoCodec === "h264");
                }
                // updateSimulcastButtons(remoteFeed.rfindex, substream, temporal);
              }
            } else {
              // What has just happened?
            }
          }
          if (jsep) {
            Janus.debug("Handling SDP as well...", jsep);
            // Answer and attach
            remoteFeed.createAnswer({
              jsep: jsep,
              tracks: [
                { type: 'data' },
              ],
              // media: { data: true, audioSend: false, videoSend: false }, // We want recvonly audio/video
              success: function (jsep) {
                Janus.debug("Got SDP!", jsep);
                var body = { request: "start", room: myroom };
                remoteFeed.send({ message: body, jsep: jsep });
              },
              error: function (error) {
                Janus.error("WebRTC error:", error);
              },
            });
          }
        },
        iceState: function (state) {
          Janus.log(
            "ICE state of this WebRTC PeerConnection (feed #" +
            remoteFeed.rfid +
            ") changed to " +
            state
          );
        },
        webrtcState: function (on) {
          Janus.log(
            "Janus says this WebRTC PeerConnection (feed #" +
            remoteFeed.rfid +
            ") is " +
            (on ? "up" : "down") +
            " now"
          );
        },
        onlocaltrack: function (track, on) { },
        onremotetrack: function (track, mid, on, metadata) {
          Janus.debug(
            "Remote feed #" + remoteFeed.rfindex +
            ", remote track (mid=" + mid + ") " +
            (on ? "added" : "removed") +
            (metadata ? " (" + metadata.reason + ") " : "") + ":", track
          );

          if (!on) {
            delete remoteFeed['remoteTracks'][mid];
            return;
          }

          if (track.kind === "audio") {
            let stream = new MediaStream([track]);
            remoteFeed.remoteTracks[mid] = stream;

            remoteFeed.stream = stream;

            Janus.log("Created remote audio stream:", stream);
            setFeeds((prev) => {
              let findIndex = prev.findIndex((f) => f.rfid === remoteFeed.rfid);
              let newFeed = [...prev];
              newFeed[findIndex].stream = stream;
              // TODO: hark undefined를 처리해야 한다.
              newFeed[findIndex].hark = createSpeechEvents(stream); 
              return newFeed;
            });
          } else {
            let stream = new MediaStream([track]);
            remoteFeed.remoteTracks[mid] = stream;

            Janus.log("Created remote video stream:", stream);
            setFeeds((prev) => {
              let findIndex = prev.findIndex((f) => f.rfid === remoteFeed.rfid);
              let newFeed = [...prev];
              newFeed[findIndex].stream = stream;
              return newFeed;
            });
            // var videoTracks = stream.getVideoTracks();
            // if (!videoTracks || videoTracks.length === 0) {
            //   // 원격 비디오 없는 경우
            // } else {
            //   // 있는 경우 뭐 별도 버튼처리
            // }
          }
        },
        ondataopen: function () {
          console.log("remote datachannel opened");
        },
        ondata: function (data) {
          let json = JSON.parse(data);
          let what = json["textroom"];
          if (what === "message") {
            // public message
            setReceiveChat(() => `${json["display"]} : ${json["text"]}`);
          } else if (what === "file") {
            let from = json["display"];
            let filename = json["text"]["filename"];
            let chunk = json["text"]["message"];
            let last = json["text"]["last"];
            if (!receivedFileChunk[from]) receivedFileChunk[from] = {};
            if (!receivedFileChunk[from][filename]) {
              receivedFileChunk[from][filename] = [];
            }
            receivedFileChunk[from][filename].push(chunk);
            if (last) {
              setReceiveFile(() => {
                return {
                  data: receivedFileChunk[from][filename].join(""),
                  filename: filename,
                  from: from,
                };
              });
              delete receivedFileChunk[from][filename];
            }
          }
        },
        oncleanup: function () {
          Janus.log(
            " ::: Got a cleanup notification (remote feed " + id + ") :::"
          );
          // 원격피드 끊기는 경우 처리
          console.log("다른 사용자 나감 ㅇㅇㅇ");
          disconnectFeed(remoteFeed);
        },
      });
    }

    // Helper to parse query string
    function getQueryStringValue(name) {
      // 쿼리스트링에서 룸네임 찾기
      return myroom;
    }
  }, []);

  // 룸 id, 채팅 내용, 사용자 이름등의 정보가 message 객체에 저장, 
  // sfutest.data를 사용해 WebRTC에 내장되어있는 datachannel을 통해 전송한다.
  const sendChatData = (data) => {
    let message = {
      textroom: "message",
      room: myroom,
      text: data,
      display: username,
    };
    sfutest.data({
      text: JSON.stringify(message),
      error: function (err) {
        console.log(err);
      },
      success: function () {
        console.log("datachannel message sent");
      },
    });
  };

  const transferFile = (data) => {
    let message = {
      textroom: "file",
      room: myroom,
      text: data,
      display: username,
    };
    sfutest.data({
      text: JSON.stringify(message),
      error: function (err) {
        console.log(err);
      },
      success: function () {
        console.log("datachannel file sent...");
      },
    });
  };

  const handleAudioActiveClick = () => {
    let muted = sfutest.isAudioMuted();
    if (muted) sfutest.unmuteAudio();
    else sfutest.muteAudio();
    setActiveAudio(() => !sfutest.isAudioMuted());
  };

  const handleVideoActiveClick = () => {
    let muted = sfutest.isVideoMuted();
    if (muted) sfutest.unmuteVideo();
    else sfutest.muteVideo();
    setActiveVideo(() => !sfutest.isVideoMuted());
  };

  const handleSpeakerActiveClick = () => {
    setActiveSpeaker((prev) => !prev);
  };

  const handleSharingActiveClick = () => {
    if (activeSharing) {
      sfutest.createOffer({
        media: {
          replaceVideo: true,
        },
        success: function (jsep) {
          Janus.debug(jsep);
          sfutest.send({ message: { audio: true, video: true }, jsep: jsep });
        },
        error: function (error) {
          alert("WebRTC error... " + JSON.stringify(error));
        },
      });
    } else {
      if (!Janus.isExtensionEnabled()) {
        alert("확장프로그램 설치해주세요");
        return;
      }
      sfutest.createOffer({
        media: {
          video: "screen",
          replaceVideo: true,
        },
        success: function (jsep) {
          Janus.debug(jsep);
          sfutest.send({ message: { audio: true, video: true }, jsep: jsep });
        },
        error: function (error) {
          alert("WebRTC error... " + JSON.stringify(error));
        },
      });
    }
    setActiveSharing((prev) => !prev);
  };

  useEffect(() => {
    if (activeSpeaker) {
      for (let i = 0; i < feeds.length; i++) {
        if (!feeds[i].hark) continue;
        feeds[i].hark.on("speaking", () => {
          handleMainStream(feeds[i].stream, feeds[i].rfdisplay);
        });
      }
    } else {
      for (let i = 0; i < feeds.length; i++) {
        if (!feeds[i].hark) continue;
        feeds[i].hark.off("speaking");
      }
    }
  }, [activeSpeaker]);

  const renderRemoteVideos = feeds.map((feed) => {
    return (
      <div key={feed.rfid} className="info">
        <div className="small-screen" >
          <Video
            stream={feed.stream}
            onClick={handleMainStream}
            username={feed.rfdisplay}
            muted={false}
          />
        </div>
      </div>
    );
  });

  return (
    <>
      <div id="wrap">
        <div className="side-box">
          <Sidebar />
        </div>
        <div className="main-box">
          <div className="box">
            <div className="big-screen"> {/* 가장 큰 화면의 상대적 위치 설정 부분이였음*/}
              <Video
                stream={mainStream.stream}
                username={mainStream.username}
                muted={true}
              />
            </div>
          </div>
          <div className="info"> {/* 일단 onClick 옮김 서버 연 상태에서만 확인가능 */}
            <div className="small-screen">
              {myFeed && (
                <Video
                  stream={myFeed.stream}
                  username={username}
                  muted={false}
                  onClick={handleMainStream}
                // activeSpeaker={activeSpeaker}
                />
              )}
            </div>
            {renderRemoteVideos}
          </div>
          
          <div className="flex justify-between">
          <div className="button-box">
              <Janusbutton handleAudioActiveClick={handleAudioActiveClick} handleVideoActiveClick={handleVideoActiveClick}  handleSharingActiveClick={handleSharingActiveClick}/>
          </div>
          {/* chatting */}
          <div className="chat">
          <div className="mb-4 mr-4">
            <div className="group inline-block">
              <Chatting
                    sendChatData={sendChatData}
                    receiveChat={receiveChat}
                    transferFile={transferFile}
                    receiveFile={receiveFile}
              />
              <button
                className="outline-none focus:outline-none border px-3 py-3 bg-white flex items-center min-w-1 rounded-3xl">
                <span>
                  <svg
                    className="fill-current h-4 w-4 transform group-hover:-rotate-180
                    transition duration-500 ease-in-out rotate-0"
                    xmlns="http://www.w3.org/2000/svg"  
                    viewBox="0 0 20 20"
                  >
                    <path
                      d="M9.293 12.95l.707.707L15.657 8l-1.414-1.414L10 10.828 5.757 6.586 4.343 8z"
                    />
                  </svg>
                </span>
              </button>
            </div>  
          </div>
        </div>
        </div>
        </div>
        {/* Chatting 컴포넌트 호출 부분 */}
        {/* <div className="chat">
          <div className="mt-4 ml-4">
            <div className="group inline-block">
              <Chatting
                    sendChatData={sendChatData}
                    receiveChat={receiveChat}
                    transferFile={transferFile}
                    receiveFile={receiveFile}
              />
              <button
                className="outline-none focus:outline-none border px-3 py-3 bg-white flex items-center min-w-1 rounded-3xl">
                <span>
                  <svg
                    className="fill-current h-4 w-4 transform group-hover:-rotate-180
                    transition duration-500 ease-in-out rotate-0"
                    xmlns="http://www.w3.org/2000/svg"  
                    viewBox="0 0 20 20"
                  >
                    <path
                      d="M9.293 12.95l.707.707L15.657 8l-1.414-1.414L10 10.828 5.757 6.586 4.343 8z"
                    />
                  </svg>
                </span>
              </button>
            </div>  
          </div>
        </div> */}
      </div>
    </>
  );
};

export default VideoComponent;