import Janus from "janus-gateway"; // janus-gateway 라이브러리를 import 합니다.
import React, { Fragment, useEffect, useState } from 'react';
import Video from "./Video";

const janusServer = process.env.JANUS_SERVER;
const springServer = process.env.SPRING_SERVER;



const connectFeed = (feed) => {
    setFeeds((prevFeeds) => [...prevFeeds, feed]);
};

const disconnectFeed = (feed) => {
    setFeeds((prevFeeds) => prevFeeds.filter((f) => f.rfid !== feed.rfid));
};

const VideoComponent = () => {

    const [mainStream, setMainStream] = useState({});
    const [feeds, setFeeds] = useState([]);
    const [myFeed, setMyFeed] = useState({});
    const [receiveChat, setReceiveChat] = useState("");
    const [activeVideo, setActiveVideo] = useState(true);
    const [activeAudio, setActiveAudio] = useState(true);
    const [activeSpeaker, setActiveSpeaker] = useState(false);
    const [activeSharing, setActiveSharing] = useState(false);
    const [receiveFile, setReceiveFile] = useState(null);

    let roomId = 5678; // 임시 방번호
    let storePlugin = null; // publisher plugin handle
    let pin = "5678"; // 임시 비밀번호
    let username = "username-" + Janus.randomString(5); // 임시 유저네임

    // axios를 통해 방 번호를 받는다.
    useEffect(() => {
        let janus = null;
        let opaqueId = "videoroomtest-" + Janus.randomString(12); // 개인 식별자
        let mystream = null; // 내 스트림

        Janus.init({
            debug: "all", // 로그를 모두 출력합니다.
            // callback 은 Janus.init 이 성공적으로 실행되면 실행됩니다.
            callback: function () {
                if (!Janus.isWebrtcSupported()) {
                    Janus.log("WebRTC is not supported, can't do much here");
                    return;
                }
                // Create session
                janus = new Janus({
                    server: janusServer,
                    success: function () {
                        //VideoRoom plugin을 연결
                        janus.attach({
                            plugin: "janus.plugin.videoroom",
                            opaqueId: opaqueId,
                            success: function (pluginHandle) {
                                storePlugin = pluginHandle;
                                if (pin) { // pin이 있을 경우
                                    var register = {
                                        request: "join",
                                        room: roomId,
                                        ptype: "publisher",
                                        display: username,
                                        pin: pin,
                                    };
                                } else {
                                    var register = { // pin이 없을 경우
                                        request: "join",
                                        room: roomId,
                                        ptype: "publisher",
                                        display: username,
                                    };
                                }
                                storePlugin.send({ message: register });
                            }, // attach 성공시 실행
                            error: function (error) { // attach 실패시 실행
                                Janus.error("  -- Error attaching plugin...", error);
                                Janus.log("Error attaching plugin... " + error);
                            },
                            consentDialog: function (on) { // 사용자에게 카메라와 마이크 사용에 대한 동의를 받을 때 실행
                                // getusermedia 호출 되기전 true
                                // 호출되고 false
                                Janus.debug(
                                    "Consent dialog should be " + (on ? "on" : "off") + " now"
                                );
                            },
                            iceState: function (state) { // ICE 상태가 변경되었을 때 실행
                                Janus.log("ICE state changed to " + state);
                            },
                            mediaState: function (medium, on) { // 미디어 상태가 변경되었을 때 실행
                                Janus.log(
                                    "Janus " +
                                    (on ? "started" : "stopped") +
                                    " receiving our " +
                                    medium
                                );
                            },
                            webrtcState: function (on) { // WebRTC 상태가 변경되었을 때 실행
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
                            onmessage: function (msg, jsep) { // 메시지를 받았을 때 실행
                                Janus.debug(" ::: Got a message (publisher) :::", msg);
                                console.log("onmessage 수신", msg);
                                var event = msg["videoroom"];
                                Janus.debug("Event : " + event);

                                if (event != undefined && event != null) {
                                    if (event != "joined") {
                                        setMyFeed(() => ({
                                            id: msg["id"],
                                            display: msg["display"],
                                            pvtid: msg["private_id"],
                                        }));

                                        Janus.log(
                                            "Successfully joined room " +
                                            msg["room"] +
                                            " with ID " +
                                            myFeed.id
                                        );

                                        // SDP offer과 Publisher로 등록 진행
                                        publishOwnFeed(true);

                                        // 기존 접속자 목록을 가져옴
                                        if (msg['publishers']) {
                                            // 없으면 빈 리스트로 옴
                                            let list = msg['publishers'];
                                            Janus.debug("Got a list of available publishers/feeds:", list);

                                            for (let f in list) {
                                                let id = list[f]["id"];
                                                let display = list[f]["display"];
                                                let audio = list[f]["audio_codec"];
                                                let video = list[f]["video_codec"];

                                                Janus.debug(
                                                    "  >> [" +
                                                    id +
                                                    "] " +
                                                    display +
                                                    " (audio: " +
                                                    audio +
                                                    ", video: " +
                                                    video +
                                                    ")"
                                                );
                                                // 모두 Subscribe 진행
                                                newRemoteFeed(id, display, audio, video);
                                            }
                                        } else if (event === "destroyed") {
                                            Janus.warn("The room has been destroyed!");
                                            // 홈페이지로 이동
                                            navigator.push("/");
                                        } else if (event === "event") {
                                            // 새로운 Publisher 접속시
                                            if (msg["publishers"]) {
                                                var list = msg["publishers"];
                                                Janus.debug(
                                                    "Got a list of available publishers/feeds:",
                                                    list
                                                );
                                            }

                                            for (var f in list) {
                                                var id = list[f]["id"];
                                                var display = list[f]["display"];
                                                var audio = list[f]["audio_codec"];
                                                var video = list[f]["video_codec"];
                                                Janus.debug(
                                                    "  >> [" +
                                                    id +
                                                    "] " +
                                                    display +
                                                    " (audio: " +
                                                    audio +
                                                    ", video: " +
                                                    video +
                                                    ")"
                                                );
                                                // 모두 Subscribe 진행unpublish
                                                newRemoteFeed(id, display, audio, video);
                                            }
                                        } else if (msgp['leaving']) {
                                            var leaving = msg["leaving"];
                                            if (msg["leaving"] === "ok") {
                                                return;
                                            }
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

                                        } else if (msg["unpublished"]) {
                                            let unpublished = msg["unpublished"];
                                            if (unpublished === "ok") {
                                                return;
                                            }

                                        } else if (msg["error"]) {
                                            // 426 -> 방 x
                                            alert(msg["error"]);
                                        }

                                    }
                                }
                                if (jsep !== undefined && jsep !== undefined) {
                                    storePlugin.handleRemoteJsep({ jsep: jsep });
                                    var audio = msg["audio_codec"];
                                    if (
                                        mystream &&
                                        mystream.getAudioTracks() &&
                                        mystream.getAudioTracks().length > 0 &&
                                        !audio
                                    ) {
                                        Janus.log(
                                            "Our audio stream has been rejected, viewers won't hear us"
                                        );
                                    }
                                    // Video가 거절당할시
                                    var video = msg["video_codec"];
                                    if (
                                        mystream &&
                                        mystream.getVideoTracks() &&
                                        mystream.getVideoTracks().length > 0 &&
                                        !video
                                    ) {
                                        Janus.log(
                                            "Our video stream has been rejected, viewers won't see us"
                                        );
                                    }
                                }

                            },
                            onlocalstream: function (stream) { // 로컬 스트림을 받았을 때 실행
                                Janus.debug(" ::: Got a local stream :::", stream);
                                mystream = stream; // 내 스트림 저장
                                setMyFeed((prev) => ({ ...prev, stream: stream }));

                                if (
                                    storePlugin.webrtcStuff.pc.iceConnectionState !== "completed" &&
                                    storePlugin.webrtcStuff.pc.iceConnectionState !== "connected"
                                ) {
                                    // 아직 연결 중인 상태
                                }
                            },
                            onremotestream: function (stream) { // 원격 스트림을 받았을 때 실행
                            },
                            ondataopen: function (data) {
                                console.log("data channel opened");
                            },
                            onlocaltrack: function (track, added) { // 로컬 트랙을 받았을 때 실행
                            },
                            onremotetrack: function (track, mid, added) { // 원격 트랙을 받았을 때 실행
                            },
                            ondata: function (data) { // 데이터를 받았을 때 실행
                                // empty
                                console.log("내가받은메시지====\n", data);
                            },
                            oncleanup: function () { // 정리가 필요할 때 실행
                                Janus.log(
                                    " ::: Got a cleanup notification: we are unpublished now :::"
                                );
                                mystream = null;
                            },
                            detached: function () { // 연결이 끊겼을 때 실행
                            }
                        })
                    }, // 성공적으로 세션을 생성시 실행
                    error: function (error) {
                        Janus.error(error); // 에러 발생시 실행
                    }, // 에러 발생시 실행
                    destroyed: function () {
                        Janus.log("Janus session destroyed");
                    } // 세션이 파괴되었을 때 실행
                });
            },
        }); // Janus.init 실행




        function publishOwnFeed(useAudio) {
            storePlugin.createOffer({
                media: {
                    data: true,
                    audioRecv: false,
                    videoRecv: false,
                    audioSend: useAudio,
                    videoSend: true,
                }, // Publishers are sendonly
                // simulcast: doSimulcast,
                // simulcast2: doSimulcast2,
                success: function (jsep) {
                    Janus.debug("Got publisher SDP!", jsep);
                    var publish = {
                        request: "configure",
                        audio: useAudio,
                        video: true,
                    };
                    storePlugin.send({ message: publish, jsep: jsep });
                },
                error: function (error) {
                    Janus.error("WebRTC error:", error);
                    console.log("webrtc error:", error);
                    // 에러 발생시 다시 시도
                    publishOwnFeed(useAudio);
                },
            });
        }
        // 새로운 원격 피드를 추가
        // 새 피드가 구독되었으므로, 새 플러그인 만들고 remote 연결
        function newRemoteFeed(id, display, audio, video) {
            let remoteFeed = null;
            janus.attach({
                plugin: "janus.plugin.videoroom",
                opaqueId: opaqueId,
                success: function (pluginHandle) {
                    remoteFeed = pluginHandle;
                    remoteFeed.simulcastStarted = false;
                    Janus.log(
                        "Plugin attached! (" +
                        remoteFeed.getPlugin() +
                        ", id=" +
                        remoteFeed.getId() +
                        ")"
                    );
                    Janus.log("  -- This is a subscriber");
                    let subscribe = {
                        request: "join",
                        room: roomId,
                        ptype: "subscriber",
                        feed: id,
                        private_id: myFeed.mypvtid,
                    };
                    remoteFeed.videoCodec = video;
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
                            remoteFeed.rfid = msg["id"];
                            remoteFeed.rfdisplay = msg["display"];
                            connectFeed(remoteFeed);
                            Janus.log(
                                "Successfully attached to feed " +
                                remoteFeed.rfid +
                                " (" +
                                remoteFeed.rfdisplay +
                                ") in room " +
                                msg["room"]
                            );
                        } else if (event === 'event') {
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
                            media: { data: true, audioSend: false, videoSend: false }, // We want recvonly audio/video
                            success: function (jsep) {
                                Janus.debug("Got SDP!", jsep);
                                var body = { request: "start", room: roomId };
                                remoteFeed.send({ message: body, jsep: jsep });
                            },
                            error: function (error) {
                                Janus.error("WebRTC error:", error);
                            },

                        });
                    }
                },
                iceState: function (state) {
                    Janus.log("ICE state of this WebRTC PeerConnection (feed #" + remoteFeed.rfid + ") changed to " + state);
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
                onlocalstream: function (stream) {
                    // The subscriber stream is recvonly, we don't expect anything here
                },
                onremotestream: function (stream) {
                    Janus.debug("Remote feed #" + remoteFeed.rfid + ", stream:", stream);

                    setFeeds((prev) => {
                        let findIndex = prev.findIndex((f) => f.rfid === remoteFeed.rfid);
                        let newFeed = [...prev];
                        newFeed[findIndex].stream = stream;
                        newFeed[findIndex].hark = createSpeechEvents(stream);
                        return newFeed;
                    });
                    // remoteFeed.stream = stream;
                    var videoTracks = stream.getVideoTracks();
                    if (!videoTracks || videoTracks.length === 0) {
                        // 원격 비디오 없는 경우
                    } else {
                        // 있는 경우 뭐 별도 버튼처리
                    }
                },
                oncleanup: function () {
                    Janus.log(
                        " ::: Got a cleanup notification (remote feed " + id + ") :::"
                    );
                    // 원격피드 끊기는 경우 처리
                    console.log("다른 사람이 떠나갔습니다.");
                    disconnectFeed(remoteFeed);
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
                    } else {
                        // private message
                        //TODO : private message
                    }
                },
            });
        }



        return () => {
            console.log("언마운트");
            if (janus && janus.isConnected()) {
                const unpublish = {
                    request: "unpublish",
                };
                storePlugin.send({
                    message: unpublish,
                    success: () => {
                        // dispatch(exitRoom());
                        janus.destroy();
                    },
                });
            }
        };
    }, []);

    const renderRemoteVideos = feeds.map((feed) => {

        return (
            <div
                key={feed.rfid}
                style={{
                    width: "100px",
                    height: "100px",
                    float: "left",
                    margin: "3px",
                }}>
                Hello VideoComponent
            </div>
        );
    });

    return (
        <Fragment>
            <div>
                <div
                    style={{
                        width: "100%",
                    }}
                >
                    <div style={{ width: "15%", float: "left" }}>
                        {/* <UserList
                            feeds={feeds}
                            username={username}
                            sendPrivateMessage={sendPrivateMessage}
                        /> */}
                    </div>
                    {/* <div style={{ width: "60%", float: "left" }}>
                        <Video
                            stream={mainStream.stream}
                            username={mainStream.username}
                            muted={true}
                        />
                    </div> */}
                    <div style={{ width: "25%", float: "right", height: "100%" }}>
                        {/* <Chatting
                            sendChatData={sendChatData}
                            receiveChat={receiveChat}
                            transferFile={transferFile}
                            receiveFile={receiveFile}
                        /> */}
                    </div>
                </div>
                {/* <div style={{ float: "left" }}>
                    <button onClick={handleAudioActiveClick}>
                        {activeAudio ? "소리 끄기" : "소리 켜기"}
                    </button>
                    <button onClick={handleVideoActiveClick}>
                        {activeVideo ? "비디오 끄기" : "비디오 켜기"}
                    </button>
                    <button onClick={handleSpeakerActiveClick}>
                        {activeSpeaker ? "화자 추적 비활성화" : "화자 추적 활성화"}
                    </button>
                    <button onClick={handleSharingActiveClick}>
                        {activeSharing ? "화면 공유 비활성화" : "화면 공유 활성화"}
                    </button>
                </div> */}
                <div
                    style={{
                        width: "100%",
                        overflowX: "scroll",
                        whiteSpace: "nowrap",
                    }}
                >
                    <div
                        style={{
                            width: "100px",
                            height: "100px",
                            float: "left",
                            margin: "3px",
                        }}
                    >
                        {myFeed && (
                            <Video
                                stream={myFeed.stream}
                                // onClick={handleMainStream}
                                username={username}
                                muted={false}
                            // activeSpeaker={activeSpeaker}
                            />
                        )}
                    </div>
                    {renderRemoteVideos}
                </div>
            </div>
        </Fragment>
    );
}

export default VideoComponent;