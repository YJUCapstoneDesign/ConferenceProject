import React from 'react';
import HomeText from './HomeText';
import HomeButton from './HomeButton';
import HomeImage from './HomeImage';
import Nav from '../NavBar';
import About from '../About';
import Service from '../Service';
import Notice from '../notice/Notice';

function HomeContent() {
    return (
        <React.Fragment>
            <div className="relative h-screen pt-10 sm:pt-0 mb-10 bg-banner">
                <Nav />
                <div className="container mx-auto grid grid-cols-1 md:grid-cols-2 items-center md:gap-20 mt-48">
                    <div className="content">
                        <HomeText />
                        <div clasNames="flex gap-4 mt-10">
                            <HomeButton />
                        </div>
                    </div>
                    <div className="relative sm:mt-0 mt-10 px-6 sm:px-0">
                        <HomeImage />
                    </div>
                </div>
            </div>
            <About />
            <Service />
            <Notice />
        </React.Fragment>
    );
}

export default HomeContent;
