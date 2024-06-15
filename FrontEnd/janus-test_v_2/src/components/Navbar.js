import React, { useEffect, useState } from 'react';
import { Link } from 'react-scroll';
import { FaBars, FaTimes } from 'react-icons/fa';
import { Link as RouterLink, useNavigate } from 'react-router-dom';
import './css/Navbar.css';
import api from '../components/page-in/api';

const baseURL = process.env.REACT_SPRING_SERVER;

const Navbar = () => {
  const [click, setClick] = useState(false);
  const [image, setImage] = useState(null);
  const handleClick = () => setClick(!click);
  const closeMenu = () => setClick(false);
  const navigate = useNavigate();
  const LoginStateToken = localStorage.getItem('accessToken');
  const RefreshToken = "Bearer " + JSON.parse(localStorage.getItem('refreshToken'));  

  useEffect(() => {
    if (LoginStateToken) {
      getProfileImage();
    }}, []);

  const getProfileImage = async () => {
    try {
      const response = await api.get('/api/image');
      if (response.status === 200) {
        
        let imageUrl = response.data;
        console.log(imageUrl);
        if (imageUrl.startsWith('/') === true) {
          imageUrl = baseURL + response.data;
        }
        setImage(imageUrl);
      }
    } catch (err) {
      console.log(err);
    }
  }

  const logout = async () => {
    try {
      const response = await api.get('/api/logout', {
        headers: {
          'Authorization-refresh': RefreshToken,
        },
      });
      if (response.status === 200) {
        alert('로그아웃 성공');
        localStorage.removeItem('accessToken');
        localStorage.removeItem('refreshToken');
        navigate('/signin');
      }
    } catch (err) {
      console.log(err);
      alert('로그아웃 실패');
    }
  };

  return (
    <div className='header'>
      <nav className='navbar'>
        <RouterLink to='/' className='logo'>
          UNMUTE
        </RouterLink>
        <div className='hamburger' onClick={handleClick}>
          {click ? (
            <FaTimes size={30} style={{ color: '#ffffff' }} />
          ) : (
            <FaBars size={30} style={{ color: '#ffffff' }} />
          )}
        </div>
        <ul className={click ? 'nav-menu active' : 'nav-menu'}>
          <li className='nav-item'>
            <Link to='hero' spy={true} smooth={true} offset={0} duration={500} onClick={closeMenu}>
              Home
            </Link>
          </li>
          <li className='nav-item'>
            <Link to='About' spy={true} smooth={true} offset={-130} duration={500} onClick={closeMenu}>
              About
            </Link>
          </li>
          <li className='nav-item'>
            <Link to='service' spy={true} smooth={true} offset={-240} duration={500} onClick={closeMenu}>
              Service
            </Link>
          </li>
          <li className='nav-item'>
            <Link to='notice' spy={true} smooth={true} offset={-150} duration={500} onClick={closeMenu}>
              Note
            </Link>
          </li>
          {LoginStateToken ? (
            <li className='nav-item logout' onClick={logout}>
              Log out
            </li>
          ) : (
            <li className='nav-item'>
              <RouterLink to='/signin'>Login</RouterLink>
            </li>
          )}
          {LoginStateToken && (
          <li className='nav-item1'>
          <RouterLink to='/Mypage'>
            <img
              className='rounded-full w-10 h-10 object-cover'
              src={image ? image : 'https://source.unsplash.com/random/?face'}
              alt='avatar'
            />
          </RouterLink>
        </li>
          )}
        </ul>
      </nav>
    </div>
  );
};

export default Navbar;
