import React from 'react';
import LoginImg from './SignupImage';
import SignupForm from './SignuupForm';
import { Link } from 'react-router-dom';

function SignupContent() {
    

    return (
        <React.Fragment>
            <div className="bg-gray-100 flex justify-center items-center h-screen overflow-hidden">
                {/* Left: Image */}
                <div className="w-1/2 h-screen hidden lg:block">
                    <LoginImg />
                </div>
                {/* Right: Login Form */}
                <div className="lg:p-36 md:p-52 sm:20 p-8 w-full lg:w-1/2">
                    <h1 className="flex justify-start ">
                        <Link to={'../Login'}>◀ Back To Login Page</Link>
                    </h1>
                    <h1 className="text-2xl font-semibold mb-4 font-black flex justify-start text-5xl mt-12 mb-14">
                        Sign Up
                    </h1>
                    <SignupForm />
                </div>
            </div>
        </React.Fragment>
    );
}

export default SignupContent;
