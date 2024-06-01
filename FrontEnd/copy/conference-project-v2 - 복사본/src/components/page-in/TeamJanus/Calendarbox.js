function Calendarbox() {
  return (
        <div class="relative flex flex-col min-w-0 break-words bg-white h-custom-height w-custom-size1 mt-4 mb-6 ml-4  shadow-lg rounded 2xl:min-custom-height2 xl:min-h-60 xl:h-80">
          <div class="rounded-t mb-0 px-4 py-3 border-0">
            <div class="flex flex-wrap items-center">
              <div class="relative w-full px-4 max-w-full flex-grow flex-1">
                <h3 class="font-semibold text-base text-indigo-500">Calendar</h3>
              </div>
            </div>
          </div>
      
          <div class="block w-full overflow-x-auto">
            <table class="items-center bg-transparent w-full border-collapse ">
              <thead>
                <tr>
                  <th class="px-8 bg-blueGray-50 text-blueGray-500 align-middle border border-solid border-blueGray-100 py-3 text-xs uppercase border-l-0 border-r-0 whitespace-nowrap font-semibold text-left">
                                Content
                  </th>
                </tr>
              </thead>
      
              <tbody>
                <tr>
                  <th class="border-t-0 px-6 align-middle border-l-0 border-r-0 text-xs whitespace-nowrap p-4 text-left text-blueGray-700 ">
                    
                  </th>
                </tr>
                <tr>
                  <th class="border-t-0 px-6 align-middle border-l-0 border-r-0 text-xs whitespace-nowrap p-4 text-left text-blueGray-700">
                   
                  </th>
                </tr>
                <tr>
                  <th class="border-t-0 px-6 align-middle border-l-0 border-r-0 text-xs whitespace-nowrap p-4 text-left text-blueGray-700">
                    
                  </th>
                </tr>
                <tr>
                  <th class="border-t-0 px-6 align-middle border-l-0 border-r-0 text-xs whitespace-nowrap p-4 text-left text-blueGray-700">
                    
                  </th>
                </tr>
                <tr>
                  <th class="border-t-0 px-6 align-middle border-l-0 border-r-0 text-xs whitespace-nowrap p-4 text-left text-blueGray-700">
                    
                  </th>
                </tr>
              </tbody>
      
            </table>
          </div>
        </div>
  );
}

export default Calendarbox;
