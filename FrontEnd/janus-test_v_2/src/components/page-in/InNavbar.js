import React, { useState } from 'react'
import { FaBars, FaTimes } from 'react-icons/fa'
import '../css/Navbar.css'

const Navbar = () => {
    const [click, setClick] = useState(false)
    const handleClick = () => setClick(!click)
    // const closeMenu = () => setClick(false)


    return (
        <div className='header bg-slate-950'>
            <nav className='navbar'>
                <a href='/' className='logo'>
                    UNMUTE
                </a>
                {/* <div className='hamburger' onClick={handleClick}>
                    {click ? (<FaTimes size={30} style={{ color: '#ffffff' }} />)
                        : (<FaBars size={30} style={{ color: '#ffffff' }} />)}

                </div> */}
            </nav>
        </div>
    )
}

export default Navbar