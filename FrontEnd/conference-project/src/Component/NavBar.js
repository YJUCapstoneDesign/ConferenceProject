import { Link } from "react-router-dom";
import React from "react";

function Nav() {
  return (
    <div class="shadow bg-white">
      <div class="h-16 mx-auto px-5 flex items-center justify-between">
        <a class="text-2xl hover:text-cyan-500 transition-colors cursor-pointer">
          Logo
        </a>
        <ul class="flex items-center gap-5">
          <li>
            <Link class="hover:text-cyan-500 transition-colors" to={"/"}>
              HOME
            </Link>
          </li>
          <li>
            <Link class="hover:text-cyan-500 transition-colors" to={"/"}>
              ABOUT
            </Link>
          </li>
          <li>
            <Link class="hover:text-cyan-500 transition-colors" to={"/"}>
              SERVICES
            </Link>
          </li>
          <li>
            <Link class="hover:text-cyan-500 transition-colors" to={"/"}>
              NOTICE
            </Link>
          </li>
          <li>
            <Link class="hover:text-cyan-500 transition-colors" to={"/"}>
              LOGIN
            </Link>
          </li>
        </ul>
      </div>
    </div>
  );
}

export default Nav;
