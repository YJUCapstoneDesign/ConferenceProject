import React from 'react';

const About = () => {
    return (
        <div className="w-full" id='About'>
            <div className="flex bg-white" style={{ height: '600px' }}>
                <div className="flex items-center text-center lg:text-left px-8 md:px-12 lg:w-1/2">
                    <div>
                        <h2 className="text-3xl font-semibold text-indigo-600 md:text-4xl">About<span className="text-gray-800"><br/>Our Project</span></h2>
                        <p className="mt-2 text-sm text-gray-500 md:text-lg">Our project is a video conference program using AI and brainstorming techniques. We will briefly summarize the meeting contents through AI and help select topics through mind maps. Our goal is to develop a more advanced conference program with these features.</p>
                        <div className="flex justify-center lg:justify-start mt-6">
                            <a className="px-4 py-3 bg-indigo-600 text-gray-200 text-xs font-semibold rounded-3xl hover:bg-indigo-700" href="/AboutIn">Learn more</a>
                        </div>
                    </div>
                </div>
                <div className="hidden lg:block lg:w-1/2" style={{ clipPath: 'polygon(10% 0, 100% 0%, 100% 100%, 0 100%)' }}>
                    <div className="h-full object-cover" style={{ backgroundImage: 'url(https://source.unsplash.com/random/?conference)' }}>
                        <div className="h-full bg-black opacity-25"></div>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default About;
