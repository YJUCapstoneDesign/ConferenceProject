import React from "react";
import MyCalendar from "./Calendar";


function Calendarbox() {
  return (
        <div className="relative flex flex-col min-w-0 break-words bg-white h-custom-height w-custom-size1 mt-4 mb-6 ml-4  shadow-lg rounded 2xl:min-custom-height2 xl:min-h-60 xl:h-80">
          <div className="rounded-t mb-0 px-4 py-3 border-0">
            <div className="flex flex-wrap items-center">
              <div className="relative w-full px-4 max-w-full flex-grow flex-1">
                <h3 className="font-semibold text-base text-indigo-500">Calendar</h3>
              </div>
            </div>
          </div>
      
          <div className="block w-full overflow-x-auto">
            <table className="items-center bg-transparent w-full border-collapse ">
              <tbody>
                <tr>
                  <th className="border-t-0 align-middle border-l-0 border-r-0 text-xs whitespace-nowrap text-left text-blueGray-700 ">
                    <MyCalendar/>
                  </th>
                </tr>
              </tbody>
      
            </table>
          </div>
        </div>
  );
}

export default Calendarbox;
