import React from 'react';
import loginImg from '../../img/login.png';

function LoginImage() {
    return (
        <React.Fragment>
            <img src={loginImg} alt="Login Illustration" className="object-cover w-full h-full" />
        </React.Fragment>
    );
}

export default LoginImage;
