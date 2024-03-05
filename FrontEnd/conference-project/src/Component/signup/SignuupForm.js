import React from 'react';
import { useEffect, useState } from 'react';
import SignupBnt from './SignupButton';
import axios from 'axios';

function SignupForm() {
    // Array of input labels
    const inputLabels = ['Name', 'Email', 'Nickname', 'Password', 'Address', 'Phone'];

    return (
        <React.Fragment>
            <form action="/" method="POST" className="my-14">
                {inputLabels.map((label, index) => (
                    <div className="mb-4" key={index}>
                        <label
                            htmlFor={`input-${index}`}
                            className="block text-gray-600 flex text-black font-bold"
                            style={{ marginBottom: '10px' }}
                        >
                            {label}
                        </label>
                        <input
                            type={label === 'Password' ? 'password' : 'text'}
                            id={`input-${index}`}
                            name={`input-${index}`}
                            className="w-full border border-gray-300 rounded-md py-2 px-3 focus:outline-none focus:border-blue-500 rounded-xl"
                            autoComplete="off"
                            style={{ backgroundColor: '#FFEBDF' }}
                        />
                    </div>
                ))}
                {/* Signup Button */}
                <SignupBnt />
            </form>
        </React.Fragment>
    );
}

export default SignupForm;
