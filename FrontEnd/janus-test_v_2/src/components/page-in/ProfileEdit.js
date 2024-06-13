import React from 'react';


function ProfileEdit() {

  return (
    <div>
    {/* 아래 프로필 Edit */}
    <div className="max-w-lg mx-auto mt-48 bg-white dark:bg-gray-800 rounded-lg shadow-md px-8 py-10 flex flex-col items-center">
      <div className="w-full flex flex-col gap-4">

        <div className="flex items-start flex-col justify-start">
          <label htmlFor="username" className="text-sm text-gray-700 dark:text-gray-200 mr-2">Username:</label>
          <input type="text" id="username" name="username" className="w-full px-3 dark:text-gray-200 dark:bg-gray-900 py-2 rounded-md border border-gray-300 dark:border-gray-700 focus:outline-none focus:ring-1 focus:ring-blue-500" />
        </div>

        <div className="flex items-start flex-col justify-start">
          <label htmlFor="password" className="text-sm text-gray-700 dark:text-gray-200 mr-2">Password:</label>
          <input type="password" id="password" name="password" className="w-full px-3 dark:text-gray-200 dark:bg-gray-900 py-2 rounded-md border border-gray-300 dark:border-gray-700 focus:outline-none focus:ring-1 focus:ring-blue-500" />
        </div>

        <div className="flex items-start flex-col justify-start">
          <label htmlFor="confirmPassword" className="text-sm text-gray-700 dark:text-gray-200 mr-2">Confirm Password:</label>
          <input type="password" id="confirmPassword" name="confirmPassword" className="w-full px-3 dark:text-gray-200 dark:bg-gray-900 py-2 rounded-md border border-gray-300 dark:border-gray-700 focus:outline-none focus:ring-1 focus:ring-blue-500" />
        </div>

        <button type="submit" className="bg-blue-500 hover:bg-blue-600 text-white mt-4 font-medium py-2 px-4 rounded-md shadow-sm">Edit</button>
      </div>
     </div>
    </div>
  );
}

export default ProfileEdit;
