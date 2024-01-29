import React from 'react';

function SignupButton() {
    return (
        <React.Fragment>
            <button
                type="submit"
                className="bg-black hover:bg-blue-600 text-white font-semibold rounded-md p-3 px-4 w-full mt-10 rounded-3xl"
            >
                Sign Up
            </button>
        </React.Fragment>
    );
}

export default SignupButton;
