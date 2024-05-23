import React, { useState, useRef, useCallback, useEffect } from 'react';
import ReactFlow, {
  MiniMap,
  Controls,
  Background,
  useNodesState,
  useEdgesState,
  addEdge,
} from 'reactflow';

import 'reactflow/dist/style.css';

let initialNodes = [
  { id: '1', position: { x: 300, y: 300 }, data: { label: '1' } },
  { id: '2', position: { x: 200, y: 400 }, data: { label: '2' } },
];

let initialEdges = [{ id: 'e1-2', source: '1', target: '2' }];

export default function App() {
  const [nodes, setNodes, onNodesChange] = useNodesState(initialNodes);
  const [edges, setEdges, onEdgesChange] = useEdgesState(initialEdges);
  const [socketData, setSocketData] = useState();
  const [selectedNodes, setSelectedNodes] = useState([]);
  const [selectedEdges, setSelectedEdges] = useState([]);

  const ws = useRef(null); // 웹소켓 연결을 위한 ref

  useEffect(() => {
    ws.current = new WebSocket("ws://localhost:8080/app");

    ws.current.onmessage = (message) => { // 서버에서 메시지가 오면 실행
      setSocketData(message.data);
    };

    return () => {
      ws.current.close();
    };
  }, []);

  useEffect(() => {
    if (socketData !== undefined) {
      const parsedSocketData = JSON.parse(socketData);
      
      setNodes(parsedSocketData.node);
      setEdges(parsedSocketData.edge);
    }
  }, [socketData]);

  const sendWebSocketData = useCallback((data) => {
    if (ws.current && ws.current.readyState === WebSocket.OPEN) {
      ws.current.send(JSON.stringify(data));
    } else {
      ws.current.onopen = () => {
        ws.current.send(JSON.stringify(data));
      };
    }
  }, []);

  const addNode = useCallback(() => {
    const newNode = {
      id: (nodes.length + 1).toString(),
      position: { x: Math.random() * window.innerWidth, y: Math.random() * window.innerHeight },
      data: { label: "New Node" },
    };
    const newNodes = [...nodes, newNode];
    initialNodes = newNodes;

    const newData = {
      node: newNodes,
      edge: initialEdges
    };

    sendWebSocketData(newData);
  }, [nodes, sendWebSocketData]);

  const onSelectionDragStop = (e, nodes) => {
    nodes.forEach((node) => {
      const index = initialNodes.findIndex(item => item.id === node.id);
      if (index !== -1) {
        initialNodes[index].position = node.position;
      }
    });

    const newData = {
      node: initialNodes,
      edge: initialEdges
    };

    sendWebSocketData(newData);
  };

  const onNodeDragStop = (e, dragNode, nodes) => {
    nodes.forEach((node) => {
      const index = initialNodes.findIndex(item => item.id === node.id);
      if (index !== -1) {
        initialNodes[index].position = node.position;
      }
    });

    const newData = {
      node: initialNodes,
      edge: initialEdges
    };

    sendWebSocketData(newData);
  };

  const onNodesDelete = () => {
    const newNodes = nodes.filter(node => node.id !== selectedNodes.id);
    initialNodes = newNodes;

    const newEdges = edges.filter(edge => edge.source !== selectedNodes.id && edge.target !== selectedNodes.id);
    initialEdges = newEdges;

    const newData = {
      node: initialNodes,
      edge: initialEdges
    };

    sendWebSocketData(newData);
  };

  const onEdgesConnect = (params) => {
    const newEdge = {
      id: `e${params.source}-${params.target}`,
      source: params.source,
      target: params.target,
    };
    const newEdges = [...edges, newEdge];
    initialEdges = newEdges;

    const newData = {
      node: initialNodes,
      edge: newEdges
    };

    sendWebSocketData(newData);
  };

  const onEdgesDelete = () => {
    const newEdges = edges.filter(edge => edge.id !== selectedEdges.id);
    initialEdges = newEdges;

    const newData = {
      node: initialNodes,
      edge: newEdges
    };

    sendWebSocketData(newData);
  };

  const onNodeDoubleClick = (e) => {
    const newLabel = prompt("새로운 라벨을 입력하세요.");
    const newNodes = nodes.map((item) => {
      if (item.id === selectedNodes.id) {
        item.data.label = newLabel;
      }
      return item;
    });
    initialNodes = newNodes;

    const newData = {
      node: newNodes,
      edge: initialEdges
    };

    sendWebSocketData(newData);
  };

  const onSelectNodes = (e, nodes) => {
    setSelectedNodes(nodes);
  };

  const onSelectEdges = (e, edges) => {
    setSelectedEdges(edges);
  };

  // const handleSaveClick = async () => {
  //   const mindMapData = {
  //       nodes: initialNodes,
  //       edges: initialEdges,
  //   };
  //   try {
  //       const response = await fetch("api/mind-map/save", {
  //           method: "POST",
  //           headers: {
  //               "Content-Type": "application/json",
  //           },
  //           body: JSON.stringify(mindMapData),
  //       });
  //       if (response.ok) {
  //           console.log("Mind map data sent successfully.");
  //       } else {
  //           console.error(
  //               "Failed to send mind map data:",
  //               response.statusText
  //           );
  //       }
  //   } catch (error) {
  //       console.error("Error sending mind map data:", error);
  //   }
  // }


  return (
    <div style={{ width: '100vw', height: '100vh' }}>
      <button onClick={addNode}>추가 버튼</button>
      {/* <button onClick={handleSaveClick}>저장 버튼</button> */}
      {/* <button onClick={loadData}>불러오기 버튼</button> */}
      <ReactFlow
        nodes={nodes}
        edges={edges}
        onNodesChange={onNodesChange}
        onEdgesChange={onEdgesChange}
        onNodeDragStop={onNodeDragStop}
        onNodesDelete={onNodesDelete}
        onNodeClick={onSelectNodes}
        onNodeDoubleClick={onNodeDoubleClick}
        onConnect={onEdgesConnect}
        onEdgeClick={onSelectEdges}
        onEdgesDelete={onEdgesDelete}
        onSelectionChange={() => {}}
        onSelectionDragStop={onSelectionDragStop}
      >
        <Controls />
        <MiniMap />
        <Background variant="dots" gap={12} size={1} />
      </ReactFlow>
    </div>
  );
}
