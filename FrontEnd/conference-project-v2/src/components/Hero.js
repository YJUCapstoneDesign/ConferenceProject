import React from 'react'
import './css/Hero.css'

const Hero = () => {
    return (
        <div className='hero'>
            <div className='content'>
                {/* <p>UNMUTE</p> */}
                <p><br/>The <span>UNMUTE</span>
                <br />project is a video conference
                <br />program with various functions.</p><br/>
                <button href='/' className='button'>Try it now</button>
            </div>
        </div>
    )
}

export default Hero