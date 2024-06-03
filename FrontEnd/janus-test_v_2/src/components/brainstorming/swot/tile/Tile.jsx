import React from "react";
import {colors} from "./colors";

const Tile = ({title, classType, description, onClick}) => {

  return (
    <div className={classType +" w-3/6 flex items-center justify-center relative "+ colors[title] } onClick={() => {console.log("S")}}>
    
    <div className="drop-shadow-md px-12">
        <div className=" cursor-pointer font-bold 2xl:text-12xl xl:text-11xl lg:text-10xl md:text-9xl sm:text-8xl">
            <p>{title}</p>
        </div>
        <div className="2xl:text-2xl xl:text-xl lg:text-lg md:text-base sm:text-sm description absolute hidden">
            <p>{description}</p>
        </div>
    </div>
</div>
  );
}

export default Tile;