import React from "react";
import './canvas.css';

export default function Canvas() {
    return (
        <div className="flex flex-wrap h-full w-full text-center text-white font-bold cursor-auto">
            <div className="str w-3/6 bg-[#839EFF] flex items-center justify-center relative" onClick={() => {console.log("S")}}>
                <div className="drop-shadow-md px-12">
                    <div className="text-12xl font-bold">
                        <p>S</p>
                    </div>
                    <div className="text-2xl description absolute hidden">
                        <p>STRENGTHS</p>
                    </div>
                </div>
            </div>
            <div className="weak w-3/6 bg-[#F99156] flex items-center justify-center relative" onClick={() => {console.log("W")}}>
                <div className="drop-shadow-md px-12">
                    <div className="text-12xl font-bold">
                        <p>W</p>
                    </div>
                    <div className="text-2xl description absolute hidden">
                        <p>WEAKNESSES</p>
                    </div>
                </div>
            </div>
            <div className="oppor w-3/6 bg-[#8CF35B] flex items-center justify-center relative" onClick={() => {console.log("O")}}>
                <div className="drop-shadow-md px-12">
                    <div className="text-12xl font-bold">
                        <p>O</p>
                    </div>
                    <div className="text-2xl description absolute hidden">
                        <p>OPPORTUNITIES</p>
                    </div>
                </div>
            </div>
            <div className="ths w-3/6 bg-[#E968ED] flex items-center justify-center relative" onClick={() => {console.log("T")}}>
                <div className="drop-shadow-md px-12">
                    <div className="text-12xl font-bold">
                        <p>T</p>
                    </div>
                    <div className="text-2xl description absolute hidden">
                        <p>THREATS</p>
                    </div>
                </div>
            </div>
        </div>
    )
}
