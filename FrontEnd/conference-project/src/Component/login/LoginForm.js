import react from 'react';
import { useEffect, useState } from 'react';
import axios from 'axios';
// function App() {
//     const [hello, setHello] = useState('');

//     useEffect(() => {
//         axios.get('/api/test').then((res) => {
//             setHello(res.data);
//         }, []);
//     return (
//         <div className='App'>
//             <h1>백엔드 데이터 : {hello}</h1>
//         </div>
//     );
//     });
    
// }


function LoginForm() {
    return (
        <react.Fragment>
            {/* Username Input */}
            <div className="mb-4">
                <label htmlFor="username" className="block text-gray-600 pt-3 ... flex justify-start">
                    Username
                </label>
                <input
                    type="text"
                    id="username"
                    name="username"
                    className="w-full border border-gray-300 rounded-md py-2 px-3 focus:outline-none focus:border-blue-500"
                    autoComplete="off"
                    style={{ backgroundColor: '#FFEBDF' }}
                />
            </div>
            {/* Password Input */}
            <div className="mb-4  pb-3 ...">
                <label htmlFor="password" className="block text-gray-600 pt-3 ... flex justify-start ">
                    Password
                </label>
                <input
                    type="password"
                    id="password"
                    name="password"
                    className="w-full border border-gray-300 rounded-md py-2 px-3 focus:outline-none focus:border-blue-500"
                    autoComplete="off"
                    style={{ backgroundColor: '#FFEBDF' }}
                />
            </div>
        </react.Fragment>
    );
}
export default LoginForm;
