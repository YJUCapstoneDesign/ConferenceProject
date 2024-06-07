import React, { Fragment, useState } from "react";
import './canvas.css';
import Tile from "./tile/Tile";
import HiddenLayer from "./layer/HiddenLayer";
import { DndProvider } from "react-dnd";
import { HTML5Backend } from "react-dnd-html5-backend";
import AddModal from "./modal/AddModal";

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
  const [onModal, setOnModal] = useState(false);
  const [tiles, setTiles] = useState(Array.from({ length: SIZE * 2 }, 
    () => Array.from({ length: SIZE * 2 }, () => {
      return {
        title: "",
        content: "",
        area: 0,
      }; // empty tile
  })));

  const moveCell = (fromRow, fromCol, toRow, toCol, updatedArea) => {
    setTiles((prevTiles) => {
      const newTiles = [...prevTiles];
      const temp = newTiles[toRow][toCol];
      newTiles[toRow][toCol] = newTiles[fromRow][fromCol];
      newTiles[fromRow][fromCol] = temp;

			newTiles[toRow][toCol].area = updatedArea;
      return newTiles;
    });
  };

	// tile 업데이트
  const updateTile = (row, col, newTitle, newContent) => {
    setTiles((prevTiles) => {
      const newTiles = [...prevTiles];
      newTiles[row][col] = {
        ...newTiles[row][col],
        title: newTitle,
        content: newContent,
      };
      return newTiles;
    });
  };

	// tile 삭제
  const deleteTile = (row, col) => {
    setTiles((prevTiles) => {
      const newTiles = [...prevTiles];
      newTiles[row][col] = {
        title: "",
        content: "",
        area: 0,
      };
      return newTiles;
    });
  };

  return (
    <Fragment>
      <div className="canvas flex flex-wrap h-full w-full text-center text-white font-bold cursor-auto">
        {TILES.map((tile) => (
          <Tile
            key={tile.id}
            title={tile.title}
            classType={tile.classType}
            description={tile.description}
          />
        ))}
        <DndProvider backend={HTML5Backend}>
          <HiddenLayer list={tiles} moveCell={moveCell} updateTile={updateTile} deleteTile={deleteTile} />
        </DndProvider>
      </div>
      <button onClick={() => setOnModal(true)} className="border-gray-400 border-2 p-4">Add</button>
      {onModal && <AddModal onClose={() => setOnModal(false)} setDataList={setTiles} />}
    </Fragment>
  );
}
