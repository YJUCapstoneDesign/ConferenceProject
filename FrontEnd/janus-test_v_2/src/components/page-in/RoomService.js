import React, { useState,useEffect } from 'react';
import api from './api';
import { useNavigate } from 'react-router-dom';

function RoomService() {
  const [teamId, setTeamId] = useState(null);
  const [password, setPassword] = useState(null);
  const navigate = useNavigate();

  useEffect(() => {
    const accessToken = localStorage.getItem('accessToken');
    if (!accessToken) {
      alert('로그인이 필요합니다.');
      navigate('/signin'); 
      return; 
    }}, []); 

  const CreateRoom = async (event) => {
    event.preventDefault();

    console.log(event);
    try{
      const data = {
        teamId,
        password,
      };
      const response = await api.post('http://localhost:8080/api/team/create', data);
      console.log(response.data);
      const teamNumber = response.data;
      navigate(`/JanusTeam/${teamNumber}`);
      } catch (error) {
      console.error('Error creating room:', error);
      alert('회의 입장 실패');
    }
  };
  return (
    <div className="App animated-background h-screen bg-gradient-to-r from-blue-500 via-blue-500 to-indigo-500">
      <div className="min-h-screen flex items-center justify-center">
        <div className="max-w-md w-full p-6 bg-white rounded-lg shadow-lg">
          <h1 className="text-2xl font-semibold text-center text-gray-500 mt-8 mb-6">회의를 시작해보세요!</h1> 
          <p className="text-sm text-gray-600 text-center mt-8 mb-6">간단한 요청사항을 실행한 후 회의를 시작해보세요.</p> 
          <form onSubmit={(e) => CreateRoom(e)}>
            <div className="mb-6">
              <label htmlFor="name" className="block mb-2 text-sm text-gray-600"></label>
              <input type="password" id="text" name="text" className="w-full px-4 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-indigo-700"onChange={(e) => setTeamId(e.target.value)} required placeholder="Meeting id" />
            </div>
            <div className="mb-6">
              <label htmlFor="name" className="block mb-2 text-sm text-gray-600"></label>
              <input type="password" id="text" name="text" className="w-full px-4 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-indigo-700"onChange={(e) => setPassword(e.target.value)} required placeholder="Meeting password" />
            </div>
            <button type="submit" className="w-32 bg-gradient-to-r from-indigo-700 to-cyan-600 text-white py-2 rounded-lg mx-auto block focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-cyan-500 mt-4 mb-4">생 성</button>
          </form>
          <p className="text-xs text-gray-600 text-center mt-8">&copy; 2024 UNMUTE</p>
        </div>
      </div>
    </div>
  );
}

export default RoomService;
