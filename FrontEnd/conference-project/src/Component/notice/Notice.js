/* eslint-disable */
import React, { useState, useEffect } from 'react';
import NoticeTextContent from './NoticeText';
import textData from './NoticeData';
import imgData from './NoticeImg';
import { MdOutlineKeyboardArrowLeft } from 'react-icons/md';
import { MdOutlineKeyboardArrowRight } from 'react-icons/md';

function NoticeContent() {
    const [currentIndex, setCurrentIndex] = useState(0);
    const [imageError, setImageError] = useState(false);

    const handleNextImage = () => {
        const newIndex = (currentIndex + 1) % imgData.length;
        setCurrentIndex(newIndex);
    };

    const handlePrevImage = () => {
        const newIndex = (currentIndex - 1 + imgData.length) % imgData.length;
        setCurrentIndex(newIndex);
    };

    const handleImageError = () => {
        setImageError(true);
    };

    useEffect(() => {
        const interval = setInterval(handleNextImage, 5000);
        return () => clearInterval(interval);
    }, [currentIndex]);

    return (
        <div className="bg-gray-800 dark:bg-gray-900 mt-32">
            <nav className="bg-gray-800 dark:bg-gray-900">
                <div className="container p-6 mx-auto">
                    <a
                        className="block text-2xl font-bold text-center text-white lg:text-3xl dark:text-white dark:hover:text-gray-300"
                        href="/"
                    >
                        Unmute의 새 소식
                    </a>

                    <div className="flex items-center justify-center mt-6 capitalize text-white dark:text-gray-300">
                        최신 소식을 받아 보고, 모범 사례를 배우고, 더 많은 정보를 얻으세요.
                    </div>
                </div>
            </nav>
            <div className="container flex flex-col px-6 py-4 mx-auto space-y-6 md:h-128 md:py-8 md:flex-row md:items-center md:space-x-6">
                <div className="flex flex-col items-center w-full md:flex-row md:w-1/2">
                    <button onClick={handlePrevImage} className="mr-2 text-white">
                        <MdOutlineKeyboardArrowLeft style={{ fontSize: '76px' }} />
                    </button>
                    <NoticeTextContent title={textData[currentIndex].title} content={textData[currentIndex].content} />
                </div>
                <div className="flex items-center justify-center w-full h-96 md:w-1/2">
                    {imageError ? (
                        <div>Error loading image</div>
                    ) : (
                        <img
                            src={imgData[currentIndex].src}
                            alt={imgData[currentIndex].alt}
                            onError={handleImageError}
                            className="transition-opacity duration-500 ease-in-out opacity-100 hover:opacity-50"
                            style={{
                                width: `${imgData[currentIndex].width}px`,
                                height: `${imgData[currentIndex].height}px`,
                            }}
                        />
                    )}
                    <button onClick={handleNextImage} className="ml-2 text-white">
                        <MdOutlineKeyboardArrowRight style={{ fontSize: '76px' }} />
                    </button>
                </div>
            </div>
        </div>
    );
}

export default NoticeContent;
