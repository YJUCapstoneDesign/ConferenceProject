import React, { useRef, useState, useEffect } from "react";
import api from './api';
import { Link } from 'react-router-dom';
import NavIn from "./InNavbar";

const baseURL = process.env.REACT_SPRING_SERVER

function Mypage() {
  const profileImageRef = useRef(null);
  const [userInfo, setUserInfo] = useState({
    name: "",
    phone: "",
    email: "",
    profileImageUrl: "", 
  });
  const [selectedImage, setSelectedImage] = useState(null); 

  useEffect(() => {
    const fetchUserInfo = async () => {
      try {
        const response = await api.get("/api/profile");
        
        let imageUrl = response.data.imageUrl;
        // "/로 시작하는 지 확인"
        if (response.data.imageUrl.startsWith("/") === true) {
            imageUrl = baseURL + response.data.imageUrl;
        }
        setUserInfo({
          name: response.data.username,
          phone: response.data.phone,
          email: response.data.email,
          profileImageUrl: imageUrl, 
        });
      } catch (error) {
        console.error("Error fetching user info:", error);
      }
    };

    fetchUserInfo();
  }, []);

  const handleProfileImageClick = () => {
    profileImageRef.current.click();
  };

  const handleProfileImageChange = async (event) => {
    const file = event.target.files[0];
    if (file) {
      setSelectedImage(URL.createObjectURL(file)); 
      const formData = new FormData();
      formData.append("profileImage", file);

      try {
        const response = await api.put("/api/update/profileImage", formData, {
          headers: {
            "Content-Type": "multipart/form-data",
          },
        });

        console.log("Profile image updated:", response.data);
        setUserInfo((prevState) => ({
          ...prevState,
          profileImageUrl: response.data.imageUrl,
        }));
      } catch (error) {
        console.error("Error updating profile image:", error);
      }
    }
  };

  return (
    <div className="h-full bg-white p-8">
      <NavIn/>
      <div className="mt-20 bg-white rounded-lg shadow-xl pb-8">
        <div className="w-full h-[250px]">
          <img
            src="https://images.unsplash.com/photo-1582192903020-8a5e59dcdcf2?q=80&w=3870&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"
            className="object-cover w-full h-full rounded-tl-lg rounded-tr-lg"
            alt="Background"
          />
        </div>
        <div className="flex flex-col items-center -mt-20" onClick={handleProfileImageClick}>
          <img
            src={selectedImage || userInfo.profileImageUrl || "https://source.unsplash.com/random/?face"}
            className="rounded-full w-40 h-40 object-cover"
            alt="Profile"
          />
          <input
            type="file"
            ref={profileImageRef}
            style={{ display: "none" }}
            onChange={handleProfileImageChange}
          />
          <div className="flex items-center space-x-2 mt-2">
            <p className="text-2xl">{userInfo.name}</p>
            <span className="bg-indigo-500 rounded-full p-1" title="Verified">
              <svg xmlns="http://www.w3.org/2000/svg" className="text-gray-100 h-2.5 w-2.5" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="4" d="M5 13l4 4L19 7"></path>
              </svg>
            </span>
          </div>
        </div>
        <div className="flex-1 flex flex-col items-center lg:items-end justify-end px-8 mt-2">
          <div className="flex items-center space-x-4 mt-2">
            <Link to="/ProfileEdit" className="flex items-center bg-indigo-600 hover:bg-indigo-700 text-gray-100 px-4 py-2 rounded text-sm space-x-2 transition duration-100">
              <span>Edit Profile</span>
            </Link>
          </div>
        </div>
      </div>

      <div className="my-4 flex flex-col 2xl:flex-row space-y-4 2xl:space-y-0 2xl:space-x-4">
        <div className="w-full flex flex-col 2xl:w-full">
          <div className="flex-1 h-80 bg-white rounded-lg shadow-xl p-8">
            <h4 className="text-xl text-gray-900 font-bold">Personal Info</h4>
            <ul className="mt-2 text-gray-700">
              <li className="flex border-y py-2">
                <span className="font-bold w-24">name:</span>
                <span className="text-gray-700">{userInfo.name}</span>
              </li>
              <li className="flex border-b py-2">
                <span className="font-bold w-24">Phone:</span>
                <span className="text-gray-700">{userInfo.phone}</span>
              </li>
              <li className="flex border-b py-2">
                <span className="font-bold w-24">Email:</span>
                <span className="text-gray-700">{userInfo.email}</span>
              </li>
            </ul>
          </div>
        </div>
      </div>
    </div>
  );
}

export default Mypage;
