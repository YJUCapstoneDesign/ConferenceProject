import React from 'react'
import './css/Footer.css'

const Footer = () => {
    return (
        <div className='footer'>
            <div className='container'>
                <ul>
                    <li className='nav-item'>
                        <a href='/'>Home</a>
                    </li>
                    <li className='nav-item'>
                        <a href='#About'>About</a>
                    </li>
                    <li className='nav-item'>
                        <a href='#service'>Service</a>
                    </li>
                    <li className='nav-item'>
                        <a href='#notice'>Notice</a>
                    </li>
                </ul>
                <div className='bottom'>
                    <span className='line'></span>
                    <p>2020 Execute, Inc. All rights reserved</p>
                </div>
            </div>
        </div>
    )
}

export default Footer