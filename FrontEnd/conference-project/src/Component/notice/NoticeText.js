/* eslint-disable */

import React, { useState } from 'react';

function NoticeTextContent(props) {
    return (
        <React.Fragment>
            <div className="max-w-lg md:mx-12 md:order-2">
                <h1 className="text-3xl font-medium tracking-wide text-gray-800 dark:text-white md:text-4xl">
                    {props.title}
                </h1>
                <p className="mt-4 text-gray-600 dark:text-gray-300">{props.content}</p>
                <div className="mt-6">
                    <a
                        href="#"
                        className="block px-3 py-2 font-semibold text-center text-white transition-colors duration-200 transform bg-blue-500 rounded-md md:inline hover:bg-blue-400"
                    >
                        button
                    </a>
                </div>
            </div>
        </React.Fragment>
    );
}

export default NoticeTextContent;
