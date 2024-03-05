import React from "react";
import ProfileHeader from "./ProfileHeader";
import '../styles/profile.css';
import userImage from '../assets/image.jpg';
import { BiBook } from "react-icons/bi";
const courses = [
    {
        title: 'HTML CSS',
        state: 'online',
        icon: <BiBook />
    },
    {
        title: 'JavaScript',
        state: 'online',
        icon: <BiBook />
    },
    {
        title: 'React.js',
        state: 'online',
        icon: <BiBook />
    },
]

const Profile = () => {
    return <div className="profile">
        <ProfileHeader />
        <div className="user--profile">
            <div className="user--detail">
                <img src={userImage} alt="" />
                <h3 className="username">Jhone Doe</h3>
                <span className="profession">web programmer</span>
            </div>
            <div className="user-courses">
                {courses.map((courses) => (
                    <div className="course">
                        <div className="course--detail">
                            <div className="course--cover">{courses.icon}</div>
                            <div className="course-name">
                                <h5 className="title">{courses.title}</h5>
                                <span className="duration">{courses.duration}</span>
                            </div>
                        </div>
                        <div className="action"></div>
                    </div>
                ))}
            </div>
        </div>
    </div>
};

export default Profile;