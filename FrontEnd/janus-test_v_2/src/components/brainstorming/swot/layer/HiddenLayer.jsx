import React from 'react';
import { useDrag, useDrop } from "react-dnd";

const DraggableCell = ({ row, col, content, moveCell }) => {
  const [, drag] = useDrag({
    type: "CELL",
    item: { row, col, content },
  });

  const [, drop] = useDrop({
    accept: "CELL",
    drop: (item) => moveCell(item.row, item.col, row, col),
  });

  return (
    <td
      ref={(node) => drag(drop(node))}
      className="border border-black w-10 h-10 text-center"
    >
      {content}
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
              content={col}
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