import React from "react";
import LoginImg from "./SignupImage";
import SignupForm from "./SignuupForm";
import { Link } from "react-router-dom";
import BgImg from "../../img/LoginImage.png";
function SignupContent() {
  return (
    <React.Fragment>
      <div className="relative">
        <div
          className="bg-cover bg-no-repeat bg-center relative "
          style={{
            backgroundImage: `url(${BgImg})`, // 이미지 경로를 직접 사용
          }}
        >
          <div className="p-10">
          <h1 className="flex justify-start text-white drop-shadow-[0_1.2px_1.2px_rgba(0,0,0,0.8)] text-lg">
                  <Link to={"../Login"}>◀ Back To Login Page</Link>
                </h1>
            <div className="bg-gray-100 flex justify-center items-center h-screen overflow-hidden rounded-2xl">
              {/* Left: Image */}
              <div className="w-1/2 h-screen hidden lg:block end-50">
                <LoginImg />
              </div>
              {/* Right: Login Form */}
              <div className="lg:p-36 md:p-52 sm:20 p-8 w-full lg:w-1/2">
                <h1 className="font-semibold flex justify-start text-4xl mt-20 r-screen">
                  Sign Up
                </h1>
                <SignupForm />
              </div>
            </div>
          </div>
        </div>
      </div>
    </React.Fragment>
  );
}

export default SignupContent;
