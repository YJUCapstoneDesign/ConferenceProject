import React from "react";
import JanusSide from "./TeamJanus/JanusSide";
import "../css/JanusTeam.css"
import UserList from "./TeamJanus/UserList";
import BeforeLog from "./TeamJanus/BeforeLog";
import Tutorial from "./TeamJanus/Tutorial";
import Undefined from "./TeamJanus/Undefined";
import Calendarbox from "./TeamJanus/Calendarbox";

function JanusTeam() {
  return (
    <div className="wrap bg-slate-50" id="wrap">
      <div className="side-box">
        <JanusSide />
      </div>
      <section className="text-gray-600 body-font w-full">
          <div className="container px-5 py-5 mx-auto max-w-full">
            <div className="flex flex-wrap w-full mb-6 p-4 -ml-3">
              <div className="w-full mb-6 lg:mb-0">
                <h1 className="sm:text-4xl text-5xl font-bold title-font mb-2 text-gray-900">Team Page</h1>
                <div className="h-1 w-44 bg-indigo-500 rounded"></div>
              </div>
            </div>
            <div className="flex flex-wrap -m-4">
                <Calendarbox/>
                <BeforeLog/>
                <Tutorial/>
                <UserList/>
                <Undefined/>
            </div>
          </div>
        </section>
    </div>
  );
}

export default JanusTeam;
