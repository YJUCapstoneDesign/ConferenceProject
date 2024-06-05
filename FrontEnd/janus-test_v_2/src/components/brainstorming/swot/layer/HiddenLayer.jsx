import React, {Fragment, useState} from 'react';
import { useDrag, useDrop } from "react-dnd";
import { getArea } from '../modal/utils/TileList';
import DetailModal from '../modal/DetailModal';

const DraggableCell = ({ row, col, title, content, area,onClick, moveCell }) => {
  const canDrag = title && content && area !== 0;
  
  const [, drag] = useDrag({
    type: "CELL",
    item: { row, col, title, content, area },
    canDrag: () => canDrag,
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
      className="text-center borde w-10 h-10"
      onClick={() => {
        if (canDrag) {
        onClick(title, content, area)
      }
    }}
    >
      {title}
    </td>
  );
};

const HiddenLayer = ({list, moveCell}) => {
  const [onModal, setOnModal] = useState(false);
  const [data, setData] = useState({title: "", content: "", area: 0});

  const showModal = (title, content, area) => {
    console.log(title, content, area);
    setData({title, content, area});
    setOnModal(true);
  }

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
              onClick={(title, content, area) => showModal(title, content,area)}
              moveCell={moveCell}
            />
          ))}
        </tr>
      ))}
    </tbody>
  </table>
  {onModal && <DetailModal onClose={() => setOnModal(false)} title={data.title} content={data.content} area={data.area} />}
  </Fragment>
  );

}

export default HiddenLayer;