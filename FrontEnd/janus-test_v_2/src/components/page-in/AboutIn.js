import React from 'react';
import NavIn from './InNavbar';

function AboutIn() {
    return (
        <div className="container">
            <NavIn />
            <div className="mt-10">
                <div className="mb-4 md:mb-0 w-full max-w-screen-md mx-auto relative" style={{ height: '24em' }}>
                    <div className="absolute left-0 bottom-0 w-full h-full z-10"
                        style={{ backgroundImage: 'url(https://source.unsplash.com/random/?conference)' }}></div>
                    <img src="https://images.unsplash.com/photo-1493770348161-369560ae357d?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=2100&q=80" className="absolute left-0 top-0 w-full h-full z-0 object-cover" />
                    <div className="p-4 absolute bottom-0 left-0 z-20">
                        <a href="#"
                            className="px-4 py-1 bg-black text-gray-200 inline-flex items-center justify-center mb-2">Meeting Conference</a>
                        <h2 className="text-4xl font-semibold text-gray-100 leading-tight">
                          Introducing the video conferencing project to the Unmute team.
                        </h2>
                    </div>
                </div>

                <div className="px-4 lg:px-0 mt-12 text-gray-700 max-w-screen-md mx-auto text-lg leading-relaxed">
                <h2 className="text-2xl text-gray-800 font-semibold mb-4 mt-4">Introduction</h2>
                    <p className="pb-6">Welcome to UNMUTE, where innovation meets connectivity, creating a seamless and immersive communication experience. In an era where global interactions are crucial, our video conferencing platform stands at the forefront of technological advancements, bridging gaps and fostering collaboration across distances. Whether you are connecting with colleagues, conducting a virtual classroom, or catching up with loved ones, [Your Program Name] provides a robust and reliable solution tailored to your needs.</p>

                    <p className="pb-6">We understand the complexities of modern communication and have designed our platform to be intuitive and user-friendly, making virtual interactions as natural as possible. Our commitment to excellence drives us to continuously enhance our features, ensuring that you have the best tools at your disposal for every meeting, webinar, or conference call.</p>

                    <p className="pb-6">With UNMUTE, you are not just getting a product; you are joining a community dedicated to breaking down barriers and enabling meaningful connections. We are passionate about helping you achieve your goals, whether through seamless integration with your existing workflows or by providing innovative solutions to new challenges. Our platform is built to adapt to the ever-evolving landscape of digital communication, ensuring that you stay ahead in a rapidly changing world.
                    </p>
                    <p className='pb-6'>
                    Thank you for choosing UNMUTE. We are excited to be a part of your journey and look forward to supporting you in all your communication needs.</p>

                    <h2 className="text-2xl text-gray-800 font-semibold mb-4 mt-4">Our Vision</h2>

                    <p className="pb-6">At UNMUTE, our vision is to revolutionize the way people interact and collaborate. We believe that communication is the cornerstone of innovation and progress, and our goal is to create a platform that not only facilitates but enhances this interaction. We aim to design a solution that is not only functional but also intuitive, enjoyable, and accessible to everyone, regardless of their technical expertise.</p>


                    <p className="pb-6">In the modern world, the boundaries between work, education, and personal life are increasingly blurred. Our platform is designed to adapt to these changes, providing a versatile solution that meets the diverse needs of businesses, educational institutions, and individuals alike. Whether it's hosting a critical business meeting, delivering an engaging online lecture, or connecting with family and friends across the globe, UNMUTE ensures that every interaction is seamless and effective.</p>

                </div>
            </div>
        </div>
    );
}

export default AboutIn;
