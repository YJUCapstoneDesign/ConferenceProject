import React from 'react';
import HomeText from './HomeText';
import HomeButton from './HomeButton';
import HomeImage from './HomeImage';
import Nav from '../NavBar';
import News from '../news/news';
import Notice from '../notice/Notice';
import Service from '../service/Service';
import About from '../about/About';
import Footer from '../footer/Footer';

function HomeContent() {
    return (
        <React.Fragment>
            <div className="relative h-[112.7vh] sm:pt-0 bg-banner">
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
            <div data-spy="scroll" data-target="#navbar-example2" data-offset="0">
                <div id="one">
                    <About />
                </div>
                <div id="two">
                    <Notice />
                </div>
                <div id="three">
                    <Service />
                </div>
                <div id="four">
                    <News />
                </div>
                <div id='five'>
                    <Footer />
                </div>
            </div>
        </React.Fragment>
    );
}

export default HomeContent;
