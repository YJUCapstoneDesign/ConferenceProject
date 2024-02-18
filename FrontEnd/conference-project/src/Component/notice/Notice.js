import React, { useState } from "react";
import NoticeTextContent from "./NoticeText";
import textData from "./NoticeData";
import imgData from "./NoticeImg";

function NoticeContent() {
  const [currentTitle, setCurrentTitle] = useState(textData[0].title);
  const [currentContent, setCurrentContent] = useState(textData[0].content);
  const [currentImg, setCurrentImg] = useState(imgData[0].url);
  const [imageError, setImageError] = useState(false);

  const handleButtonClick = (index) => {
    setCurrentTitle(textData[index].title);
    setCurrentContent(textData[index].content);
    setCurrentImg(imgData[index].url);
    setImageError(false);
  };

  const handleImageError = () => {
    setImageError(true);
  };

  return (
    <div className="bg-gray-800 dark:bg-gray-900">
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
          <div className="flex justify-center order-2 mt-6 md:mt-0 md:space-y-3 md:flex-col">
            {textData.map((item, index) => (
              <button
                key={index}
                className={`w-3 h-3 mx-2 bg-gray-300 rounded-full md:mx-0 focus:outline-none hover:bg-blue-500 ${
                  currentTitle === item.title ? "bg-blue-500" : ""
                }`}
                onClick={() => handleButtonClick(index)}
              ></button>
            ))}
          </div>

          <NoticeTextContent title={currentTitle} content={currentContent} />
        </div>
        <div className="flex items-center justify-center w-full h-96 md:w-1/2">
          {imageError ? (
            <div>Error loading image</div>
          ) : (
            <img
              src={currentImg}
              alt={currentTitle}
              onError={handleImageError}
            />
          )}
        </div>
      </div>
    </div>
  );
}

export default NoticeContent;
