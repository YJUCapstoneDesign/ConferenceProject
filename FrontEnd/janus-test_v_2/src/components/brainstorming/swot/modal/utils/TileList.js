
function getBounds(area) {
  switch (area) {
    case 1:
      return { rowStart: 0, rowEnd: 3, colStart: 0, colEnd: 3 };
    case 2:
      return { rowStart: 0, rowEnd: 3, colStart: 4, colEnd: 7 };
    case 3:
      return { rowStart: 4, rowEnd: 7, colStart: 0, colEnd: 3 };
    case 4:
      return { rowStart: 4, rowEnd: 7, colStart: 4, colEnd: 7 };
    default:
      error("Invalid area");
  }
};

export function updateArea (list, title, content, area) {
  const newDataList = list.map(row => row.slice());
  let updated = false;

  const { rowStart, rowEnd, colStart, colEnd } = getBounds(area);

  for (let i = rowStart; i <= rowEnd; i++) {
    for (let j = colStart; j <= colEnd; j++) {
      if (newDataList[i][j].title === "" && newDataList[i][j].content === "" && newDataList[i][j].area === 0) {
        newDataList[i][j] = { title, content, area };
        updated = true;
        break;
      }
    }
    if (updated) break;
  }

  return newDataList;
};

// 현재 위치한 곳에따라 area를 반환하는 함수
export function getArea(row, col) {
  if (row < 4 && col < 4) {
    return 1;
  } else if (row < 4 && col >= 4) {
    return 2;
  } else if (row >= 4 && col < 4) {
    return 3;
  } else {
    return 4;
  }
}