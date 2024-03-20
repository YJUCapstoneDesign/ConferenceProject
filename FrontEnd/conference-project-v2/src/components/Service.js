/* eslint-disable */
import React from 'react';

import { Swiper, SwiperSlide } from 'swiper/react';
import { Pagination, EffectCoverflow, Autoplay } from 'swiper/modules';

import 'swiper/css/bundle';
import './Service.css';

const slider = [
    {
        title: 'video conference',
        description: 'Start a video conference now',
        url: 'https://images.unsplash.com/photo-1542744173-8e7e53415bb0?q=80&w=3870&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3Ds',
    },
    {
        title: 'brain storming',
        description: 'Use brainstorming techniques to generate ideas faster',
        url: 'https://images.unsplash.com/photo-1553877522-43269d4ea984?q=80&w=3870&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
    },
    {
        title: 'White board',
        description: 'Write down what comes to mind on the whiteboard',
        url: 'https://images.unsplash.com/photo-1573497491208-6b1acb260507?q=80&w=3870&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
    },
    {
        title: 'Mind map',
        description: 'Organize your ideas using mind maps',
        url: 'https://images.unsplash.com/photo-1522071820081-009f0129c71c?q=80&w=3870&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
    },
    {
        title: 'Summarize using AI',
        description: 'AI-powered summaries make meetings more efficient.',
        url: 'https://images.unsplash.com/photo-1529119368496-2dfda6ec2804?q=80&w=3870&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
    },
];

function ServiceContent() {
    return (
        <div className="carousel text-left">
            <div>
                <div className="carousel-content" id='service'>
                    <span>Unmute</span>
                    <h1>What are our services</h1>
                    <hr></hr>
                    <p>The best conference program with various features</p>
                    <a href="/Ourservice" className="slider-btn">
                        more information
                    </a>
                </div>
            </div>
            <Swiper
                className="myswiper"
                modules={[Pagination, EffectCoverflow, Autoplay]}
                effect={'coverflow'}
                grabCursor={true}
                centeredSlides={true}
                coverflowEffect={{
                    rotate: 0,
                    stretch: 0,
                    depth: 100,
                    modifier: 3,
                    slideShadows: true,
                }}
                loop={true}
                pagination={{ clickable: true }}
                autoplay={{
                    delay: 5000,
                    disableOnInteraction: false,
                }}
                breakpoints={{
                    640: {
                        slidesPerView: 2,
                    },
                    768: {
                        slidesPerView: 1,
                    },
                    1024: {
                        slidesPerView: 2,
                    },
                    1560: {
                        slidesPerView: 3,
                    },
                }}
            >
                {slider.map((data, index) => (
                    <SwiperSlide key={index} style={{ backgroundImage: `url(${data.url})` }} className="myswiper-slider">
                        <div>
                            <h2>{data.title}</h2>
                            <p>{data.description}</p>
                            <a href={`${data.url}`} target="_blank" className="slider-btn">
                                explore
                            </a>
                        </div>
                    </SwiperSlide>
                ))}
            </Swiper>
        </div>
    );
}

export default ServiceContent;
