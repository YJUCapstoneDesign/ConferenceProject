import React, { useState } from 'react';
import api from './api';
import NavIn from "./InNavbar";
import { useNavigate } from 'react-router-dom';

function ProfileEdit() {
  const navigate = useNavigate(); 
  const [formData, setFormData] = useState({
    username: '',
    phone: ''
  });

  const handleChange = (e) => {
    const { name, value } = e.target; 
    setFormData({
      ...formData,
      [name]: value
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const response = await api.patch('/api/update/info', formData, {
        headers: {  
          'Content-Type': 'application/json'
        }
      });
        alert('프로필 수정 완료');
        navigate('/Mypage');
    } catch (error) {
      console.error('프로필 수정 실패!', error);
    }
  };

  return (
    <div>
      {/* Below is Profile Edit */}
      <div className="max-w-lg mx-auto mt-48 bg-white dark:bg-gray-800 rounded-lg shadow-md px-8 py-10 flex flex-col items-center">
        <div className="w-full flex flex-col gap-4">
          <NavIn />
          <form onSubmit={handleSubmit} className="flex flex-col gap-4">
            <div className="flex items-start flex-col justify-start">
            <h2 className="text-2xl font-semibold text-black mb-10">Edit Profile!</h2>
              <label htmlFor="username" className="text-sm text-gray-700 dark:text-gray-200 mr-2">Username:</label>
              <input
                type="text"
                id="username"
                name="username"
                value={formData.username}
                onChange={handleChange}
                className="w-full px-3 dark:text-gray-200 dark:bg-gray-900 py-2 rounded-md border border-gray-300 dark:border-gray-700 focus:outline-none focus:ring-1 focus:ring-blue-500"
              />
            </div>

            <div className="flex items-start flex-col justify-start">
              <label htmlFor="phone" className="text-sm text-gray-700 dark:text-gray-200 mr-2">Phone:</label>
              <input
                type="text"
                id="phone"
                name="phone"
                value={formData.phone}
                onChange={handleChange}
                className="w-full px-3 dark:text-gray-200 dark:bg-gray-900 py-2 rounded-md border border-gray-300 dark:border-gray-700 focus:outline-none focus:ring-1 focus:ring-blue-500"
              />
            </div>

            <button type="submit" className="bg-indigo-600 hover:bg-indigo-700 text-white mt-4 font-medium py-2 px-4 rounded-md shadow-sm">Edit</button>
          </form>
        </div>
      </div>
    </div>
  );
}

export default ProfileEdit;
