import React, { useState } from 'react'
import { FaBars, FaTimes } from 'react-icons/fa'
import '../css/Navbar.css'
import { Link } from 'react-router-dom'

const InNavbar = () => {
    const [click, setClick] = useState(false)
    const handleClick = () => setClick(!click)
    // const closeMenu = () => setClick(false)


    return (
        <div className='header bg-slate-950'>
            <nav className='navbar'>
                <Link to='/' className='logo'>
                    UNMUTE
                </Link>
                {/* <div className='hamburger' onClick={handleClick}>
                    {click ? (<FaTimes size={30} style={{ color: '#ffffff' }} />)
                        : (<FaBars size={30} style={{ color: '#ffffff' }} />)}

                </div> */}
            </nav>
        </div>
    )
}

export default InNavbar