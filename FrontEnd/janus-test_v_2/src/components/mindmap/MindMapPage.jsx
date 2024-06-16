import React, { useState, useRef, useCallback, useEffect } from 'react';
import ReactFlow, {
  MiniMap,
  Controls,
  Background,
  useNodesState,
  useEdgesState,
} from 'reactflow';

import 'reactflow/dist/style.css';
import { uploadToS3 } from './FileUploadDownload';
import { listUploadedFiles, downloadFileFromS3 } from './FileUploadDownload'; 
import { useParams } from 'react-router-dom';

const baseURL = process.env.REACT_WEBSOCKET_SERVER;

const initialNodes = [];
const initialEdges = [];

export default function MindMapPage() {

  const { teamNumber } = useParams();

  const [nodes, setNodes, onNodesChange] = useNodesState(initialNodes);
  const [edges, setEdges, onEdgesChange] = useEdgesState(initialEdges);
  const [fileList, setFileList] = useState([]);
  const [selectedNodes, setSelectedNodes] = useState([]);
  const [selectedEdges, setSelectedEdges] = useState([]);
  const [lastAddedNode, setLastAddedNode] = useState(null);

  const ws = useRef(null);
  const initBgColor = '#1A192B';

  const styles = {
    height: '100%',
    backgroundColor: initBgColor,
  };

  const handleSaveClick = async () => {
    const mindMapData = {
      nodes,
      edges,
    };

    const mindMapDataJson = JSON.stringify(mindMapData);

    const uploadResult = await uploadToS3(mindMapDataJson, teamNumber);

    setFileList([]);

    if (uploadResult.success) {
      alert('파일 저장 성공!');
    } else {
      alert('파일 저장 실패!');
    }
  };

  const handleLoadData = async () => {
    try {
      const files = await listUploadedFiles(teamNumber);
      setFileList(files);
    } catch (error) {
      console.error('데이터 로드 실패:', error);
    }
  };

  const handleApplyFile = async (fileName) => {
    try {
      const fileContent = await downloadFileFromS3(fileName);
      const fileContentString = new TextDecoder().decode(fileContent.value);
      const { nodes: newNodes, edges: newEdges } = JSON.parse(fileContentString);

      setNodes(newNodes);
      setEdges(newEdges);

      const newData = {
        node: newNodes,
        edge: newEdges
      };

      sendWebSocketData(newData);
      setFileList([]);
    } catch (error) {
      console.error('파일 적용 실패:', error);
    }
  };

  useEffect(() => {
    ws.current = new WebSocket(`${baseURL}/app`);
    console.log("웹소켓 연결됨");

    ws.current.onopen = () => {
      ws.current.send(JSON.stringify({ id: parseInt(teamNumber), type: "ENTER" }));
    };

    ws.current.onmessage = (message) => {
      const data = JSON.parse(message.data);
      console.log("message: ", data);
      setNodes(data.node);
      setEdges(data.edge);
    };

    return () => {
      ws.current.close();
    };
  }, [teamNumber]);

  const sendWebSocketData = useCallback((data) => {
    console.log("sendWebSocketData: ", data);
    const sendData = {
      id: parseInt(teamNumber),
      type: "MSG",
      data,
    };
    if (ws.current && ws.current.readyState === WebSocket.OPEN) {
      ws.current.send(JSON.stringify(sendData));
    } else {
      ws.current.onopen = () => {
        ws.current.send(JSON.stringify(sendData));
      };
    }
  }, [teamNumber]);

  const addNode = useCallback(() => {
    const baseNode = selectedNodes.length > 0 ? selectedNodes[0] : lastAddedNode;
    const position = baseNode
      ? {
          x: baseNode.position.x + Math.random() * 200 - 50,
          y: baseNode.position.y + Math.random() * 200 - 50,
        }
      : { x: Math.random() * window.innerWidth / 2, y: Math.random() * window.innerHeight / 2 };

    const newNode = {
      id: (nodes.length + 1).toString(),
      position,
      data: { label: "New Node" },
    };
    const newNodes = [...nodes, newNode];
    setNodes(newNodes);

    // Automatically add an edge if a node was selected
    let newEdges = [...edges];
    if (selectedNodes.length === 1) {
      const newEdge = {
        id: `e${selectedNodes[0].id}-${newNode.id}`,
        source: selectedNodes[0].id,
        target: newNode.id,
      };
      newEdges = [...edges, newEdge];
      setEdges(newEdges);
    }

    const newData = {
      node: newNodes,
      edge: newEdges
    };

    sendWebSocketData(newData);
    setLastAddedNode(newNode);
  }, [nodes, edges, selectedNodes, lastAddedNode, sendWebSocketData]);

  const onNodeDragStop = useCallback((e, dragNode) => {
    const updatedNodes = nodes.map(node => node.id === dragNode.id ? dragNode : node);
    setNodes(updatedNodes);

    const newData = {
      node: updatedNodes,
      edge: edges
    };

    sendWebSocketData(newData);
  }, [nodes, edges, sendWebSocketData]);

  const onNodesDelete = useCallback(() => {
    const updatedNodes = nodes.filter(node => !selectedNodes.some(selected => selected.id === node.id));
    const updatedEdges = edges.filter(edge => !selectedNodes.some(selected => selected.id === edge.source || selected.id === edge.target));
    setNodes(updatedNodes);
    setEdges(updatedEdges);

    const newData = {
      node: updatedNodes,
      edge: updatedEdges
    };

    sendWebSocketData(newData);
  }, [nodes, edges, selectedNodes, sendWebSocketData]);

  const onEdgesConnect = useCallback((params) => {
    const newEdge = {
      id: `e${params.source}-${params.target}`,
      source: params.source,
      target: params.target,
    };
    const updatedEdges = [...edges, newEdge];
    setEdges(updatedEdges);

    const newData = {
      node: nodes,
      edge: updatedEdges
    };

    sendWebSocketData(newData);
  }, [nodes, edges, sendWebSocketData]);

  const onEdgesDelete = useCallback(() => {
    const updatedEdges = edges.filter(edge => !selectedEdges.some(selected => selected.id === edge.id));
    setEdges(updatedEdges);

    const newData = {
      node: nodes,
      edge: updatedEdges
    };

    sendWebSocketData(newData);
  }, [nodes, edges, selectedEdges, sendWebSocketData]);

  const onNodeDoubleClick = useCallback((e, node) => {
    const newLabel = prompt("새로운 라벨을 입력하세요.", node.data.label);
    const updatedNodes = nodes.map(item => item.id === node.id ? { ...item, data: { ...item.data, label: newLabel } } : item);
    setNodes(updatedNodes);

    const newData = {
      node: updatedNodes,
      edge: edges
    };

    sendWebSocketData(newData);
  }, [nodes, edges, sendWebSocketData]);

  return (
    <div style={{ width: '100vw', height: '100vh', backgroundColor:'#1A192B'}}>
      <div className="inline-flex shadow-sm rounded-sm mt-1 ml-1" role="group">
        <button onClick={addNode} className='px-4 py-2 text-sm font-semibold text-gray-900 rounded-s-lg bg-transparent border hover:bg-gray-900 hover:text-white focus:z-10 focus:ring-2 focus:ring-gray-500 focus:bg-gray-900 focus:text-white bg-white transition-colors duration-500 ease-in-out'>
          Add
        </button>
        <button onClick={handleSaveClick} className='px-4 py-2 text-sm font-semibold text-gray-900 bg-transparent border-t border-b hover:bg-gray-900 hover:text-white focus:z-10 focus:ring-2 focus:ring-gray-500 focus:bg-gray-900 focus:text-white bg-white transition-colors duration-500 ease-in-out'>
          Save
        </button>
        <button onClick={handleLoadData} className='px-4 py-2 text-sm font-semibold text-gray-900 rounded-e-lg bg-transparent border hover:bg-gray-900 hover:text-white focus:z-10 focus:ring-2 focus:ring-gray-500 focus:bg-gray-900 focus:text-white b  bg-white transition-colors duration-500 ease-in-out'>
          Load
        </button>
      </div>
      {fileList.length > 0 && (
        <div>
          <ul className='border border-gray-200 rounded-2xl shadow-md'>
            {fileList.map((file, index) => (
              <li key={index} className='px-4 py-2 bg-white hover:bg-sky-100 hover:text-sky-900 border-b last:border-none border-gray-200 transition-all duration-300 ease-in-out text-gray-500'>
                {file}
                <button onClick={() => handleApplyFile(file)}>적용하기</button>
              </li>
            ))}
          </ul>
        </div>
      )}
      <div style={{ height: 'calc(100% - 50px)' }}>
        <ReactFlow
          nodes={nodes}
          edges={edges}
          onNodesChange={onNodesChange}
          onEdgesChange={onEdgesChange}
          onNodeDragStop={onNodeDragStop}
          onNodesDelete={onNodesDelete}
          onNodeClick={(_, node) => setSelectedNodes([node])}
          onNodeDoubleClick={onNodeDoubleClick}
          onConnect={onEdgesConnect}
          style={styles}
          onEdgeClick={(_, edge) => setSelectedEdges([edge])}
          onEdgesDelete={onEdgesDelete}
        >
          <Controls />
          <MiniMap />
          {/* <Background variant="dots" gap={12} size={1} /> */}
        </ReactFlow>
      </div>
    </div>
  );
}
