import JanusSide from "./JanusSide";
import "../css/JanusTeam.css";
import "./Calendar";
import Calendar from "./Calendar";

function JanusTeam() {
  return (
    <div className="wrap bg-slate-50" id="wrap">
      <div className="side-box">
        <JanusSide />
      </div>
      
      <div className="grid grid-cols-1 sm:grid-cols-3 md:grid-cols-3 lg:grid-cols-3 w-full gap-8 m-10 auto-rows-auto">
        
        <div className="col-span-2 backdrop-blur-sm bg-white p-6 rounded-md shadow-sm cursor-pointer border-2 border-gray-50 hover:border-indigo-300 transition-colors duration-500 sm:h-80 md:h-80 lg:h-96">
        <h2 className="text-xl font-bold mb-10">Profile</h2>
          <div className="sm:mt-4 lg:mt-4 xl:mt-4">
            <div className="flex justify-end sm:justify-start lg:justify-end xl:justify-start -space-x-1.5">
            </div>
          </div>
        </div>

        <div className="backdrop-blur-sm bg-white p-6 rounded-md shadow-sm cursor-pointer border-2 border-gray-50 hover:border-indigo-300 transition-colors duration-500 sm:h-80 md:h-80 lg:h-96">
          <div className="sm:mt-4 lg:mt-4 xl:mt-4">
            <div className="flex justify-end sm:justify-start lg:justify-end xl:justify-start -space-x-1.5">
            </div>
          </div>
        </div>
        
        <div className="backdrop-blur-sm bg-white p-6 rounded-md shadow-sm cursor-pointer border-2 border-gray-50 hover:border-indigo-300 transition-colors duration-500">
        <h2 className="text-xl font-bold mb-10">User List</h2>
          <div className="sm:mt-4 lg:mt-4 xl:mt-4">
            <div className="flex justify-end sm:justify-start lg:justify-end xl:justify-start -space-x-1.5">
            </div>
          </div>
        </div>
        
        <div className="backdrop-blur-sm bg-white p-6 rounded-md shadow-sm cursor-pointer border-2 border-gray-50 hover:border-indigo-300 transition-colors duration-500">
        <h2 className="text-xl font-bold mb-10">Previous Meeting</h2>
          <div className="sm:mt-4 lg:mt-4 xl:mt-4">
            <div className="flex justify-end sm:justify-start lg:justify-end xl:justify-start -space-x-1.5">
            </div>
          </div>
        </div>

        <div className="backdrop-blur-sm bg-white p-6 rounded-md shadow-sm cursor-pointer border-2 border-gray-50 hover:border-indigo-300 transition-colors duration-500">
        <h2 className="text-xl font-bold mb-10">Calendar</h2>
          <Calendar/>
          <div className="sm:mt-4 lg:mt-4 xl:mt-4">
            <div className="flex justify-end sm:justify-start lg:justify-end xl:justify-start -space-x-1.5">
            </div>
          </div>
        </div>
        
      </div>
    </div>
  );
}

export default JanusTeam;
