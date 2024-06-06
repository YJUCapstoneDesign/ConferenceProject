import React from "react";

function Mypage() {
  return (
    <div class="h-full bg-gray-200 p-8">
          <div class="bg-white rounded-lg shadow-xl pb-8">
              <div class="w-full h-[250px]">
                  <img src="https://vojislavd.com/ta-template-demo/assets/img/profile-background.jpg" class="w-full h-full rounded-tl-lg rounded-tr-lg" />
              </div>
              <div class="flex flex-col items-center -mt-20">
                  <img src="https://source.unsplash.com/random/?face" class="rounded-full w-40 h-40 object-cover" />
                  <div class="flex items-center space-x-2 mt-2">
                      <p class="text-2xl">알렉스 킴</p>
                      <span class="bg-blue-500 rounded-full p-1" title="Verified">
                          <svg xmlns="http://www.w3.org/2000/svg" class="text-gray-100 h-2.5 w-2.5" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="4" d="M5 13l4 4L19 7"></path>
                          </svg>
                      </span>
                  </div>
                  <p class="text-gray-700">교수</p>
                  <p class="text-sm text-gray-500">복현동</p>
              </div>
              <div class="flex-1 flex flex-col items-center lg:items-end justify-end px-8 mt-2">
                <div class="flex items-center space-x-4 mt-2">
                    <button class="flex items-center bg-blue-600 hover:bg-blue-700 text-gray-100 px-4 py-2 rounded text-sm space-x-2 transition duration-100">
                        <span>Edit Profile</span>
                    </button>
                </div>
            </div>
          </div>

          <div class="my-4 flex flex-col 2xl:flex-row space-y-4 2xl:space-y-0 2xl:space-x-4">
              <div class="w-full flex flex-col 2xl:w-full">
                  <div class="flex-1 h-80 bg-white rounded-lg shadow-xl p-8">
                      <h4 class="text-xl text-gray-900 font-bold">Personal Info</h4>
                      <ul class="mt-2 text-gray-700">
                          <li class="flex border-y py-2">
                              <span class="font-bold w-24">name:</span>
                              <span class="text-gray-700">알렉스 킴</span>
                          </li>
                          <li class="flex border-b py-2">
                              <span class="font-bold w-24">Phone:</span>
                              <span class="text-gray-700">010-1234-1234</span>
                          </li>
                          <li class="flex border-b py-2">
                              <span class="font-bold w-24">Email:</span>
                              <span class="text-gray-700">alexkim123@g.yju.ac.kr</span>
                          </li>
                      </ul>
                  </div>
              </div>
          </div>
      </div>
  )
}

export default Mypage;

