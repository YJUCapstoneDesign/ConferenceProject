import React, { useState, useEffect } from "react";
import { getTeamFilesText } from "./FileUploadDownload";
import { useParams } from 'react-router-dom';

function BeforeLog() {
  const [logTexts, setLogTexts] = useState([]); 
  const { teamNumber } = useParams();
  const [topic, setTopic] = useState();

    
    const fetchLogText = async () => {
      try {
        const texts = await getTeamFilesText(teamNumber,topic);
        setLogTexts(texts);
      } catch (error) {
        console.error("로그 불러오기 실패:", error);
      }
    };
 

  return (
    // <div class="w-10 xl:w-7/12 mb-12 xl:mb-0 ml-4">
    <div className="justify-start relative flex flex-col min-w-0 break-words bg-white h-custom-height w-custom-size mt-4 mb-6 ml-4  shadow-lg rounded 2xl:min-custom-height2 xl:min-h-60 xl:h-80">
      <div className="rounded-t mb-0 px-4 py-3 border-0">
        <div className="flex flex-wrap items-center">
          <div className="relative w-full px-4 max-w-full flex-grow flex-1">
            <h3 className="font-semibold text-base text-indigo-500">Previous Log</h3>
          </div>
        </div>
      </div>

      <div className="block w-full overflow-y-auto max-h-60"> 
        <table className="items-center bg-transparent w-full border-collapse ">
          <thead>
            <tr>
            <th className="pl-8 bg-blueGray-50 text-blueGray-500 align-middle border border-solid border-blueGray-100 py-2 text-xs uppercase border-l-0 border-r-0 whitespace-nowrap font-semibold text-left">
              <div className="flex items-center justify-between">
                <input 
                  className="block w-full h-9 p-2 text-gray-900 border border-gray-300 rounded-lg bg-gray-50 text-xs focus:ring-blue-500 focus:border-blue-500" 
                  onChange={(e) => setTopic(e.target.value)}
                />
              <div className="relative w-full px-4 max-w-full flex-grow flex-1 text-right">
                <button onClick={fetchLogText} className="bg-indigo-500 text-white active:bg-indigo-600 text-xs font-bold uppercase px-3 py-1 rounded outline-none focus:outline-none mb-1 ease-linear transition-all duration-150" type="button">
                   See All
                </button>
              </div>
            </div>
            </th>

            </tr>
          </thead>

          <tbody>
            {logTexts.map((text, index) => (
              <tr key={index}> 
                <th className="border-t-0 px-6 align-middle border-l-0 border-r-0 text-xs whitespace-nowrap p-4 text-left text-blueGray-700 ">
                  <span>{text}</span>
                </th>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
    // </div>
  );
}

export default BeforeLog;