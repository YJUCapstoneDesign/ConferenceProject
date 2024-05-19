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
        let [node, edge] = socketData; // 서버에서 받은 데이터를 node와 edge로 나눔
      // 1. 노드는 변경되지 않고 엣지만 변경되었을때(조건 : 이전 노드와 현재 노드가 같을때 && 이전 엣지안에 추가할 엣지가 없을때 : map 을 사용하여 비교)
      if(checkNode(node, edge) && !checkEdge(node, edge)) {
        setEdges((prev) => [...prev, JSON.parse(edge)]);
      // 2. 노드 만 변경되고 엣지는 변경이 되지 않았을때(조건 : 이전 노드와 현재 노드가 다를때 && 이전 엣지와 현재 엣지가 같을때 map 을 사용하여 비교)  
      } else if(!checkNode(node, edge) && checkEdge(node, edge)) {
        setNodes((prev) => [...prev, JSON.parse(node)]);
      } else {
      // 3. 노드와 엣지 둘다 변경 되었을때
        setNodes((prev) => [...prev, JSON.parse(node)]);
        setEdges((prev) => [...prev, JSON.parse(edge)]);
      }
    }
  }, [socketData])

  const webSockectConnect = useCallback(() => {
    ws.current = new WebSocket("ws://localhost:8080/app") // 서버 주소

    ws.current.onmessage = (message) => { // 서버에서 메시지가 오면 실행
      const dataSet = message.data.split("/") // 서버에서 받은 데이터를 /로 나눔
      setSocketData(dataSet) // 나눈 데이터를 setSocketData에 저장
    } 
  })

  const checkNode = (node, edge) => {
    // 이전 노드와 현재 노드가 같을때 && 이전 엣지안에 추가할 엣지가 없을때
    return nodes.map((item) => item.id).includes(JSON.parse(node).id) && !edges.map((item) => item.id).includes(JSON.parse(edge).id) 
  }

  const checkEdge = (node, edge) => {
    // 이전 노드와 현재 노드가 다를때 && 이전 엣지와 현재 엣지가 같을때
    return !nodes.map((item) => item.id).includes(JSON.parse(node).id) && edges.map((item) => item.id).includes(JSON.parse(edge).id) 
  }

  const send = useCallback(() => { // 버튼 클릭 시 실행
    webSockectConnect() // 웹소켓 연결
    
    const newNode = {
      id: (nodes.length + 1).toString(),
      position: { x: Math.random() * window.innerWidth, y: Math.random() * window.innerHeight },
      data: { label: "New Node" },
    }

    const newEdge = {
      id: 'e1-3', 
      source: '1', 
      target: '3' 
    }

    // 3가지 조건
    // 1. 노드는 변경되지 않고 엣지만 변경되었을때( 조건 : newNode의 값은 변경되지 않고 newEdge의 값만 변경되었을때 엣지의 값만 서버로 전송)
    // 2. 노드 만 변경되고 엣지는 변경이 되지 않았을때( 조건 : newNode의 값만 변경되고 newEdge의 값은 변경되지 않았을때 노드의 값만 서버로 전송)
    // 3. 노드와 엣지 둘다 변경 되었을때( 조건 : newNode의 값과 newEdge의 값이 변경되었을때 노드와 엣지의 값 모두 서버로 전송)

    const newNodeData = JSON.stringify(newNode) // JSON 형식으로 변환
    const newEdgeData = JSON.stringify(newEdge) // JSON 형식으로 변환

    const data = newNodeData.concat("/").concat(newEdgeData) // 노드와 엣지를 /로 나눔
    ws.current.onopen = () => { // 웹소켓이 연결되면 실행
      ws.current.send(data) // 서버로 데이터 전송
    }
  });
  

  return (
    <div style={{ width: '100vw', height: '100vh' }}>
        <button onClick={send}>추가 버튼</button>
      <ReactFlow
        nodes={nodes}
        edges={edges}
        onNodesChange={onNodesChange}
        onEdgesChange={onEdgesChange}
        onConnect={() => {}}
      >
        <Controls />
        <MiniMap />
        <Background variant="dots" gap={12} size={1} />
      </ReactFlow>
    </div>
  );
}