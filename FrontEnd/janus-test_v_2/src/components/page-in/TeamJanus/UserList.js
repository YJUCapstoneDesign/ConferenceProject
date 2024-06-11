import React, { useEffect, useState } from "react";
import api from "../api";

function UserList(props) {
    const [userList, setUserList] = useState([]);

    const teamId = props.teamNumber;

    const CheckedList = async () => {
      try {
        const response = await api.get(`/api/team/user-list/${teamId}`);
        setUserList(response.data);
      } catch (error) {
        console.log("팀원이 없습니다.");
      }
    };

    useEffect(() => {
      CheckedList();
    }, []);

    return (
      <div className="relative flex flex-col min-w-0 break-words bg-white h-custom-height w-custom-size5 mt-4 mb-6 ml-4 shadow-lg rounded 2xl:min-custom-height2 xl:min-h-60 xl:h-custom-height4">
        <div className="rounded-t mb-0 px-4 py-3 border-0">
          <div className="flex flex-wrap items-center">
            <div className="relative w-full px-4 max-w-full flex-grow flex-1">
              <h3 className="font-semibold text-base text-indigo-500">Team List</h3>
            </div>
            <div className="relative w-full px-4 max-w-full flex-grow flex-1 text-right">
              <button className="bg-indigo-500 text-white active:bg-indigo-600 text-xs font-bold uppercase px-3 py-1 rounded outline-none focus:outline-none mr-1 mb-1 ease-linear transition-all duration-150" type="button" onClick={CheckedList}>
                See All
              </button>
            </div>
          </div>
        </div>
  
        <div className="block w-full overflow-x-auto">
          <table className="items-center bg-transparent w-full border-collapse">
            <thead>
              <tr>
                <th className="px-8 bg-blueGray-50 text-blueGray-500 align-middle border border-solid border-blueGray-100 py-3 text-xs uppercase border-l-0 border-r-0 whitespace-nowrap font-semibold text-left">
                  name
                </th>
              </tr>
            </thead>
  
            <tbody>
              {userList.map((user,index) => (
                <tr key={index}>
                  <th className="border-t-0 px-6 align-middle border-l-0 border-r-0 text-xs whitespace-nowrap p-4 text-left text-blueGray-700">
                    <p className="ml-2">
                      {user.name}
                    </p>
                  </th>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </div>
    );
}

export default UserList;
