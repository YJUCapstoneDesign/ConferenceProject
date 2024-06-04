import React, {useState} from 'react';
import { useDrag, useDrop } from "react-dnd";
import { getArea } from '../modal/utils/TileList';

const DraggableCell = ({ row, col, title, content, area, moveCell }) => {
  if (title === "" && content === "" && area === 0) {
    return <td className="text-center w-10 h-10"></td>;
  }
  const [, drag] = useDrag({
    type: "CELL",
    item: { row, col, title, content, area },
  });

  const [, drop] = useDrop({
    accept: "CELL",
    drop: (item) => moveCell(item.row, item.col, row, col),
  });

  // 움직인 경우 현재 위치에 따라 area를 반환한다.
  area = getArea(row, col);

  return (
    <td
      ref={(node) => drag(drop(node))}
      className="text-center w-10 h-10"
    >
      {title}
      <div id="tile-content"className='hidden'>{content}</div>
      <div id="tile-area" className='hidden'>{area}</div>
    </td>
  );
};

const HiddenLayer = ({list, moveCell}) => {
  // 16 * 16 grid border를 준다.
  return (
    <table className="absolute w-full h-full left-0 top-0 table-fixed">
    <tbody>
      {list.map((row, rowIndex) => (
        <tr key={rowIndex}>
          {row.map((col, colIndex) => (
            <DraggableCell
              key={colIndex}
              row={rowIndex}
              col={colIndex}
              title={col.title}
              content={col.content}
              area={col.area}
              moveCell={moveCell}
            />
          ))}
        </tr>
      ))}
    </tbody>
  </table>
  );

}

export default HiddenLayer;