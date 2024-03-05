import React from "react";
import "../styles/UserList.css";
import Image1 from '../assets/image.jpg'; 

const admin = [
    {
        image: Image1,
        name: 'Prof. Jhone Doe',
        state: 'Online',
    },
    {
        image: Image1,
        name: 'Prof. Jhone Doe',
        state: 'Online',
    },
    {
        image: Image1,
        name: 'Prof. Jhone Doe',
        state: 'Online',
    },
    {
        image: Image1,
        name: 'Prof. Jhone Doe',
        state: 'Online',
    },
];

const TeacherList = () => {
    return (
    <div className="teacher--list">
        <div className="list--header">
            <h2>User List</h2>
            <select>
                <option value="English" > English </option>
                <option value="Korean" > Korean </option>
            </select>
        </div>
        <div className="list--container">
            {admin.map(admin => (
                <div className="list">
                    <div className="teacher--detail">
                        <img src={admin.image} alt={admin.name} />
                        <h2>{admin.name}</h2>
                    </div>
                    <span>{admin.state}</span>
                    <button className="button-delete">delete</button>
                    <span className="teacher--todo">:</span>
                </div>
            ))}
        </div>
    </div>
    );
};

export default TeacherList;
