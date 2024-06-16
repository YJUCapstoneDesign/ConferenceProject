import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { Link } from 'react-router-dom';

function SelectRoom() {

  return (
    <div className="App animated-background h-screen bg-gradient-to-r from-blue-500 via-blue-500 to-indigo-500">
      <div className="min-h-screen flex items-center justify-center">
        <div className="max-w-md w-96 p-6 bg-white rounded-lg shadow-lg">
        <Link to={'/'}>
              <p className='text-xs font-semibold text-decoration-line: underline inline-block'> <svg             xmlns="http://www.w3.org/2000/svg" width="30" height="30" viewBox="0 0 24 24" style={{ fill: 'rgba(0, 0, 0, 1)', transform: '', msFilter: '' }}>  
              <path d="M12.707 17.293L8.414 13H18v-2H8.414l4.293-4.293-1.414-1.414L4.586 12l6.707 6.707z"></path>
            </svg></p></Link>
          <h1 className="text-2xl font-semibold text-center text-gray-500 mt-2 mb-2">회의를 시작해보세요!</h1> 
          <p className="text-sm text-gray-600 text-center mt-8 mb-6">시작할 준비가 되셨나요?<br/> 회의를 생성하거나 참여하여 바로 시작하세요!</p>
          <form className='flex flex-wrap'>
            <Link to='/Entrance' className='w-32 bg-gradient-to-r from-indigo-700 to-cyan-600 text-white py-2 rounded-lg mx-auto block focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-cyan-500 mt-4 mb-4'><p className="ml-10">입 장</p></Link>
            <Link to='/Room' className='w-32 bg-gradient-to-r from-indigo-700 to-cyan-600 text-white py-2 rounded-lg mx-auto block focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-cyan-500 mt-4 mb-4'><p className="ml-10">생 성</p></Link>
          </form>
          <p className="text-xs text-gray-600 text-center mt-8">&copy; 2024 UNMUTE</p>
        </div>
      </div>
    </div>
  );
}

export default SelectRoom;
