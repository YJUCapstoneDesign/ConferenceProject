import { Link } from "react-router-dom";
import React from "react";
import paths from "./paths";

function Nav() {
  const linkAttr = {
    color: "hover:text-cyan-500 text-indigo-700 transition-colors font-black",
  };

  const navItems = [
    { text: "HOME", path: paths.home },
    { text: "ABOUT", path: paths.about },
    { text: "SERVICE", path: paths.service },
    { text: "NOTICE", path: paths.notice },
  ];

  return (
    <div className="shadow bg-white">
      <div className="h-16 mx-auto px-5 flex items-center justify-between">
        <Link
          className="text-3xl cursor-pointer text-indigo-700 font-black"
          to={paths.home}
        >
          Zoom
        </Link>

        <ul className="flex items-center gap-5">
          {navItems.map((item) => (
            <li key={item.text}>
              <Link className={`${linkAttr.color}`} to={item.path}>
                {item.text}
              </Link>
            </li>
          ))}
          <li>
            <Link
              className="font-black text-white bg-indigo-700 rounded-xl"
              to={paths.login}
              style={{ padding: "0.5rem 1rem" }}
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
