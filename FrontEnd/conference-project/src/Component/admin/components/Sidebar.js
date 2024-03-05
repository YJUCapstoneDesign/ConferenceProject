import React from "react";
import { BiHome, BiBookAlt, BiMessage, BiSolidReport,
BiStats, BiTask, BiHelpCircle } from 'react-icons/bi';
import '../styles/sidebar.css';
const Sidebar = () => {
    return <div className="menu">
        <div className="logo">
             <BiBookAlt className="logo-icon" />
             <h2>Admin</h2>
        </div>

        <div className="menu--list">
            <a href="#" className="item active">
                <BiHome className="logo"/>
                Dashboard
            </a>
            <a href="#" className="item">
                <BiTask className="logo"/>
                Assignment
            </a>
            <a href="#" className="item">
                <BiSolidReport className="logo"/>
                Report
            </a>
            <a href="#" className="item">
                <BiStats className="logo"/>
                Stats
            </a>
            <a href="#" className="item">
                <BiMessage className="logo"/>
                Message
            </a>
            <a href="#" className="item">
                <BiHelpCircle className="logo"/>
                Help
            </a>
        </div>
    </div>
};

export default Sidebar;