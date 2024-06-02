import React from 'react';
import { Link } from 'react-router-dom';
import './css/Hero.css';

const Hero = () => {
    return (
        <div className='hero'>
            <div className='content'>
                <p><br/>The <span>UNMUTE</span>
                <br />project is a video conference
                <br />program with various functions.</p><br/>
                <Link to='/Select' className='button'>Try it now</Link>
            </div>
        </div>
    );
};

export default Hero;
