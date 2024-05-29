import JanusSide from "./JanusSide";
import "../css/JanusTeam.css";
import "./Calendar";
import Calendar from "./Calendar";

function JanusTeam() {
  return (
    <div className="wrap" id="wrap">
      <div className="side-box">
        <JanusSide />
      </div>
      <div className="main-box">
        <div className=" bg-white rounded-xl p-4 shadow-xl my-4 ml-6 mr-6">
          <div className="m-auto w-full">
            <div>
                <div className="px-4 py-2 text-left">
                  <h2 className="text-ml font-semibold text-gray-600">previous meeting</h2>
                </div>
            </div>
            <div>
            </div>
          </div>
        </div>
        {/* 캘린더 자리 */}
          <div className="bg-white shadow-xl rounded-xl">
            <Calendar/>
          </div>
        </div>
      </div>
  );
}

export default JanusTeam;
