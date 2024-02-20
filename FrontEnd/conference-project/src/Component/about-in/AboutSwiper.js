import React from 'react';
import BrainStromingImg from './AboutImage/BrainStorming.png';
import LiveMeetingImg from './AboutImage/LiveMeeting.png';
import ScreenSharing from './AboutImage/ScreenSharing.png';

function AboutSwiper() {
    return (
        <div className="mx-0 max-w-xl flex rounded-2xl bg-indigo-700">
            <div className="swiper-container flex-col flex self-center">
                <div className="swiper-wrapper">
                    <div className="swiper-slide">
                        {/* swiper-slide */}
                        <blockquote className="text-left">
                            <div className="relative">
                                <div className="relative">
                                    {/* 상단 swiper 이미지 */}
                                    <img
                                        src={LiveMeetingImg}
                                        alt="aji"
                                        className="object-cover w-full h-60 mx-auto rounded-t-2xl"
                                    />
                                    <div className="rounded-t-2xl absolute bg-gradient-to-t from-gray-800 opacity-75 inset-0 z-0"></div>
                                </div>
                            </div>
                            {/* 하단 swiper 텍스트 */}
                            <div className="relative m-5 p-5">
                                {/* svg태그는 쌍따옴표 이미지 */}
                                <svg
                                    className="absolute left-0 w-6 fill-indigo-500"
                                    viewBox="0 0 512 512"
                                    xmlns="http://www.w3.org/2000/svg"
                                >
                                    <path d="M464 256h-80v-64c0-35.3 28.7-64 64-64h8c13.3 0 24-10.7 24-24V56c0-13.3-10.7-24-24-24h-8c-88.4 0-160 71.6-160 160v240c0 26.5 21.5 48 48 48h128c26.5 0 48-21.5 48-48V304c0-26.5-21.5-48-48-48zm-288 0H96v-64c0-35.3 28.7-64 64-64h8c13.3 0 24-10.7 24-24V56c0-13.3-10.7-24-24-24h-8C71.6 32 0 103.6 0 192v240c0 26.5 21.5 48 48 48h128c26.5 0 48-21.5 48-48V304c0-26.5-21.5-48-48-48z" />
                                </svg>
                                <p className="text-gray-100 text-xl px-5">
                                    실시간 화상 미팅 서비스를 통해 팀원들과 소통하세요!
                                </p>
                                <svg
                                    className="absolute right-0 w-6 fill-indigo-500"
                                    viewBox="0 0 512 512"
                                    xmlns="http://www.w3.org/2000/svg"
                                >
                                    <path d="M464 32H336c-26.5 0-48 21.5-48 48v128c0 26.5 21.5 48 48 48h80v64c0 35.3-28.7 64-64 64h-8c-13.3 0-24 10.7-24 24v48c0 13.3 10.7 24 24 24h8c88.4 0 160-71.6 160-160V80c0-26.5-21.5-48-48-48zm-288 0H48C21.5 32 0 53.5 0 80v128c0 26.5 21.5 48 48 48h80v64c0 35.3-28.7 64-64 64h-8c-13.3 0-24 10.7-24 24v48c0 13.3 10.7 24 24 24h8c88.4 0 160-71.6 160-160V80c0-26.5-21.5-48-48-48z" />
                                </svg>
                                <div className="text-sm mt-5 mx-5">
                                    <p className="font-medium text-white">화상회의</p>
                                    <p className="mt-1 text-gray-300">자세히 알아보기 </p>
                                </div>
                            </div>
                        </blockquote>
                    </div>
                    {/* 두 번째 swiper-slide */}
                    <div className="swiper-slide">
                        <blockquote className="text-left">
                            <div className="">
                                <div className="relative">
                                    <img
                                        src={BrainStromingImg}
                                        alt="aji"
                                        className="object-cover w-full mx-auto rounded-t-2xl h-60"
                                    />
                                    <div className="rounded-t-2xl absolute bg-gradient-to-t from-gray-800 opacity-75 inset-0 z-0"></div>
                                </div>
                            </div>
                            <div className="relative m-5 p-5">
                                <svg
                                    className="absolute left-0 w-6 fill-indigo-500"
                                    viewBox="0 0 512 512"
                                    xmlns="http://www.w3.org/2000/svg"
                                >
                                    <path d="M464 256h-80v-64c0-35.3 28.7-64 64-64h8c13.3 0 24-10.7 24-24V56c0-13.3-10.7-24-24-24h-8c-88.4 0-160 71.6-160 160v240c0 26.5 21.5 48 48 48h128c26.5 0 48-21.5 48-48V304c0-26.5-21.5-48-48-48zm-288 0H96v-64c0-35.3 28.7-64 64-64h8c13.3 0 24-10.7 24-24V56c0-13.3-10.7-24-24-24h-8C71.6 32 0 103.6 0 192v240c0 26.5 21.5 48 48 48h128c26.5 0 48-21.5 48-48V304c0-26.5-21.5-48-48-48z" />
                                </svg>
                                <p className="text-gray-100 text-xl px-5">브레인스토밍을 통해 아이디어를 공유하세요!</p>
                                <svg
                                    className="absolute right-0 w-6 fill-indigo-500"
                                    viewBox="0 0 512 512"
                                    xmlns="http://www.w3.org/2000/svg"
                                >
                                    <path d="M464 32H336c-26.5 0-48 21.5-48 48v128c0 26.5 21.5 48 48 48h80v64c0 35.3-28.7 64-64 64h-8c-13.3 0-24 10.7-24 24v48c0 13.3 10.7 24 24 24h8c88.4 0 160-71.6 160-160V80c0-26.5-21.5-48-48-48zm-288 0H48C21.5 32 0 53.5 0 80v128c0 26.5 21.5 48 48 48h80v64c0 35.3-28.7 64-64 64h-8c-13.3 0-24 10.7-24 24v48c0 13.3 10.7 24 24 24h8c88.4 0 160-71.6 160-160V80c0-26.5-21.5-48-48-48z" />
                                </svg>
                                <div className="text-sm mt-5 mx-5">
                                    <p className="font-medium text-white">브레인스토밍</p>
                                    <p className="mt-1 text-gray-300">자세히 알아보기</p>
                                </div>
                            </div>
                        </blockquote>
                    </div>
                    {/* 세 번째 swiper-slide */}
                    <div className="swiper-slide">
                        <blockquote className="text-left">
                            <div className="">
                                <div className="relative">
                                    <img
                                        src={ScreenSharing}
                                        alt="aji"
                                        className="object-cover w-full mx-auto rounded-t-2xl h-60"
                                    />
                                    <div className="rounded-t-2xl absolute bg-gradient-to-t from-gray-800 opacity-75 inset-0 z-0"></div>
                                </div>
                            </div>
                            <div className="relative m-5 p-5">
                                <svg
                                    className="absolute left-0 w-6 fill-indigo-500"
                                    viewBox="0 0 512 512"
                                    xmlns="http://www.w3.org/2000/svg"
                                >
                                    <path d="M464 256h-80v-64c0-35.3 28.7-64 64-64h8c13.3 0 24-10.7 24-24V56c0-13.3-10.7-24-24-24h-8c-88.4 0-160 71.6-160 160v240c0 26.5 21.5 48 48 48h128c26.5 0 48-21.5 48-48V304c0-26.5-21.5-48-48-48zm-288 0H96v-64c0-35.3 28.7-64 64-64h8c13.3 0 24-10.7 24-24V56c0-13.3-10.7-24-24-24h-8C71.6 32 0 103.6 0 192v240c0 26.5 21.5 48 48 48h128c26.5 0 48-21.5 48-48V304c0-26.5-21.5-48-48-48z" />
                                </svg>
                                <p className="text-gray-100 text-xl px-5">화면 공유를 통해 프로젝트를 공유하세요!</p>
                                <svg
                                    className="absolute right-0 w-6 fill-indigo-500"
                                    viewBox="0 0 512 512"
                                    xmlns="http://www.w3.org/2000/svg"
                                >
                                    <path d="M464 32H336c-26.5 0-48 21.5-48 48v128c0 26.5 21.5 48 48 48h80v64c0 35.3-28.7 64-64 64h-8c-13.3 0-24 10.7-24 24v48c0 13.3 10.7 24 24 24h8c88.4 0 160-71.6 160-160V80c0-26.5-21.5-48-48-48zm-288 0H48C21.5 32 0 53.5 0 80v128c0 26.5 21.5 48 48 48h80v64c0 35.3-28.7 64-64 64h-8c-13.3 0-24 10.7-24 24v48c0 13.3 10.7 24 24 24h8c88.4 0 160-71.6 160-160V80c0-26.5-21.5-48-48-48z" />
                                </svg>
                                <div className="text-sm mt-5 mx-5">
                                    <p className="font-medium text-white">화면공유</p>
                                    <p className="mt-1 text-gray-300">자세히 알아보기</p>
                                </div>
                            </div>
                        </blockquote>
                    </div>
                </div>
                <div className="mt-12 swiper-pagination hidden"></div>
            </div>
        </div>
    );
}
export default AboutSwiper;
