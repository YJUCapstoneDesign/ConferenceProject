import React, { useEffect, useRef, useState } from 'react';
import { fabric } from 'fabric';

const DrawingCanvas = () => {
  const canvasRef = useRef(null);
  const [canvas, setCanvas] = useState(null);
  const [canvasSize, setCanvasSize] = useState({ width: 800, height: 600 });
  const [drawingColor, setDrawingColor] = useState('#000000');
  const [drawingLineWidth, setDrawingLineWidth] = useState(1);
  const [eraserWidth, setEraserWidth] = useState(1);

  useEffect(() => {
    const canvasInstance = new fabric.Canvas(canvasRef.current, {
      isDrawingMode: true,
    });
    fabric.Object.prototype.transparentCorners = false;
    setCanvas(canvasInstance);

    const resizeCanvas = () => {
      const { clientWidth, clientHeight } = document.documentElement;
      setCanvasSize({ width: clientWidth, height: clientHeight });
    };

    resizeCanvas();
    window.addEventListener('resize', resizeCanvas);

    return () => {
      canvasInstance.dispose();
      window.removeEventListener('resize', resizeCanvas);
    };
  }, []);

  useEffect(() => {
    if (canvas) {
      canvas.isDrawingMode = true;
      canvas.setDimensions(canvasSize);
    }
  }, [canvas, canvasSize]);

  useEffect(() => {
    if (canvas && canvas.freeDrawingBrush) {
      const brush = canvas.freeDrawingBrush;
      brush.color = drawingColor;
      brush.width = drawingLineWidth;
    }
  }, [canvas, drawingColor, drawingLineWidth]);

  const clearCanvas = () => {
    canvas.clear();
  };

  const changeBrush = (brushType) => {
    if (!canvas) return;

    let brush;
    if (brushType === 'Eraser') {
      brush = new fabric.PencilBrush(canvas);
      canvas.isDrawingMode = true;
      brush.color = '#FFFFFF';
      brush.width = eraserWidth;
    } else {
      brush = new fabric[`${brushType}Brush`](canvas);
      brush.color = drawingColor;
      brush.width = brushType === 'Eraser' ? eraserWidth : drawingLineWidth;
    }

    canvas.freeDrawingBrush = brush;
  };

  const handleWidthChange = (value, isEraser) => {
    const width = parseInt(value, 10);
    isEraser ? setEraserWidth(width) : setDrawingLineWidth(width);
    if (canvas && canvas.freeDrawingBrush && canvas.isDrawingMode) {
      canvas.freeDrawingBrush.width = width;
      canvas.renderAll();
    }
  };

  return (
    <div>
      <div id="drawing-mode-options">
        <label htmlFor="drawing-line-width">Line width</label>
        <input
          type="range"
          id="drawing-line-width"
          min="1"
          max="100"
          value={drawingLineWidth}
          onChange={(e) => handleWidthChange(e.target.value, false)}
        />
        <input
          type="color"
          id="drawing-color"
          value={drawingColor}
          onChange={(e) => setDrawingColor(e.target.value)}
        />
        {['Pencil', 'Circle', 'Spray', 'Eraser'].map((brushType) => (
          <button key={brushType} onClick={() => changeBrush(brushType)}>{brushType}</button>
        ))}
        <label htmlFor="eraser-width">Eraser width</label>
        <input
          type="range"
          id="eraser-width"
          min="1"
          max="100"
          value={eraserWidth}
          onChange={(e) => handleWidthChange(e.target.value, true)}
        />
      </div>
      <button onClick={clearCanvas}>Clear</button>
      <canvas id="c" ref={canvasRef} width={canvasSize.width} height={canvasSize.height}></canvas>
    </div>
  );
};

export default DrawingCanvas;