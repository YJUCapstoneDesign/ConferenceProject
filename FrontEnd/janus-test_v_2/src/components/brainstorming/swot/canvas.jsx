import React, {useState} from "react";
import './canvas.css';
import Tile from "./tile/Tile";
import HiddenLayer from "./layer/HiddenLayer";
import { DndProvider } from "react-dnd";
import { HTML5Backend } from "react-dnd-html5-backend";

const SIZE = 4;

const TILES = [
    {
        id: 1,
        title: "S",
        classType: "str",
        description: "STRENGTHS",
    },

    {
        id: 2,
        title: "W",
        classType: "weak",
        description: "WEAKNESSES",
    },

    {
        id: 3,
        title: "O",
        classType: "oppor",
        description: "OPPORTUNITIES",
    },

    {
        id: 4,
        title: "T",
        classType: "ths",
        description: "THREATS",
    },
];

export default function Canvas() {
    // 16 * 16 2차 배열 기본값 번호 순으로 초기화
    // Size = 4

    const [tiles, setTiles] = useState(Array.from({length: SIZE * 2}, () => Array.from({length: SIZE * 2}, () => "item" + Math.floor(Math.random() * 100))));
    
    const moveCell = (fromRow, fromCol, toRow, toCol) => {
        setTiles((prevTiles) => {
            const newTiles = [...prevTiles];
            const temp = newTiles[toRow][toCol];
            newTiles[toRow][toCol] = newTiles[fromRow][fromCol];
            newTiles[fromRow][fromCol] = temp;
            return newTiles;
        });
    };
    
    return (
        <div className="canvas flex flex-wrap h-full w-full text-center text-white font-bold cursor-auto">

            {TILES.map(tile => 
                <Tile
                    key={tile.id}
                    title={tile.title}
                    classType={tile.classType}
                    description={tile.description}
                />)}

            <DndProvider backend={HTML5Backend}>
                <HiddenLayer list={tiles} moveCell={moveCell}/>
            </DndProvider>
        </div>
    )
}
