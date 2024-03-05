import React from "react";
import LoginImg from "./LoginImage";
import { Link } from "react-router-dom";
import LoginForm from "./LoginForm";
import LoginMemory from "./LoginMemory";
import { Button } from "@material-tailwind/react";
import kakaoIcon from "../../img/kakao.png";
import naverLogo from "../../img/naverLogo.png";
import BgImg from "../../img/LoginImage.png";

function LoginContent() {
  return (
    <React.Fragment>
      <div className="relative">
        <div
          className="bg-cover bg-no-repeat bg-center relative "
          style={{
            backgroundImage: `url(${BgImg})`, // 이미지 경로를 직접 사용
          }}
        >
          <div className="p-20">
            <div className="bg-gray-100 flex justify-center items-center h-screen overflow-hidden rounded-2xl">
              {/* Left: Image */}
              <div className="w-1/2 h-screen hidden lg:block end-50">
                <LoginImg />
              </div>

              {/* Right: Login Form */}
              <div className="lg:p-36 md:p-52 sm:20 p-8 w-full lg:w-1/2">
                {/* Back to Website */}
                <div className="flex justify-start">
                  <p>
                    <button>
                      <Link to={"/"}>◀ Back to website</Link>
                    </button>
                  </p>
                </div>
                <h1 className="text-4xl font-semibold mb-4 flex justify-start pt-6 ...">
                  Login
                </h1>

                <form action="/" method="POST">
                  {/* Username Input */}
                  <LoginForm />
                  {/* Remember Me Checkbox */}
                  <LoginMemory />
                  {/* Forgot Password Link */}
                  <button
                    type="submit"
                    className="bg-black hover:bg-blue-600 text-white font-semibold rounded-md p-3 px-4 w-full"
                  >
                    Login
                  </button>
                  <div className="mb-6 text-[#787878]">
                    <a href="/" className="hover:underline font-semibold">
                      Forgot Password? /
                    </a>
                    <a
                      href="../Signup"
                      className="hover:underline font-semibold"
                    >
                      {" "}
                      Sign up Here
                    </a>
                  </div>
                  {/* Login Button */}
                </form>
                <div className="flex justify-center items-center gap-2 py-8 pl-3 flex-wrap">
                  <Button
                    color="blue-gray"
                    className="flex items-center gap-3 text-[#787878]"
                  >
                    <img
                      src="https://docs.material-tailwind.com/icons/google.svg"
                      alt="metamask"
                      className="h-7 w-7"
                    />
                    Continue With Google
                  </Button>
                  <Button
                    color="blue-gray"
                    className="flex items-center gap-2  text-[#787878]"
                  >
                    <img src={kakaoIcon} alt="metamask" className="h-7 w-7" />
                    Continue With kakao
                  </Button>
                  <Button
                    color="blue-gray"
                    className="flex justify-center items-center gap-2  text-[#787878]"
                  >
                    <img src={naverLogo} alt="metamask" className="h-7 w-7" />
                    Continue With Naver
                  </Button>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </React.Fragment>
  );
}

export default LoginContent;
