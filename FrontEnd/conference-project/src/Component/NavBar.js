import {Link} from 'react-router-dom';
import React from 'react';
import './Nav.css';

function Nav() {
    return(
        <div>
            <div className='navWrap'>
                <div className='navLogo'>
                    Zoom
                </div>
                <div className='menubar'>
                    <Link className="navbarMenu" to={'/'}>HOME</Link>
                    <Link className="navbarMenu" to={'/About'}>ABOUT</Link>
                    <Link className="navbarMenu" to={'/Service'}>SERVICES</Link>
                    <Link className="navbarMenu" to={'/Notice'}>NOTICE</Link>
                    <Link className='navbarButton' to={'https://www.naver.com'}>
                        <div className='Button'>LOGIN</div>
                    </Link>
                </div>
            </div>
        </div>
    )
} 

export default Nav;