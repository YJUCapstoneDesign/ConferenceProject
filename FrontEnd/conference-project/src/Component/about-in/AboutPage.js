import React, { useEffect } from 'react';
import Swiper from 'swiper';
import 'swiper/swiper-bundle.css';
// import "tailwindcss/tailwind.css";
import './Servicedesign.css';
import AboutText from './AboutText';
import AboutSwiper from './AboutSwiper';
import Navbar from '../NavBar';

function About() {
    // swiper style 설정
    useEffect(() => {
        const swiper = new Swiper('.swiper-container', {
            loop: true,
            slidesPerView: 1,
            spaceBetween: 8,
            autoplay: {
                delay: 8000,
            },
            pagination: {
                el: '.swiper-pagination',
                clickable: true,
            },
            breakpoints: {
                640: {
                    slidesPerView: 1,
                },
                1024: {
                    slidesPerView: 1,
                },
            },
        });

        return () => {
            swiper.destroy();
        };
    }, []);

    return (
        <div>
            {/* 배경설정 */}
            <div
                className="bg-no-repeat bg-cover bg-center relative"
                style={{
                    backgroundImage:
                        'url(https://images.unsplash.com/photo-1606938704652-3e588c2c9fd4?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1964&q=80)',
                }}
            >
                {/* 배경 밝기 설정 */}
                <div className="absolute bg-gradient-to-r from-gray-900 to-gray-900 opacity-75 inset-0 z-0" />

                <div className="min-h-screen flex justify-center">
                    <div className="z-10">
                        <Navbar />
                        <div className="grid grid-cols-2 gap-4 items-center z-10 pt-44">
                            <AboutText />
                            <AboutSwiper />
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default About;
