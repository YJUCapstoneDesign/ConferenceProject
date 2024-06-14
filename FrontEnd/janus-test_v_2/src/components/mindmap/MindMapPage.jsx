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

let initialNodes = [

];

const baseURL = process.env.REACT_WEBSOCKET_SERVER;

let initialEdges = [

];

export default function MindMapPage() {

  const {teamNumber} = useParams();

  const [nodes, setNodes, onNodesChange] = useNodesState(initialNodes);
  const [edges, setEdges, onEdgesChange] = useEdgesState(initialEdges);
  const [socketData, setSocketData] = useState();
  const [selectedNodes, setSelectedNodes] = useState([]);
  const [selectedEdges, setSelectedEdges] = useState([]);
  const [fileList, setFileList] = useState([]);


  const ws = useRef(null); // 웹소켓 연결을 위한 ref


  const handleSaveClick = async () => {
    const mindMapData = {
      nodes: initialNodes,
      edges: initialEdges,
    };

    const mindMapDataJson = JSON.stringify(mindMapData);

    const uploadResult = await uploadToS3(mindMapDataJson, 'mind', teamNumber);

    setFileList([]);

    if (uploadResult.success) {
      alert('파일 저장 성공!');
    } else {
      alert('파일 저장 실패!');
    }
  };

  const handleLoadData = async (fileName) => {
    try {
      // 파일 목록을 받아옴
      const files = await listUploadedFiles();
      console.log('업로드된 파일 목록:', files);
      
      // 파일 목록 상태 업데이트
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
      initialNodes = newNodes;
      initialEdges = newEdges;
  
      const newData = {
        node: initialNodes,
        edge: initialEdges
      };
  
      sendWebSocketData(newData);
      setFileList([]); //리스트 초기화
    } catch (error) {
      console.error('파일 적용 실패:', error);
    }
  };
  

  useEffect(() => {
    ws.current = new WebSocket(`${baseURL}/app`);
    console.log("웹소켓 연결됨");

    ws.current.onopen = () => {
      ws.current.send(
        JSON.stringify(
          { id: parseInt(teamNumber), type: "ENTER" }
        )
      );
    };

    ws.current.onmessage = (message) => { // 서버에서 메시지가 오면 실행
      console.log("message: ", message.data);
      setSocketData(message.data);
    };

    return () => {
      ws.current.close();
    };
  }, []);

  useEffect(() => {
    if (socketData !== undefined) {
      const parsedSocketData = JSON.parse(socketData).data;
      console.log("parsedSocketData: ", parsedSocketData);
      setNodes(parsedSocketData.node);
      setEdges(parsedSocketData.edge);
    }
  }, [socketData]);

  const sendWebSocketData = useCallback((data) => {
    console.log("sendWebSocketData: ", data);
    const sendData = {
      id: parseInt(teamNumber),
      type: "MSG",
      data,
    }
    if (ws.current && ws.current.readyState === WebSocket.OPEN) {
      ws.current.send(JSON.stringify(sendData));
    } else {
      ws.current.onopen = () => {
        ws.current.send(JSON.stringify(sendData));
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
      edge: edges
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

  return (
    <div style={{ width: '100vw', height: '100vh' }}>
      <div className="inline-flex shadow-sm rounded-sm mt-1 ml-1" role="group">
      <button onClick={addNode} className='px-4 py-2 text-sm font-semibold text-gray-900 rounded-s-lg bg-transparent border hover:bg-gray-900 hover:text-white focus:z-10 focus:ring-2 focus:ring-gray-500 focus:bg-gray-900 focus:text-white dark:border-white dark:text-white dark:hover:text-white dark:hover:bg-gray-700 dark:focus:bg-gray-700 transition-colors duration-500 ease-in-out'>
        Add
      </button>
      <button onClick={handleSaveClick} className='px-4 py-2 text-sm font-semibold text-gray-900 bg-transparent border-t border-b hover:bg-gray-900 hover:text-white focus:z-10 focus:ring-2 focus:ring-gray-500 focus:bg-gray-900 focus:text-white dark:border-white dark:text-white dark:hover:text-white dark:hover:bg-gray-700 dark:focus:bg-gray-700 transition-colors duration-500 ease-in-out'>
        Save
      </button>
      <button onClick={handleLoadData} className='px-4 py-2 text-sm font-semibold text-gray-900 rounded-e-lg bg-transparent hover:bg-gray-900 hover:text-white focus:z-10 focus:ring-2 focus:ring-gray-500 focus:bg-gray-900 focus:text-white dark:border-white dark:text-white dark:hover:text-white dark:hover:bg-gray-700 dark:focus:bg-gray-700 transition-colors duration-500 ease-in-out'>
        Load
      </button>
      </div>
      {/* 파일 목록을 표시하는 부분 */} 
      {fileList && fileList.length > 0 && (
        <div>
          <h2>파일 목록</h2>
          <ul>
            {fileList.map((file, index) => (
              <li key={index}>
                {file}
                <button onClick={() => handleApplyFile(file)}>적용하기</button>
              </li>
            ))}
          </ul>
        </div>
      )}
      
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
