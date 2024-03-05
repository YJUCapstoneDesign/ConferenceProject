import React, { useEffect } from 'react';
import axios from 'axios';
import SignupButton from './SignupButton';

function SignupForm() {
    useEffect(() => {
        // 회원가입 데이터를 서버에 보내는 함수
        const sendDataToServer = async () => {
            try {
                const name = document.getElementById('input-0').value;
                const email = document.getElementById('input-1').value;
                const password = document.getElementById('input-2').value;
                const nickname = document.getElementById('input-3').value;
                const address = document.getElementById('input-4').value;
                const phone = document.getElementById('input-5').value;

                const response = await axios.post('/api/signup', {
                    name,
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

        document.getElementById('signup-button').addEventListener('click', sendDataToServer);

        return () => {
            document.getElementById('signup-button').removeEventListener('click', sendDataToServer);
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
