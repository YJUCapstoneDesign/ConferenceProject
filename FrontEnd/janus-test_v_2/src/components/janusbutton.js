import React from 'react';
import "./janusbutton.css";

function Janusbutton({handleAudioActiveClick, handleVideoActiveClick, handleSharingActiveClick}) {
  return (
    <div className="janus_button mb-4 ml-4 block">
      <div className="group inline-block">
        <button
          className="outline-indigo-500 shadow-xl focus:outline-none px-3 py-3 bg-white flex items-center min-w-1 rounded-3xl border-2 ">
          <span>
            <svg
              className="fill-current h-4 w-4 transform group-hover:-rotate-180
              transition duration-500 ease-in-out rotate-0"
              xmlns="http://www.w3.org/2000/svg"  
              viewBox="0 0 20 20"
            >
              <path
                d="M9.293 12.95l.707.707L15.657 8l-1.414-1.414L10 10.828 5.757 6.586 4.343 8z"
              />
            </svg>
          </span>
        </button>
        <ul
          className="new_work bg-white border rounded-3xl transform scale-0 group-hover:scale-100 absolute 
        transition duration-500 ease-in-out origin-bottom min-w-10"
        >
          <li className="rounded-3xl px-3 py-3 hover:bg-indigo-400 hover:text-white transition duration-500 ease-in-out" onClick={handleAudioActiveClick}><svg
          xmlns="http://www.w3.org/2000/svg"
          width="20"
          height="20"
          viewBox="1 0 22 22"
          style={{ fill: 'currentColor' }}
        >
          <path d="M12 16c2.206 0 4-1.794 4-4V6c0-2.217-1.785-4.021-3.979-4.021a.933.933 0 0 0-.209.025A4.006 4.006 0 0 0 8 6v6c0 2.206 1.794 4 4 4z"></path>
          <path d="M11 19.931V22h2v-2.069c3.939-.495 7-3.858 7-7.931h-2c0 3.309-2.691 6-6 6s-6-2.691-6-6H4c0 4.072 3.061 7.436 7 7.931z"></path>
        </svg></li>

          <li className="rounded-3xl px-3 py-3 hover:bg-indigo-400 hover:text-white transition duration-500 ease-in-out" onClick={handleVideoActiveClick}>
            <svg
            xmlns="http://www.w3.org/2000/svg"
            width="20"
            height="20"
            viewBox="1 0 22 22"
            style={{ fill: 'currentColor', verticalAlign: 'middle' }}
            >
            <path d="M18 7c0-1.103-.897-2-2-2H4c-1.103 0-2 .897-2 2v10c0 1.103.897 2 2 2h12c1.103 0 2-.897 2-2v-3.333L22 17V7l-4 3.333V7z" />
        </svg>
          </li>
          <li className="rounded-3xl px-3 py-3 hover:bg-indigo-400 hover:text-white transition duration-500 ease-in-out" onClick={handleSharingActiveClick}>
              <svg
              xmlns="http://www.w3.org/2000/svg"
              width="20"
              height="20"
              viewBox="1 0 22 22"
              style={{ fill: 'currentColor', verticalAlign: 'middle' }}
              >
              <path d="M20 17.722c.595-.347 1-.985 1-1.722V5c0-1.103-.897-2-2-2H5c-1.103 0-2 .897-2 2v11c0 .736.405 1.375 1 1.722V18H2v2h20v-2h-2v-.278zM5 16V5h14l.002 11H5z" />
            </svg>
          </li>
          <li className="rounded-3xl px-3 py-3 hover:bg-red-500 hover:text-white transition duration-500 ease-in-out">
            <svg
            xmlns="http://www.w3.org/2000/svg"
            width="20"
            height="20"
            viewBox=" 0 2 22 22"
            style={{ fill: 'currentColor', transform: 'rotate(135deg)', msFilter: 'progid:DXImageTransform.Microsoft.BasicImage(rotation=2)' }}
            >
            <path d="m20.487 17.14-4.065-3.696a1.001 1.001 0 0 0-1.391.043l-2.393 2.461c-.576-.11-1.734-.471-2.926-1.66-1.192-1.193-1.553-2.354-1.66-2.926l2.459-2.394a1 1 0 0 0 .043-1.391L6.859 3.513a1 1 0 0 0-1.391-.087l-2.17 1.861a1 1 0 0 0-.29.649c-.015.25-.301 6.172 4.291 10.766C11.305 20.707 16.323 21 17.705 21c.202 0 .326-.006.359-.008a.992.992 0 0 0 .648-.291l1.86-2.171a.997.997 0 0 0-.085-1.39z" />
          </svg>
          </li>
        </ul>
      </div>  
    </div>
  );
}

export default Janusbutton;
