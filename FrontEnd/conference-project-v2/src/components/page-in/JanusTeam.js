import JanusSide from "./JanusSide";
import "../css/JanusTeam.css";
import "./Calendar";
import Calendar from "./Calendar";
import UserList from "./UserList";
import BeforeLog from "./BeforeLog";
import Tutorial from "./Tutorial";
import Undefined from "./Undefined";

function JanusTeam() {
  return (
    <div className="wrap bg-slate-50" id="wrap">
      <div className="side-box">
        <JanusSide />
      </div>
      <section class="text-gray-600 body-font w-full">
          <div class="container px-5 py-5 mx-auto max-w-full">
            <div class="flex flex-wrap w-full mb-6 p-4 -ml-3">
              <div class="w-full mb-6 lg:mb-0">
                <h1 class="sm:text-4xl text-5xl font-bold title-font mb-2 text-gray-900">Team Page</h1>
                <div class="h-1 w-44 bg-indigo-500 rounded"></div>
              </div>
            </div>
            <div class="flex flex-wrap -m-4">
              <div class="xl:w-1/4 md:w-1/2 p-4 mb-7">
                <div class="bg-white p-6 rounded-lg">
                  <h2 class="tracking-widest text-indigo-500 text-xs font-bold title-font mb-1">CALENDAR</h2>
                  <h2 class="text-lg text-gray-900 font-medium title-font mb-4">Manage your time here!</h2>
                  <Calendar/>
                </div>
              </div>
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
