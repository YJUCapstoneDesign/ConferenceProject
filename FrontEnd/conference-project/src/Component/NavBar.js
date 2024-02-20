import { Link } from 'react-router-dom';
import React from 'react';
import paths from './paths';

function Nav(props) {
    const linkAttr = {
        color: 'hover:text-cyan-500 text-indigo-700 transition-colors font-black',
    };

    const navItems = [
        { text: 'HOME', path: paths.home },
        { text: 'ABOUT', path: paths.about },
        { text: 'SERVICE', path: paths.service },
        { text: 'NOTICE', path: paths.notice },
    ];

    return (
        <div>
            <div className="h-20 mx-auto px-5 flex items-center justify-between text-white">
                <Link
                    className="text-3xl cursor-pointer text-white font-bold"
                    to={paths.home}
                    style={{ margin: '20px', fontSize: '2.5rem' }}
                >
                    Zoom
                </Link>

                <ul
                    className="flex items-center gap-5"
                    style={{
                        marginRight: '20px',
                    }}
                >
                    {navItems.map((item) => (
                        <li key={item.text}>
                            <Link
                                className={`${linkAttr.color} text-white`}
                                to={item.path}
                                style={{
                                    marginRight: '25px',
                                }}
                            >
                                {item.text}
                            </Link>
                        </li>
                    ))}
                    <li>
                        <Link
                            className="font-bold text-white rounded-xl"
                            to={paths.login}
                            style={{
                                padding: '0.5rem 1rem',
                                backgroundColor: '#000080',
                            }}
                        >
                            LOGIN
                        </Link>
                    </li>
                </ul>
            </div>
        </div>
    );
}

export default Nav;
