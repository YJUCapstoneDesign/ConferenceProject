import React,{useState} from 'react';
import {useNavigate} from 'react-router-dom';


function Signup() {
  // unmute 기준 각 요소의 상태관리 
  const [name, setName] = useState("")
  const [email, setEmail] = useState("")
  const [password, setPassword] = useState("")
  const [phone, setPhone] = useState("")
  const [Address, setAddress] = useState("")
  const navigate = useNavigate()

  const signup = () => {
    fetch('http://localhost:4000/api/test', { 
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({
        name: name,
        email: email,
        password: password,
        phone: phone,
        Address: Address,
      }),
    })
      .then((response) => response.json())
      .then(response => {
        if (response.message === 'success') {
        alert("성공");
        navigate("/signin")
        } else {
          alert('양식을 다시 한번 확인해주세요');
        }
      });
  };


  return (  
    <div className='Signup'>
            <div className="py-24 animated-background h-screen bg-gradient-to-r from-blue-500 via-blue-500 to-indigo-500">
              <div className="flex bg-white rounded-lg shadow-lg overflow-hidden mx-auto max-w-sm lg:max-w-4xl mb-5">
                    <div className="hidden lg:block lg:w-1/2 bg-cover" style={{backgroundImage: "url('https://source.unsplash.com/random/?conference')"}}></div>
                    <div className="w-full p-8 lg:w-1/2">
                        <a href="/"><p className='text-xs font-mono text-decoration-line: underline mb-5 inline-block'> Back to Website</p></a>
                        <h2 className="text-2xl font-bold text-gray-700 font-mono text-left">Signup!</h2>
                        {/* <p className="text-xl text-gray-600 text-center">Welcome back!</p> */}
                        <div className="mt-4">
                            <label className="block text-gray-700 text-sm font-bold font-mono mb-2  ">Name</label>
                            <input className="bg-custom-flesh text-gray-700 focus:outline-none focus:shadow-outline border border-custom-flesh rounded-3xl py-2 px-4 block w-full appearance-none" type="text" onChange={(e) => setName(e.target.value)}/>
                        </div>
                        <div className="mt-4">
                            <label className="block text-gray-700 text-sm font-bold font-mono mb-2  ">Email</label>
                            <input className="bg-custom-flesh text-gray-700 focus:outline-none focus:shadow-outline border border-custom-flesh rounded-3xl py-2 px-4 block w-full appearance-none" type="email" onChange={(e) => setEmail(e.target.value)} />
                        </div>
                        <div className="mt-4">
                            <div className="flex justify-between">
                                <label className="block text-gray-700 text-sm  font-mono font-bold mb-2">Password</label>
                            </div>
                            <input className="bg-custom-flesh text-gray-700 focus:outline-none focus:shadow-outline border border-custom-flesh rounded-3xl py-2 px-4 block w-full appearance-none" type="password" onChange={(e) => setPassword(e.target.value)} />
                        </div>
                        <div className="mt-4">
                            <label className="block text-gray-700 text-sm font-bold font-mono mb-2  ">Address</label>
                            <input className="bg-custom-flesh text-gray-700 focus:outline-none focus:shadow-outline border border-custom-flesh rounded-3xl py-2 px-4 block w-full appearance-none" type="text" onChange={(e) => setAddress(e.target.value)}/>
                        </div>
                        <div className="mt-4">
                            <label className="block text-gray-700 text-sm font-bold font-mono mb-2  ">Phone</label>
                            <input className="bg-custom-flesh text-gray-700 focus:outline-none focus:shadow-outline border border-custom-flesh rounded-3xl py-2 px-4 block w-full appearance-none" type="text" onChange={(e) => setPhone(e.target.value)}/>
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
