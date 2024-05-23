import React from 'react';
import { Link } from 'react-router-dom';

function Sidebar() {
    const OpenPopupMind = () => {
        window.open("/mindmap","마인드 맵","width=900px,height=600px");
    }

    const OpenPopupWhite = () => {
        window.open("/whiteboard","화이트보드","width=900px,height=600px");
    }

    return (
        <div className="min-h-screen bg-gray-100">
        <div className="sidebar min-h-screen w-[3.35rem] overflow-hidden border-r hover:w-56 ease-in-out duration-500  hover:bg-white hover:shadow-lg"> {/* hover시 느리게 화면 크기 변경 ease-in-out duration */}
          <div className="flex h-screen flex-col justify-between pt-2 pb-6">
            <div>
                <ul className="mt-6 space-y-2 tracking-wide">
                <li className="min-w-max">
                    <Link to="#" className="bg group flex items-center space-x-4 rounded-full px-4 py-3 text-gray-600">
                    <svg xmlns="http://www.w3.org/2000/svg" className="h-5 w-5" viewBox="0 0 24 24" fill="currentColor">
                        <path className="fill-current text-gray-600 group-hover:text-indigo-600" fillRule="evenodd" d="M9 4C10.1046 4 11 4.89543 11 6V12.8271C10.1058 12.1373 8.96602 11.7305 7.6644 11.5136L7.3356 13.4864C8.71622 13.7165 9.59743 14.1528 10.1402 14.7408C10.67 15.3147 11 16.167 11 17.5C11 18.8807 9.88071 20 8.5 20C7.11929 20 6 18.8807 6 17.5V17.1493C6.43007 17.2926 6.87634 17.4099 7.3356 17.4864L7.6644 15.5136C6.92149 15.3898 6.1752 15.1144 5.42909 14.7599C4.58157 14.3573 4 13.499 4 12.5C4 11.6653 4.20761 11.0085 4.55874 10.5257C4.90441 10.0504 5.4419 9.6703 6.24254 9.47014L7 9.28078V6C7 4.89543 7.89543 4 9 4ZM12 3.35418C11.2671 2.52376 10.1947 2 9 2C6.79086 2 5 3.79086 5 6V7.77422C4.14895 8.11644 3.45143 8.64785 2.94126 9.34933C2.29239 10.2415 2 11.3347 2 12.5C2 14.0652 2.79565 15.4367 4 16.2422V17.5C4 19.9853 6.01472 22 8.5 22C9.91363 22 11.175 21.3482 12 20.3287C12.825 21.3482 14.0864 22 15.5 22C17.9853 22 20 19.9853 20 17.5V16.2422C21.2044 15.4367 22 14.0652 22 12.5C22 11.3347 21.7076 10.2415 21.0587 9.34933C20.5486 8.64785 19.8511 8.11644 19 7.77422V6C19 3.79086 17.2091 2 15 2C13.8053 2 12.7329 2.52376 12 3.35418ZM18 17.1493V17.5C18 18.8807 16.8807 20 15.5 20C14.1193 20 13 18.8807 13 17.5C13 16.167 13.33 15.3147 13.8598 14.7408C14.4026 14.1528 15.2838 13.7165 16.6644 13.4864L16.3356 11.5136C15.034 11.7305 13.8942 12.1373 13 12.8271V6C13 4.89543 13.8954 4 15 4C16.1046 4 17 4.89543 17 6V9.28078L17.7575 9.47014C18.5581 9.6703 19.0956 10.0504 19.4413 10.5257C19.7924 11.0085 20 11.6653 20 12.5C20 13.499 19.4184 14.3573 18.5709 14.7599C17.8248 15.1144 17.0785 15.3898 16.3356 15.5136L16.6644 17.4864C17.1237 17.4099 17.5699 17.2926 18 17.1493Z" clipRule="evenodd" />
                    </svg>
                    <span className="group-hover:text-gray-700">BrainStorming</span>
                    </Link>
                </li>   
                <li className="min-w-max" onClick={OpenPopupMind}>
                    <Link to="/mindmap" className="group flex items-center space-x-4 rounded-md px-4 py-3 text-gray-600" target='_blank'>
                    <svg xmlns="http://www.w3.org/2000/svg" className="h-5 w-5" viewBox="0 0 24 24" fill="currentColor">
                        <path className="fill-current text-gray-600 group-hover:text-indigo-600" fillRule="evenodd" d="M15.874 13C15.4299 14.7252 13.8638 16 12 16C10.1362 16 8.57006 14.7252 8.12602 13H3V11H8.12602C8.57006 9.27477 10.1362 8 12 8C13.8638 8 15.4299 9.27477 15.874 11H21V13H15.874ZM12 14C13.1046 14 14 13.1046 14 12C14 10.8954 13.1046 10 12 10C10.8954 10 10 10.8954 10 12C10 13.1046 10.8954 14 12 14Z" clipRule="evenodd" />
                    </svg>
                    <span className="group-hover:text-gray-700">MindMap</span>
                    </Link>
                </li>
                <li className="min-w-max" onClick={OpenPopupWhite}>
                    <Link to="/whiteboard" className="group flex items-center space-x-4 rounded-md px-4 py-3 text-gray-600" target='_blank'>
                    <svg xmlns="http://www.w3.org/2000/svg" className="h-5 w-5" viewBox="0 0 24 24" fill="currentColor">
                        <path className="fill-current text-gray-600 group-hover:text-indigo-600" d="M8.58579 17H3V15H21V17H15.4142L18.6569 20.2426L17.2426 21.6569L13 17.4142V20H11V17.4142L6.75736 21.6569L5.34315 20.2426L8.58579 17ZM5 3H19C19.5523 3 20 3.44772 20 4V14H4V4C4 3.44772 4.44772 3 5 3ZM6 5V12H18V5H6Z" />
                    </svg>
                    <span className="group-hover:text-gray-700">WhiteBoard</span>
                    </Link>
                </li>
                </ul>
            </div>
        </div>
      </div>
    </div>
    );
}

export default Sidebar;
