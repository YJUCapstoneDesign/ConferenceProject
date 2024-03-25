import Janus from "janus-gateway"; // janus-gateway 라이브러리를 import 합니다.
import React, { Fragment, useEffect, useState } from 'react';
import JanusComponent from '../janus/JanusComponent';
import JanusPublisher from '../janus/JanusPublisher';
import JanusSubscriber from '../janus/JanusSubscriber';
import JanusVideoRoom from '../janus/JanusVideoRoom';
import JanusPlayer from '../janus/JanusPlayer';

const janusServer = process.env.REACT_JANUS_SERVER;
const springServer = process.env.REACT_SPRING_SERVER; // 서버 주소

const opaqueId = "videoroomtest-" + Janus.randomString(12); // 고유 아이디 생성

const VideoComponent = () => {
    console.log(process.env.REACT_JANUS_SERVER); //테스트를 위해 자누스 서버 주소를 출력

    const [room, setRoom] = React.useState('');
    const [username, setUsername] = React.useState('');
    const [pubId, setPubId] = React.useState(null);
    const [pubPvtId, setPubPvtId] = React.useState(null);
    const [show, setShow] = React.useState(false);

    const handleShow = () => {

        if (!room || !username) {
            alert("Room Id와 Username을 입력해주세요.");
            return;
        }
        if (!Number.isInteger(Number(room))) {
            alert("Room Id는 숫자만 입력 가능합니다.");
            return;
        }
        setRoom(Number(room));
        setShow(true);
    }

    const handleRoom = (event) => {
        const value = event.target.value;
        if (/^\d*$/.test(value)) {
            setRoom(value);
        }
    };

    const handleUsername = (e) => {
        setUsername(e.target.value);
    }



    return (
        <Fragment>
            <div>
                <h2>방 참여</h2>
                <label htmlFor="roomId">Room Id</label>{": "}
                <input type="text" id="roomId" value={room} name="roomId" onChange={handleRoom} /><br />
                <label htmlFor="username">Username</label>{": "}
                <input type="text" id="username" value={username} name="username" onChange={handleUsername} /><br />
                <button onClick={handleShow}>Join</button>
            </div>
            {show &&
                <JanusComponent server={janusServer}>
                    <JanusVideoRoom>
                        <JanusPublisher
                            opaqueId={opaqueId}
                            room={room}
                            username={username}
                            // setRoom={setRoom}

                            setPubId={setPubId}
                            setPubPvtId={setPubPvtId}
                        >
                            <JanusPlayer readyText="Something" />
                        </JanusPublisher>
                        <JanusSubscriber
                            opaqueId={opaqueId}
                            room={room}
                            pubId={pubId}
                            pubPvtId={pubPvtId}
                        >
                            <JanusPlayer readyText="Something" />
                        </JanusSubscriber>
                    </JanusVideoRoom>
                    {/* Janus Chat 부분이 들어올 곳 */}
                </JanusComponent>
            }
        </Fragment>)
}

export default VideoComponent;