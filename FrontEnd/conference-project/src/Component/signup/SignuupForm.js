import React, { useEffect } from 'react';
import axios from 'axios';
import SignupButton from './SignupButton';

function SignupForm() {
    useEffect(() => {
        const sendDataToServer = async () => {
            try {
                const username = document.getElementById('input-0').value;
                const email = document.getElementById('input-1').value;
                const password = document.getElementById('input-2').value;
                const nickname = document.getElementById('input-3').value;
                const address = document.getElementById('input-4').value;
                const phone = document.getElementById('input-5').value;
                
                // 최소 8자, 하나의 이상의 대소문자 및 하나의 숫자, 하나의 특수문자를 포함하는 비밀번호
                const passwordRegex = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{8,}$/;

                // 앞자리가 01이며 (0,1,6,7,8,9) 중간에 3~4자리, 세번째는 4자리인 전화번호
                const phoneRegex = /^01([0|1|6|7|8|9])-?([0-9]{3,4})-?([0-9]{4})$/;

                // @ 포함여부와 대문자,소문자를 구분하지않게 표현식끝에 i 사용
                const emailRegex = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;

                if (!passwordRegex.test(password)) {
                    alert('비밀번호 형식이 올바르지 않습니다.');
                    return;
                }

                if (!phoneRegex.test(phone)) {
                    alert('전화번호 형식이 올바르지 않습니다.');
                    return;
                }

                if (!emailRegex.test(email)) {
                    alert('이메일 형식이 올바르지 않습니다.');
                    return;
                }

                const response = await axios.post('/api/signup', {
                    username,
                    email,
                    password,
                    nickname,
                    address,
                    phone
                });
                console.log('회원가입 성공:', response.data);
            } catch (error) {
                console.error('회원가입 실패:', error);
            }
        };

        const signupButton = document.getElementById('signup-button');
        
        // 'signup-button' 요소에 click 이벤트 리스너를 등록
        if(signupButton) {
            signupButton.addEventListener('click', sendDataToServer);
        }
        
        // useEffect의 클린업 함수를 사용하여 컴포넌트가 언마운트될 때 이벤트 리스너를 제거
        return () => {
            if(signupButton) {
                document.removeEventListener('click', sendDataToServer);
            }
            
        };
    }, []);

    const inputLabels = ['Name', 'Email', 'Password', 'Nickname', 'Address', 'Phone'];

    return (
        <React.Fragment>
            <div className="my-14">
                {inputLabels.map((label, index) => (
                    <div className="mb-4" key={index}>
                        <label
                            htmlFor={`input-${index}`}
                            className="block text-gray-600 flex text-black font-bold"
                            style={{ marginBottom: '10px' }}
                        >
                            {label}
                        </label>
                        <input
                            type={label === 'Password' ? 'password' : 'text'}
                            id={`input-${index}`}
                            name={`input-${index}`}
                            className="w-full border border-gray-300 rounded-md py-2 px-3 focus:outline-none focus:border-blue-500 rounded-xl"
                            autoComplete="off"
                            style={{ backgroundColor: '#FFEBDF' }}
                        />
                    </div>
                ))}
                <SignupButton />
            </div>
        </React.Fragment>
    );
}

export default SignupForm;
