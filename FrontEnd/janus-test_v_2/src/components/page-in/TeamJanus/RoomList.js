import React, { useState, useEffect } from "react";
import { Link } from "react-router-dom";
import api from "../api";

function RoomList(props) {
  const [error, setError] = useState('');
  const [roomId, setRoomId] = useState(null);
  const [userName, setUserName] = useState(null);

  const teamId = props.teamNumber;

  useEffect(() => {
    const CheckRoom = async () => {
      try {
        const response = await api.get(`/api/room/exist/${teamId}`);
        const { roomId, userName } = response.data;
        setRoomId(roomId);
        setUserName(userName);
      } catch (err) {
        setError("방 조회 실패: " + (err.response?.data?.message || "알 수 없는 오류"));
      }
    };
    CheckRoom();
  }, [teamId]); 

  const CreateRoom = async (event) => {
    event.preventDefault();
    try {
      const response = await api.post(`http://localhost:8080/api/room/create/${teamId}`, {
        teamId, 
      });
      const { roomId } = response.data;
      setRoomId(roomId);
      alert("방 생성 성공");
    } catch (err) {
      setError("방 생성 실패: " + (err.response?.data?.message || "알 수 없는 오류"));
    }
  };

  return (
    <div className="relative flex flex-col min-w-0 break-words bg-white h-custom-height w-custom-size1 mt-4 mb-6 ml-4 shadow-lg rounded 2xl:min-custom-height2 xl:min-h-60 xl:h-custom-height4">
      <div className="rounded-t mb-0 px-4 py-3 border-0">
        <div className="flex flex-wrap items-center">
          <div className="relative w-full px-4 max-w-full flex-grow flex-1">
            <h3 className="font-semibold text-base text-indigo-500">RoomList</h3>
          </div>
          <div className="relative w-32 max-w-full flex-grow flex-1 text-right">
            <button
              className="bg-indigo-500 text-white active:bg-indigo-600 text-xs font-bold uppercase px-3 py-1 rounded outline-none focus:outline-none mb-1 ease-linear transition-all duration-150 mr-5"
              type="button"
              onClick={CreateRoom}
            >
              Create
            </button>
          </div>
        </div>
      </div>

      <div className="block w-full overflow-x-auto">
        <table className="items-center bg-transparent w-full border-collapse">
          <thead>
            <tr>
              <th className="px-8 bg-blueGray-50 text-blueGray-500 align-middle border border-solid border-blueGray-100 py-3 text-xs uppercase border-l-0 border-r-0 whitespace-nowrap font-semibold text-left">
                Content
              </th>
            </tr>
          </thead>

          <tbody>
            {roomId ? (
              <tr>
                <th className="border-t-0 px-6 align-middle border-l-0 border-r-0 text-xs whitespace-nowrap p-4 text-left text-blueGray-700 pl-8 text-indigo-600">
                  Room ID: {roomId}
                </th>
                <td>
                  <Link
                    to={`/video/${roomId}`}
                    className="bg-indigo-500 text-white active:bg-indigo-600 text-xs font-bold uppercase px-3 py-1 rounded-2xl outline-none focus:outline-none mb-1 ease-linear transition-all duration-150 mt-3 ml-24"
                  >
                    Enter
                  </Link>
                </td>
              </tr>
            ) : (
              <tr>
                <th className="border-t-0 px-6 align-middle border-l-0 border-r-0 text-xs whitespace-nowrap p-4 text-left text-blueGray-700 pl-8">
                  방을 찾을 수 없습니다.
                </th>
              </tr>
            )}
          </tbody>
        </table>
      </div>
    </div>
  );
}

export default RoomList;
