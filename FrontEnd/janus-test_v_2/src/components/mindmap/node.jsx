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
  // const [message, setMessage] = useState(''); // 서버에서 받은 데이터를 저장할 state
  const [socketData, setSocketData ] = useState();

  const ws = useRef(null); // 웹소켓 연결을 위한 ref

  useEffect(() => {
    if(socketData !== undefined) {
       let parseSocketData = JSON.parse(socketData);
       
       console.log(parseSocketData.node); 
       setNodes(parseSocketData.node);
        setEdges(parseSocketData.edge);
    }
  }, [socketData])

  const webSockectConnect = useCallback(() => {
    ws.current = new WebSocket("ws://localhost:8080/app") // 서버 주소

    ws.current.onmessage = (message) => { // 서버에서 메시지가 오면 실행
      console.log("msg updated");
      const dataSet = message.data
      setSocketData(dataSet) // 나눈 데이터를 setSocketData에 저장
    } 
  })

  const addNode = useCallback(() => { // 버튼 클릭 시 실행
    const newNode = {
      id: (nodes.length + 1).toString(),
      position: { x: Math.random() * window.innerWidth, y: Math.random() * window.innerHeight },
      data: { label: "New Node" },
    }
    onDataChange(newNode, null);
  });

  const onSelectionDragStop = (e, nodes) => {
    webSockectConnect();
    nodes.forEach((node) => {
      initialNodes.findIndex((item, index) => {
        if(item.id === node.id) {
          initialNodes[index].position = node.position;
        }
      })
    });
    
    const newData = {
      node: initialNodes,
      edge: initialEdges
    }
    ws.current.onopen = () => {
      ws.current.send(JSON.stringify(newData));
    }
  }
  const onNodeDragStop = (e, dragNode, nodes) => {
    webSockectConnect();
    nodes.forEach((node) => {
      initialNodes.findIndex((item, index) => {
        if(item.id === node.id) {
          initialNodes[index].position = node.position;
        }
      })
    });
    
    const newData = {
      node: initialNodes,
      edge: initialEdges
    }
    ws.current.onopen = () => {
      ws.current.send(JSON.stringify(newData));
    }
  }
  return (
    <div style={{ width: '100vw', height: '100vh' }}>
        <button onClick={addNode}>추가 버튼</button>
      <ReactFlow
        nodes={nodes}
        edges={edges}
        onNodesChange={() => {onNodesChange}}
        onEdgesChange={() => {onEdgesChange}}
        onNodeDragStop={onNodeDragStop}
        onSelectionDragStop={onSelectionDragStop}
        onConnect={() => {}}
      >
        <Controls />
        <MiniMap />
        <Background variant="dots" gap={12} size={1} />
      </ReactFlow>
    </div>
  );
}