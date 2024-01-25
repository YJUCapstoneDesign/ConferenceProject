import React from "react";
import HomeText from "./HomeText";
import HomeButton from "./HomeButton";

function HomeContent() {
  return (
    <div className="bg-banner bg-[length:100vm]">
      <div className="relative mt-10 h-screen pt-10 sm:pt-0 mb-10">
        <div className="container mx-auto grid grid-cols-1 md:grid-cols-2 items-center md:gap-20">
          <div className="content">
            <HomeText />
            <div clasNames="flex gap-4 mt-10">
              <HomeButton />
            </div>
          </div>
          <div className="relative sm:mt-0 mt-10 px-6 sm:px-0">
            {/* <HomeImage /> */}
          </div>
        </div>
      </div>
    </div>
  );
}

export default HomeContent;
