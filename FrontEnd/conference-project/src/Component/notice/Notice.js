import React from "react";
import { useState } from "react";

import "react-responsive-carousel/lib/styles/carousel.min.css";
import { Carousel } from "react-responsive-carousel";
import imageData from "./NoticeImg";

function NoticeContent() {
  const renderSlides = imageData.map((img) => (
    <div key={img.alt}>
      <img src={img.url} alt={img.alt} />
    </div>
  ));

  const [current, setCurrent] = useState(0);
  function handleChange(index) {
    setCurrent(index);
  }

  return (
    <div className="bg-white dark:bg-gray-800">
      {/* 1 */}
      <nav className="bg-white dark:bg-gray-800">
        <div className="container p-6 mx-auto">
          <a
            className="block text-2xl font-bold text-center text-gray-800 dark:text-white lg:text-3xl hover:text-gray-700 dark:hover:text-gray-300"
            href="/"
          >
            UnMute
          </a>

          <div className="flex items-center justify-center mt-6 text-gray-600 capitalize dark:text-gray-300">
            <a
              href="/"
              className="text-gray-800 dark:text-gray-200 border-b-2 border-blue-500 mx-1.5 sm:mx-6"
            >
              home
            </a>

            <a
              href="/"
              className="border-b-2 border-transparent hover:text-gray-800 dark:hover:text-gray-200 hover:border-blue-500 mx-1.5 sm:mx-6"
            >
              features
            </a>

            <a
              href="/"
              className="border-b-2 border-transparent hover:text-gray-800 dark:hover:text-gray-200 hover:border-blue-500 mx-1.5 sm:mx-6"
            >
              pricing
            </a>

            <a
              href="/"
              className="border-b-2 border-transparent hover:text-gray-800 dark:hover:text-gray-200 hover:border-blue-500 mx-1.5 sm:mx-6"
            >
              blog
            </a>
          </div>
        </div>
      </nav>

      {/* 2 */}
      <div className="container flex flex-col px-6 py-4 mx-auto space-y-6 md:h-128 md:py-16 md:flex-row md:items-center md:space-x-6">
        <div className="flex flex-col items-center w-full md:flex-row md:w-1/2">
          <div className="flex justify-center order-2 mt-6 md:mt-0 md:space-y-3 md:flex-col">
            <button className="w-3 h-3 mx-2 bg-blue-500 rounded-full md:mx-0 focus:outline-none"></button>
            <button className="w-3 h-3 mx-2 bg-gray-300 rounded-full md:mx-0 focus:outline-none hover:bg-blue-500"></button>
            <button className="w-3 h-3 mx-2 bg-gray-300 rounded-full md:mx-0 focus:outline-none hover:bg-blue-500"></button>
            <button className="w-3 h-3 mx-2 bg-gray-300 rounded-full md:mx-0 focus:outline-none hover:bg-blue-500"></button>
          </div>

          <div className="max-w-lg md:mx-12 md:order-2">
            <h1 className="text-3xl font-medium tracking-wide text-gray-800 dark:text-white md:text-4xl">
              The best Apple Watch apps
            </h1>
            <p className="mt-4 text-gray-600 dark:text-gray-300">
              Lorem ipsum, dolor sit amet consectetur adipisicing elit. Aut quia
              asperiores alias vero magnam recusandae adipisci ad vitae
              laudantium quod rem voluptatem eos accusantium cumque.
            </p>
            <div className="mt-6">
              <a
                href="#"
                className="block px-3 py-2 font-semibold text-center text-white transition-colors duration-200 transform bg-blue-500 rounded-md md:inline hover:bg-blue-400"
              >
                Download from App Store
              </a>
            </div>
          </div>
        </div>

        <div className="flex items-center justify-center w-full h-96 md:w-1/2">
          <Carousel
            showArrows={false}
            autoPlay={true}
            infiniteLoop={true}
            showThumbs={false}
            selectedItem={imageData[current]}
            onChange={handleChange}
            className="w-[400px] lg:hidden"
          >
            {renderSlides}
          </Carousel>
        </div>
      </div>
    </div>
  );
}

export default NoticeContent;
