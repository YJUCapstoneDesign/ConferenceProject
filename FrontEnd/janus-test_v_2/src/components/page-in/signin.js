import '../css/signin.css';
import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
// import axios from 'axios';  
import api from './api';

const Signin = () => {
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [error, setError] = useState("");
    const navigate = useNavigate(); 

    const login = async (event) => {
        event.preventDefault();
        try {
            const response = await api.post("http://localhost:8080/api/login", {
                email,
                password,
            });
            // const { accessToken, refreshToken } = response.data;
            const accessToken = response.headers['authorization'];
            const refreshToken = response.headers['authorization-refresh']
            localStorage.setItem("accessToken", JSON.stringify(accessToken));
            localStorage.setItem("refreshToken", JSON.stringify(refreshToken));
            navigate("/");
        } catch (err) {
            setError("로그인 실패: " + (err.response?.data?.message || "알 수 없는 오류"));
        }
    };

    return (
        <div className='Sign-in'>
            <div className="py-32 animated-background h-screen bg-gradient-to-r from-blue-500 via-blue-500 to-indigo-500">
                <div className="flex bg-white rounded-lg shadow-lg overflow-hidden mx-auto max-w-sm lg:max-w-4xl mb-5">
                    <div className="hidden lg:block lg:w-1/2 bg-cover" style={{backgroundImage: "url('https://source.unsplash.com/random/?conference')"}}></div>
                    <div className="w-full p-8 lg:w-1/2">
                        <a href="/"><p className='text-xs font-mono text-decoration-line: underline mb-5 inline-block'> Back to Website</p></a>
                        <h2 className="text-2xl font-bold text-gray-700 font-mono text-left">Welcome!</h2>
                        {error && <div className="text-red-500 text-sm mt-2">{error}</div>}
                        <div className="mt-4">
                            <label className="block text-gray-700 text-sm font-bold font-mono mb-2 ">Email</label>
                            <input className="bg-custom-flesh text-gray-700 focus:outline-none focus:shadow-outline border border-custom-flesh rounded-3xl py-2 px-4 block w-full appearance-none text-base" type="email" placeholder='Please enter your e-mail' value={email} onChange={(e) => setEmail(e.target.value)} />
                        </div>
                        <div className="mt-4">
                            <div className="flex justify-between">
                                <label className="block text-gray-700 text-sm  font-mono font-bold mb-2">Password</label>
                            </div>
                            <input className="bg-custom-flesh text-gray-700 focus:outline-none focus:shadow-outline border border-custom-flesh rounded-3xl py-2 px-4 block w-full appearance-none text-base" type="password" placeholder='Please enter your password' value={password} onChange={(e) => setPassword(e.target.value)} />
                            <a href="/pass" className="text-xs text-gray-500 inline-block">Forget Password?</a>
                            <a href="/signup"><p className='text-xs font-mono text-decoration-line: underline inline-block ml-56'>Sign-up</p></a>
                        </div>
                        <div className="mt-8">
                            <button className="bg-gray-950 text-white font-bold py-2 px-4 w-full rounded-3xl hover:bg-gray-700 text-base" onClick={login}>Login</button>
                        </div>
                        <div className="mt-4 flex items-center justify-between">
                            <span className="border-b w-1/5 lg:w-1/4"></span>
                            <a href="/" className="text-xs text-center text-gray-500 uppercase">or login with email</a>
                            <span className="border-b w-1/5 lg:w-1/4"></span>
                        </div>
                        <a href="/" className="flex items-center justify-center mt-4 text-white rounded-3xl shadow-md hover:bg-gray-100">
                            <div className="px-4 py-2">
                                <svg className="h-6 w-6" viewBox="0 0 40 40">
                                    <path d="M36.3425 16.7358H35V16.6667H20V23.3333H29.4192C28.045 27.2142 24.3525 30 20 30C14.4775 30 10 25.5225 10 20C10 14.4775 14.4775 9.99999 20 9.99999C22.5492 9.99999 24.8683 10.9617 26.6342 12.5325L31.3483 7.81833C28.3717 5.04416 24.39 3.33333 20 3.33333C10.7958 3.33333 3.33335 10.7958 3.33335 20C3.33335 29.2042 10.7958 36.6667 20 36.6667C29.2042 36.6667 36.6667 29.2042 36.6667 20C36.6667 18.8825 36.5517 17.7917 36.3425 16.7358Z" fill="#FFC107"/>
                                    <path d="M5.25497 12.2425L10.7308 16.2583C12.2125 12.59 15.8008 9.99999 20 9.99999C22.5491 9.99999 24.8683 10.9617 26.6341 12.5325L31.3483 7.81833C28.3716 5.04416 24.39 3.33333 20 3.33333C13.5983 3.33333 8.04663 6.94749 5.25497 12.2425Z" fill="#FF3D00"/>
                                    <path d="M20 36.6667C24.305 36.6667 28.2167 35.0192 31.1742 32.34L26.0159 27.975C24.3425 29.2425 22.2625 30 20 30C15.665 30 11.9842 27.2359 10.5975 23.3784L5.16254 27.5659C7.92087 32.9634 13.5225 36.6667 20 36.6667Z" fill="#4CAF50"/>
                                    <path d="M36.3425 16.7358H35V16.6667H20V23.3333H29.4192C28.7592 25.1975 27.56 26.805 26.0133 27.9758C26.0142 27.975 26.015 27.975 26.0158 27.9742L31.1742 32.3392C30.8092 32.6708 36.6667 28.3333 36.6667 20C36.6667 18.8825 36.5517 17.7917 36.3425 16.7358Z" fill="#1976D2"/>
                                </svg>
                            </div>
                            <p className="px-4 py-2 w-5/6 text-center text-gray-600 font-bold text-base">Sign in with Google</p>
                        </a>
                        <a href="/" className="flex items-center justify-center mt-4 text-white rounded-3xl shadow-md hover:bg-gray-100">
                            <div className="px-4 py-2">
                                <svg width="20" height="20" viewBox="0 0 20 20" fill="none" xmlns="http://www.w3.org/2000/svg">
                                    <g clip_path="url(#clip0_403_243)">
                                    <path d="M18 20H2C0.9 20 0 19.1 0 18V2C0 0.9 0.9 0 2 0H18C19.1 0 20 0.9 20 2V18C20 19.1 19.1 20 18 20Z" fill="#03C75A"/>
                                    <path d="M11.35 10.25L8.50002 6.19995H6.15002V13.8H8.65002V9.74995L11.5 13.8H13.85V6.19995H11.35V10.25Z" fill="white"/>
                                    </g>
                                    <defs>
                                    <clipPath id="clip0_403_243">
                                    <rect width="20" height="20" fill="white" rx="10" ry="10"/>
                                    </clipPath>
                                    </defs>
                                </svg>
                            </div>
                            <p className="px-4 py-2 w-5/6 text-center text-gray-600 font-bold text-base">Sign in with Naver</p>
                        </a>
                        <a href="/" className="flex items-center justify-center mt-4 text-white rounded-3xl shadow-md hover:bg-gray-100">
                            <div className="px-4 py-2">
                                <svg width="20" height="20" viewBox="0 0 20 20" fill="none" xmlns="http://www.w3.org/2000/svg">
                                    <circle cx="10" cy="10" r="10" fill="#FEE500"/>
                                    <path d="M10.0289 4.82629C6.91773 4.82629 4.40869 6.8335 4.40869 9.27028C4.40869 10.852 5.45245 12.237 7.01809 13.0278L6.48818 15.007C6.47819 15.0366 6.47666 15.0685 6.48375 15.0989C6.49084 15.1294 6.50627 15.1574 6.52833 15.1796C6.56049 15.2079 6.60187 15.2236 6.64475 15.2237C6.6803 15.2209 6.71403 15.2068 6.74109 15.1836L9.02131 13.6461C9.3579 13.6925 9.69719 13.7166 10.037 13.7183C13.1442 13.7183 15.6572 11.7111 15.6572 9.27028C15.6572 6.82949 13.1361 4.82629 10.0289 4.82629Z" fill="#392020"/>
                                    <path d="M6.37559 8.56774H5.76137C5.68525 8.56995 5.61006 8.55045 5.54459 8.51153C5.51553 8.49332 5.49054 8.46929 5.4712 8.44096C5.45185 8.41264 5.43857 8.38062 5.43219 8.34692C5.43024 8.32556 5.43024 8.30409 5.43219 8.28273C5.43028 8.24275 5.43804 8.20288 5.45481 8.16654C5.47159 8.1302 5.49687 8.09845 5.52853 8.07397C5.59852 8.02328 5.683 7.9965 5.7694 7.99767H7.62007C7.6968 7.99497 7.7725 8.01601 7.83685 8.05789C7.8666 8.0753 7.89212 8.09911 7.91156 8.12758C7.93099 8.15604 7.94387 8.18846 7.94926 8.2225C7.95122 8.24252 7.95122 8.2627 7.94926 8.28273C7.95115 8.32328 7.94341 8.3637 7.92666 8.40068C7.90991 8.43767 7.88464 8.47013 7.85291 8.49546C7.78463 8.54608 7.70097 8.57163 7.61605 8.56774H7.02192V10.6794C7.02433 10.7243 7.01737 10.7693 7.00147 10.8115C6.98558 10.8536 6.9611 10.892 6.92958 10.9242C6.89936 10.955 6.86304 10.9791 6.82293 10.995C6.78283 11.0109 6.73985 11.0183 6.69674 11.0166C6.62135 11.0187 6.54755 10.9946 6.48799 10.9483C6.43128 10.9031 6.3928 10.839 6.3796 10.7677C6.37563 10.7384 6.37563 10.7087 6.3796 10.6794L6.37559 8.56774Z" fill="#FEE500"/>
                                    <path d="M8.12647 8.1542C8.14413 8.08814 8.18559 8.03093 8.24289 7.99361C8.30394 7.95901 8.37351 7.94231 8.44361 7.94544H8.59616C8.67055 7.94277 8.74422 7.96084 8.80893 7.99763C8.8775 8.04374 8.92623 8.11396 8.94542 8.19433L9.74831 10.4625C9.76913 10.5217 9.78655 10.582 9.8005 10.6432C9.80187 10.6686 9.80187 10.694 9.8005 10.7194C9.80186 10.7586 9.79466 10.7975 9.7794 10.8336C9.76415 10.8696 9.74121 10.9019 9.71218 10.9282C9.68406 10.957 9.65029 10.9798 9.61299 10.995C9.5757 11.0101 9.53566 11.0175 9.4954 11.0165C9.43348 11.0227 9.3713 11.0078 9.31886 10.9743C9.26643 10.9408 9.2268 10.8906 9.20636 10.8319L9.03775 10.3381H7.98195L7.81334 10.8319C7.7934 10.8918 7.75329 10.943 7.69985 10.9766C7.64642 11.0103 7.58296 11.0244 7.52029 11.0165C7.4524 11.0183 7.38591 10.9971 7.33161 10.9563C7.2787 10.9142 7.243 10.8543 7.23124 10.7877C7.22911 10.765 7.22911 10.7421 7.23124 10.7194C7.22435 10.6796 7.22435 10.6389 7.23124 10.599C7.23124 10.5549 7.25935 10.5067 7.2754 10.4625L8.12647 8.1542ZM8.52792 8.68012L8.14654 9.88446H8.90527L8.52792 8.68012Z" fill="#FEE500"/>
                                    <path d="M9.89244 8.28278C9.88836 8.19268 9.92011 8.10465 9.98075 8.0379C10.0116 8.0068 10.0486 7.9825 10.0894 7.9666C10.1302 7.95069 10.1739 7.9435 10.2176 7.94552C10.2928 7.94465 10.3662 7.96866 10.4264 8.01379C10.4821 8.06088 10.5192 8.12638 10.5307 8.19844C10.5347 8.2264 10.5347 8.25481 10.5307 8.28278V10.3944H11.6347C11.7112 10.3934 11.7865 10.4142 11.8515 10.4546C11.8807 10.4726 11.9058 10.4966 11.9252 10.5249C11.9446 10.5533 11.9578 10.5854 11.9639 10.6192C11.9639 10.6192 11.9639 10.6593 11.9639 10.6794C11.9658 10.7194 11.958 10.7592 11.9413 10.7955C11.9245 10.8319 11.8992 10.8637 11.8676 10.8882C11.7976 10.9388 11.7131 10.9656 11.6267 10.9644H10.2698C10.1834 10.97 10.0975 10.9475 10.0249 10.9002C9.96001 10.8517 9.91676 10.7796 9.90448 10.6995C9.90154 10.6554 9.90154 10.6111 9.90448 10.567L9.89244 8.28278Z" fill="#FEE500"/>
                                    <path d="M12.0362 8.28286C12.0338 8.19303 12.0653 8.1056 12.1245 8.03798C12.1847 7.98331 12.2619 7.95102 12.3431 7.94663C12.4242 7.94225 12.5044 7.966 12.5701 8.01387C12.6271 8.06055 12.6656 8.12602 12.6785 8.19852C12.6805 8.22659 12.6805 8.25479 12.6785 8.28286V9.26638L13.6942 8.09414C13.7333 8.05415 13.7749 8.01665 13.8186 7.98177C13.8599 7.95722 13.9072 7.94467 13.9551 7.94561C14.0253 7.94522 14.0939 7.96624 14.1518 8.00583C14.1791 8.02421 14.2024 8.04793 14.2204 8.07552C14.2383 8.10311 14.2505 8.13402 14.2562 8.16642C14.2578 8.17437 14.2578 8.18258 14.2562 8.19053C14.2543 8.1998 14.2543 8.20935 14.2562 8.21861C14.2561 8.26085 14.245 8.30236 14.2241 8.33906C14.2028 8.37793 14.1772 8.41424 14.1478 8.44741L13.4814 9.17807L14.2843 10.4225V10.4627C14.326 10.5202 14.3585 10.5839 14.3807 10.6514V10.6714C14.3853 10.718 14.3786 10.7649 14.3611 10.8083C14.3436 10.8516 14.3159 10.8901 14.2803 10.9203C14.2133 10.971 14.1314 10.9978 14.0475 10.9966C13.9917 10.9989 13.9363 10.9864 13.8869 10.9605C13.8334 10.9246 13.7894 10.8764 13.7584 10.82L13.0117 9.61564L12.6544 9.989V10.6594C12.6568 10.7492 12.6253 10.8366 12.5661 10.9043C12.5344 10.9353 12.4967 10.9595 12.4552 10.9754C12.4138 10.9912 12.3696 10.9985 12.3253 10.9966C12.2512 10.9982 12.1789 10.9741 12.1205 10.9284C12.0638 10.8832 12.0253 10.819 12.0121 10.7477C12.0082 10.7184 12.0082 10.6887 12.0121 10.6594L12.0362 8.28286Z" fill="#FEE500"/>
                                </svg>
                            </div>
                            <p className="px-4 py-2 w-5/6 text-center text-gray-600 font-bold text-base">Sign in with Kakao</p>
                        </a>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default Signin;
