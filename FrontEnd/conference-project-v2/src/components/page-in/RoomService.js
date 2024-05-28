import React, { useState } from 'react';
import DatePicker from 'react-datepicker';
import 'react-datepicker/dist/react-datepicker.css';
import api from './api';

function RoomService() {
  const [startDate, setStartDate] = useState(null);
  const [endDate, setEndDate] = useState(null);
  const [MeetingName, setMeetingName] = useState(null);
  
  const CreateRoom = () => {
    const data = {
      startDate: startDate,
      endDate: endDate,
      meetingName: MeetingName
    };
    api.post('', data)
      .then(response => {
        console.log(response.data);
        alert("회의 생성 성공");
      })
      .catch(error => {
        console.error('Error creating room:', error);
        alert("회의 생성 실패")
      });
  };

  return (
    <div className="App animated-background h-screen bg-gradient-to-r from-blue-500 via-blue-500 to-indigo-500">
      <div className="min-h-screen flex items-center justify-center">
        <div className="max-w-md w-full p-6 bg-white rounded-lg shadow-lg">
          <h1 className="text-2xl font-semibold text-center text-gray-500 mt-8 mb-6">회의를 시작해보세요!</h1> 
          <p className="text-sm text-gray-600 text-center mt-8 mb-6">간단한 요청사항을 실행한 후 회의를 시작해보세요.</p> 
          <form>
            <div className="mb-6">
              <label htmlFor="name" className="block mb-2 text-sm text-gray-600"></label>
              <input type="text" id="text" name="text" className="w-full px-4 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-indigo-700"onChange={(e) => setMeetingName(e.target.value)} required placeholder="Meeting name" />
            </div>
            <div className="flex items-center mb-6">
              <DatePicker
                selected={startDate}
                onChange={(date) => setStartDate(date)}
                selectsStart
                startDate={startDate}
                endDate={endDate}
                className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full px-4 py-2.5 focus:outline-none focus:ring-2 focus:ring-offset-2"
                placeholderText="Select date start"
              />
              <span className="mx-4 text-gray-500">to</span>
              <DatePicker
                selected={endDate}
                onChange={(date) => setEndDate(date)}
                selectsEnd
                startDate={startDate}
                endDate={endDate}
                minDate={startDate}
                className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full px-4 py-2.5 focus:outline-none focus:ring-2 focus:ring-offset-2"
                placeholderText="Select date end"
              />
            </div>
            <button type="submit" className="w-32 bg-gradient-to-r from-indigo-700 to-cyan-600 text-white py-2 rounded-lg mx-auto block focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-cyan-500 mt-4 mb-4" onClick={CreateRoom}>생 성</button>
          </form>
          <p className="text-xs text-gray-600 text-center mt-8">&copy; 2024 UNMUTE</p>
        </div>
      </div>
    </div>
  );
}

export default RoomService;
