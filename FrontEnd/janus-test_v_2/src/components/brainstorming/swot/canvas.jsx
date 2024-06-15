import React, { Fragment, useState, useEffect, useRef, useCallback } from "react";
import './canvas.css';
import Tile from "./tile/Tile";
import HiddenLayer from "./layer/HiddenLayer";
import { DndProvider } from "react-dnd";
import { HTML5Backend } from "react-dnd-html5-backend";
import AddModal from "./modal/AddModal";
import { useParams } from "react-router-dom";

const SIZE = 4;

const baseURL = process.env.REACT_WEBSOCKET_SERVER

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

  const { teamNumber } = useParams();

  const [onModal, setOnModal] = useState(false);
  const [tiles, setTiles] = useState([]);

  const ws = useRef(null);
  const [socketData, setSocketData] = useState(null);

  useEffect(() => {
    ws.current = new WebSocket(`${baseURL}/swot`);
    console.log("웹소켓 연결됨");

    ws.current.onopen = () => {
      ws.current.send(
        JSON.stringify(
          { id: parseInt(teamNumber), type: "ENTER" }
        )
      );
    };
    
    ws.current.onmessage = (message) => {
      console.log("웹소켓 메시지 받음", message.data);
      setSocketData(JSON.parse(message.data));
    };

    return () => {
      ws.current.close();
    };
  }, []);

  useEffect(() => {
    if (socketData) {
      setTiles(socketData);
    }
  }, [socketData]);

  // 웹소켓 데이터 전송
  const sendWebSocketData = useCallback((data) => {
    const sendData = {
      id: parseInt(teamNumber),
      type: "MSG",
      data: data.tiles,
    }
    if (ws.current && ws.current.readyState === WebSocket.OPEN) {
      ws.current.send(JSON.stringify(sendData));
    } else {
      ws.current.onopen = () => {
        ws.current.send(JSON.stringify(sendData));
      };
    }
  }, []);

  // title 이동
  const moveCell = (fromRow, fromCol, toRow, toCol, updatedArea) => {
    setTiles((prevTiles) => {
      const newTiles = [...prevTiles];
      const temp = newTiles[toRow][toCol];
      newTiles[toRow][toCol] = newTiles[fromRow][fromCol];
      newTiles[fromRow][fromCol] = temp;
      newTiles[toRow][toCol].area = updatedArea;

      sendWebSocketData({ tiles: newTiles });
      return newTiles;
    });
  };

  // title 업데이트
  const updateTile = (row, col, newTitle, newContent) => {
    setTiles((prevTiles) => {
      const newTiles = [...prevTiles];
      newTiles[row][col] = {
        ...newTiles[row][col],
        title: newTitle,
        content: newContent,
      };

      sendWebSocketData({ tiles: newTiles });
      return newTiles;
    });
  };

  // title 삭제
  const deleteTile = (row, col) => {
    setTiles((prevTiles) => {
      const newTiles = [...prevTiles];
      newTiles[row][col] = {
        title: "",
        content: "",
        area: 0,
      };

      sendWebSocketData({ tiles: newTiles });
      return newTiles;
    });
  };

  return (
    <Fragment>
      <div className="canvas flex flex-wrap h-full w-full text-center text-white font-bold cursor-auto relative">
        {TILES.map((tile) => (
          <Tile
            key={tile.id}
            title={tile.title}
            classType={tile.classType}
            description={tile.description}
          />
        ))}
        {tiles &&
        <DndProvider backend={HTML5Backend}>
          <HiddenLayer list={tiles} moveCell={moveCell} updateTile={updateTile} deleteTile={deleteTile} />
        </DndProvider>
      }
      </div>
      <button onClick={() => setOnModal(true)} className="add-button">Add</button>
      {onModal && <AddModal onClose={() => setOnModal(false)} setDataList={setTiles} sendWebSocketData={sendWebSocketData} />}
    </Fragment>
  );
}
