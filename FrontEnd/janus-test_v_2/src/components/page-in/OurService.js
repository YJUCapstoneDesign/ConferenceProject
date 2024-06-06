import React from 'react';
import NavIn from './InNavbar';

function OurService() {
    return (
        <div className="container mx-auto flex flex-col items-center">
            <NavIn />
            <div className="mt-10 w-full flex flex-col items-center">
                <div className="mb-4 md:mb-0 w-full max-w-screen-md relative flex justify-center items-center" style={{ height: '24em' }}>
                    <div className="absolute inset-0 w-full h-full z-10" style={{ backgroundImage: 'url(https://source.unsplash.com/random/?conference)', backgroundSize: 'cover', backgroundPosition: 'center' }}></div>
                    <img src="https://source.unsplash.com/random/?conference" className="absolute inset-0 w-full h-full z-0 object-cover" alt="Conference" />
                    <div className="p-4 absolute bottom-0 left-0 z-20 bg-black bg-opacity-50 w-full">
                        <div className="px-4 py-1 bg-indigo-800 text-gray-200 inline-flex items-center justify-center mb-2">Our Service</div>
                        <h2 className="text-4xl font-semibold text-gray-100 leading-tight">
                            Introducing our core services.
                        </h2>
                    </div>
                </div>
                <div className="px-4 lg:px-0 mt-12 text-gray-700 max-w-screen-md mx-auto text-lg leading-relaxed">
                    <section className="text-gray-600 body-font">
                        <div className="container px-5 py-24 mx-auto">
                            <div className="-my-8 divide-y-2 divide-gray-100">
                                <div className="py-8 flex flex-wrap md:flex-nowrap">
                                    <div className="md:w-64 md:mb-0 mb-6 flex-shrink-0 flex flex-col">
                                        <span className="font-semibold title-font text-gray-700">Video Conference</span>
                                    </div>
                                    <div className="md:flex-grow">
                                        <h2 className="text-2xl font-medium text-gray-900 title-font mb-2">Communicate with various team members through video conference!</h2>
                                        <p className="leading-relaxed">In today's fast-paced world, staying connected is more important than ever. Our video conferencing service provides a seamless and efficient way to communicate, collaborate, and connect with people from around the globe. </p>
                                    </div>
                                </div>
                                <div className="py-8 flex flex-wrap md:flex-nowrap">
                                    <div className="md:w-64 md:mb-0 mb-6 flex-shrink-0 flex flex-col">
                                        <span className="font-semibold title-font text-gray-700">MindMap</span>
                                    </div>
                                    <div className="md:flex-grow">
                                        <h2 className="text-2xl font-medium text-gray-900 title-font mb-2">Use a mind map to organize your thoughts with your team</h2>
                                        <p className="leading-relaxed">In the world of brainstorming and idea generation, having a structured way to organize your thoughts can make all the difference. Our mind mapping service offers a powerful tool to visualize your ideas, streamline your thought process, and enhance your productivity.</p>
                                    </div>
                                </div>
                                <div className="py-8 flex flex-wrap md:flex-nowrap">
                                    <div className="md:w-64 md:mb-0 mb-6 flex-shrink-0 flex flex-col">
                                        <span className="font-semibold title-font text-gray-700">Brain Storming</span>
                                    </div>
                                    <div className="md:flex-grow">
                                        <h2 className="text-2xl font-medium text-gray-900 title-font mb-2">Use brainstorming to generate your ideas!</h2>
                                        <p className="leading-relaxed">In today's dynamic world, innovative ideas are the cornerstone of success. Our brainstorming service provides a structured and collaborative environment to spark creativity and generate groundbreaking ideas. Whether you are working on a new project, solving a complex problem, or simply seeking inspiration, our platform offers a range of tools to facilitate effective brainstorming sessions, including the Six Thinking Hats method, SWOT analysis, and the Crazy Eights technique.</p>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </section>
                </div>
            </div>
        </div>
    )
}

export default OurService;
