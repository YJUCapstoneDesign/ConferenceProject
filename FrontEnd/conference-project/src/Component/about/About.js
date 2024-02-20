import React from 'react';
import ImgWithShadow from './Aboutimg';
import AboutText from './AboutText';

function AboutContent() {
    return (
        <div className="AboutContent">
            <div className="w-full lg:max-w-2xl mx-auto lg:mx-40 lg:mt-52">
                <ImgWithShadow />
            </div>
            <div className="w-full mx-auto max-w-screen-xl -mt-96">
                <AboutText />
            </div>
        </div>
    );
}

export default AboutContent;
