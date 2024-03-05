import { MdMeetingRoom } from "react-icons/md";
import { FaPeopleGroup } from "react-icons/fa6";
import { IoIosMan } from "react-icons/io";

const course = [
    {
        title: 'number of rooms',
        icon: <MdMeetingRoom />,
    },
    {
        title: 'member list',
        duration: '2 Hour',
        icon: <FaPeopleGroup />,
    },
    {
        title: 'number of users',
        duration: '2 Hour',
        icon: <IoIosMan />,
    },
]
const Card = () => {
    return <div className='card--container'>
        {course.map((item) => (
            <div className='card'> 
                <div className='card--cover'>{item.icon}</div>
                <div className='card--title'>
                    <h2>{item.title}</h2>
                </div>
            </div>
        ))}
    </div>
};

export default Card;