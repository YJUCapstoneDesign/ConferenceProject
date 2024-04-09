import React, { useState } from 'react'
import {Link} from 'react-scroll'
import { FaBars, FaTimes } from 'react-icons/fa'

import './css/Navbar.css'

const Navbar = () => {

    const [click, setClick] = useState(false)
    const handleClick = () => setClick(!click)

    const closeMenu = () => setClick(false)

    return (
        <div className='header'>
            <nav className='navbar'>
                <a href='/' className='logo'>
                    UNMUTE
                </a>
                <div className='hamburger' onClick={handleClick}>
                    {click ? (<FaTimes size={30} style={{ color: '#ffffff' }} />)
                        : (<FaBars size={30} style={{ color: '#ffffff' }} />)}

                </div>
                <ul className={click ? "nav-menu active" : "nav-menu"}>
                    <li className='nav-item'>
                        <Link to="hero" spy={true} smooth={true} offset={0} duration={500} onClick={closeMenu}>Home</Link>
                    </li>
                    <li className='nav-item'>
                        <Link to="About" spy={true} smooth={true} offset={-130} duration={500} onClick={closeMenu}>About</Link>
                    </li>
                    <li className='nav-item'>
                        <Link to="service" spy={true} smooth={true} offset={-240} duration={500} onClick={closeMenu}>Service</Link>
                    </li>
                    <li className='nav-item'>
                        <Link to="notice" spy={true} smooth={true} offset={-150} duration={500} onClick={closeMenu}>Note</Link>
                    </li>
                    <li className='nav-item'>
                        <a href='/Login'>Login</a>
                    </li>
                </ul>
            </nav>
        </div>
    )
}

export default Navbar