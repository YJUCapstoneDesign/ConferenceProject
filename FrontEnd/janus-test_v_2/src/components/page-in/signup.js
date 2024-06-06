import '../css/signin.css';
import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import api from './api';

function Signup() {
  // 초기 값 세팅
  const [username, setUserName] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [phone, setPhone] = useState("");
  const [error, setError] = useState("");
  const navigate = useNavigate();

  // 오류 메시지 상태 저장
  const [usernameMessage, setUsernameMessage] = useState("");
  const [emailMessage, setEmailMessage] = useState("");
  const [passwordMessage, setPasswordMessage] = useState("");
  const [phoneMessage, setPhoneMessage] = useState("");

  // 유효성 검사
  const [isUsername, setIsUsername] = useState(false);
  const [isEmail, setIsEmail] = useState(false);
  const [isPassword, setIsPassword] = useState(false);
  const [isPhone, setIsPhone] = useState(false);

  // 현재 포커스된 필드 상태
  const [focusedField, setFocusedField] = useState("");

  const onChangeUsername = (e) => {
    const currentUsername = e.target.value;
    setUserName(currentUsername);
    const UsernameRegExp = /^[가-힣a-zA-Z]+$/;

    if (!UsernameRegExp.test(currentUsername)) {
      setUsernameMessage("한글과 영어로만 이름을 지어주세요.");
      setIsUsername(false);
    } else {
      setUsernameMessage("사용 가능한 이름입니다:)");
      setIsUsername(true);
    }
  };

  const onChangeEmail = (e) => {
    const currentEmail = e.target.value;
    setEmail(currentEmail);
    const EmailRegExp =
      /^[A-Za-z0-9_]+[A-Za-z0-9]*[@]{1}[A-Za-z0-9]+[A-Za-z0-9]*[.]{1}[A-Za-z]{1,3}$/;

    if (!EmailRegExp.test(currentEmail)) {
      setEmailMessage("이메일의 형식이 올바르지 않습니다!");
      setIsEmail(false);
    } else {
      setEmailMessage("사용 가능한 이메일 입니다:)");
      setIsEmail(true);
    }
  };

  const onChangePassword = (e) => {
    const currentPassword = e.target.value;
    setPassword(currentPassword);
    const passwordRegExp =
      /^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,25}$/;
    if (!passwordRegExp.test(currentPassword)) {
      setPasswordMessage(
        "숫자+영문자+특수문자 조합으로 8자리 이상 입력해주세요!"
      );
      setIsPassword(false);
    } else {
      setPasswordMessage("안전한 비밀번호 입니다:)");
      setIsPassword(true);
    }
  };

  const onChangePhone = (e) => {
    const currentPhone = e.target.value;
    // 만약에 사용자가 -를 포함해서 번호를 칠 경우 공백으로 변경되게 일단 만들어놓음
    const sanitizedPhone = currentPhone.replace(/-/g, "");
    setPhone(sanitizedPhone);
    const phoneRegExp = /^01([0|1|6|7|8|9])([0-9]{3,4})([0-9]{4})$/;
  
    if (!phoneRegExp.test(sanitizedPhone)) {
      setPhoneMessage("올바른 형식이 아닙니다!");
      setIsPhone(false);
    } else {
      setPhoneMessage("사용 가능한 번호입니다:)");
      setIsPhone(true);
    }
  };
  

  const onFocusField = (field) => {
    setFocusedField(field);
  };

  const signup = async (event) => {
    event.preventDefault();
    try {
      const response = await api.post("http://localhost:8080/api/signup", {
        username,
        email,
        password,
        phone,
      });
      if (response.status === 200) {
        alert("성공");
        navigate("/signin");
      }
      // } else {
      //   setError('양식을 다시 한번 확인해주세요');
      // }
    } catch (err) {
      setError("회원가입 실패: " + (err.response?.data?.message || "알 수 없는 오류"));
    }
  };

  return (
    <div className='Signup'>
      <div className="py-24 animated-background h-screen bg-gradient-to-r from-blue-500 via-blue-500 to-indigo-500">
        <div className="flex bg-white rounded-lg shadow-lg overflow-hidden mx-auto max-w-sm lg:max-w-4xl mt-20">
          <div className="hidden lg:block lg:w-1/2 bg-cover" style={{ backgroundImage: "url('https://source.unsplash.com/random/?conference')" }}></div>
          <div className="w-full p-8 lg:w-1/2">
            <a href="/"><p className='text-xs font-mono text-decoration-line: underline mb-5 inline-block'> Back to Website</p></a>
            <h2 className="text-2xl font-bold text-gray-700 font-mono text-left">Signup!</h2>
            {error && <div className="text-red-500 text-sm mt-2">{error}</div>}
            {focusedField === "username" && usernameMessage && <div className="text-red-500 text-sm mt-2">{usernameMessage}</div>}
            {focusedField === "email" && emailMessage && <div className="text-red-500 text-sm mt-2">{emailMessage}</div>}
            {focusedField === "password" && passwordMessage && <div className="text-red-500 text-sm mt-2">{passwordMessage}</div>}
            {focusedField === "phone" && phoneMessage && <div className="text-red-500 text-sm mt-2">{phoneMessage}</div>}
            <div className="mt-4">
              <label className="block text-gray-700 text-sm font-bold font-mono mb-2">Name</label>
              <input className="bg-custom-flesh text-gray-700 focus:outline-none focus:shadow-outline border border-custom-flesh rounded-3xl py-2 px-4 block w-full appearance-none"
                     type="text"
                     onChange={onChangeUsername}
                     onFocus={() => onFocusField("username")}
              />
            </div>
            <div className="mt-4">
              <label className="block text-gray-700 text-sm font-bold font-mono mb-2">Email</label>
              <input className="bg-custom-flesh text-gray-700 focus:outline-none focus:shadow-outline border border-custom-flesh rounded-3xl py-2 px-4 block w-full appearance-none"
                     type="email"
                     onChange={onChangeEmail}
                     onFocus={() => onFocusField("email")}
              />
            </div>
            <div className="mt-4">
              <div className="flex justify-between">
                <label className="block text-gray-700 text-sm font-mono font-bold mb-2">Password</label>
              </div>
              <input className="bg-custom-flesh text-gray-700 focus:outline-none focus:shadow-outline border border-custom-flesh rounded-3xl py-2 px-4 block w-full appearance-none"
                     type="password"
                     onChange={onChangePassword}
                     onFocus={() => onFocusField("password")}
              />
            </div>
            <div className="mt-4">
              <label className="block text-gray-700 text-sm font-bold font-mono mb-2">Phone</label>
              <input className="bg-custom-flesh text-gray-700 focus:outline-none focus:shadow-outline border border-custom-flesh rounded-3xl py-2 px-4 block w-full appearance-none"
                     type="text"
                     onChange={onChangePhone}
                     onFocus={() => onFocusField("phone")}
              />
            </div>
            <div className="mt-10">
              <button className="bg-gray-950 text-white font-bold py-2 px-4 w-full rounded-3xl hover:bg-gray-700 text-base" onClick={signup}>Sign up</button>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}

export default Signup;
