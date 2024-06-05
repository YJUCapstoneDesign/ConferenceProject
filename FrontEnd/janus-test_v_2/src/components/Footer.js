import React from 'react';
import { Link } from 'react-router-dom';

const Footer = () => {
    return (
        <div className="w-full min-h-44 flex items-center justify-center bg-black">
            <div className="md:w-2/3 w-full px-4 text-white flex flex-col">
                <div className="flex flex-col">
                    <ul className="flex flex-row mt-24 mb-12 justify-between">
                        <li className="hidden md:block">
                            <Link to="/" className="cursor-pointer text-gray-600 hover:text-indigo-500 uppercase">
                                Home
                            </Link>
                        </li>
                        <li className="hidden md:block">
                            <Link to="/AboutIn" className="cursor-pointer text-gray-600 hover:text-indigo-500 uppercase">
                                About
                            </Link>
                        </li>
                        <li className="hidden md:block">
                            <Link to="/OurService" className="cursor-pointer text-gray-600 hover:text-indigo-500 uppercase">
                                Services
                            </Link>
                        </li>
                        <li className="hidden md:block">
                            <Link to="#" className="cursor-pointer text-gray-600 hover:text-indigo-500 uppercase">
                                Note
                            </Link>
                        </li>
                    </ul>
                    <hr className="border-t-4 border-indigo-500 w-20 mx-auto" />
                    <p className="w-full text-center my-12 text-gray-600">Copyright © 2024 UNMUTE</p>
                </div>
            </div>
        </div>
    );
}

export default Footer;
