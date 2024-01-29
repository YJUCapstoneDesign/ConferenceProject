import React from 'react';

function LoginMemory() {
    return (
        <div className="mb-4 flex items-center">
            <input type="checkbox" id="remember" name="remember" className="text-blue-500" />
            <label htmlFor="remember" className="text-gray-600 ml-2 ">
                Remember Me
            </label>
        </div>
    );
}
export default LoginMemory;
