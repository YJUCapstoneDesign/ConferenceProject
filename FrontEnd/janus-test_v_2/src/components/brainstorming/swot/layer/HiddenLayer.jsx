import React, { Fragment, useState } from 'react';
import { useDrag, useDrop } from "react-dnd";
import { getArea } from '../modal/utils/TileList';
import DetailModal from '../modal/DetailModal';

const DraggableCell = ({ row, col, title, content, area, onClick, moveCell }) => {
  const canDrag = title && content && area !== 0;

  const [, drag] = useDrag({
    type: "CELL",
    item: { row, col, title, content, area },
    canDrag: () => canDrag,
  });

  const [, drop] = useDrop({
    accept: "CELL",
    drop: (item) => moveCell(item.row, item.col, row, col, getArea(row, col)),
  });


  return (
    <td
      ref={(node) => drag(drop(node))}
      className="text-center borde w-10 h-10"
      onClick={() => {
        if (canDrag) {
          onClick(row, col);
        }
      }}
    >
      {title}
    </td>
  );
};

const HiddenLayer = ({ list, moveCell, updateTile, deleteTile }) => {
  const [onModal, setOnModal] = useState(false);
  const [selectedCell, setSelectedCell] = useState({ row: null, col: null });

  const showModal = (row, col) => {
    setSelectedCell({ row, col });
    setOnModal(true);
  };

  return (
    <Fragment>
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
                  onClick={(row, col) => showModal(row, col)}
                  moveCell={moveCell}
                />
              ))}
            </tr>
          ))}
        </tbody>
      </table>
      {onModal && (
        <DetailModal
          onClose={() => setOnModal(false)}
          title={list[selectedCell.row][selectedCell.col].title}
          content={list[selectedCell.row][selectedCell.col].content}
          area={list[selectedCell.row][selectedCell.col].area}
          updateTile={(newTitle, newContent) => {
            updateTile(selectedCell.row, selectedCell.col, newTitle, newContent);
          }}
          deleteTile={() => {
            deleteTile(selectedCell.row, selectedCell.col);
          }}
        />
      )}
    </Fragment>
  );
};

export default HiddenLayer;
