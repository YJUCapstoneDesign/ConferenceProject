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
  const [socketData, setSocketData ] = useState();
  const [selectedNodes, setSelectedNodes] = useState({});
  const [selectedEdges, setSelectedEdges] = useState({});

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
      setSocketData(dataSet);
    } 
  })

  const addNode = useCallback(() => {
    console.log(selectedNodes)
    console.log(selectedEdges)
    const newNode = {
      id: (nodes.length + 1).toString(),
      position: { x: Math.random() * window.innerWidth, y: Math.random() * window.innerHeight },
      data: { label: "New Node" },
    }
    const newNodes = [...nodes, newNode];
    initialNodes = newNodes;

    webSockectConnect();

    const newData = {
      node: newNodes,
      edge: initialEdges
    }

    ws.current.onopen = () => {
      ws.current.send(JSON.stringify(newData));
    }
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

  const onNodesDelete = () => {
    webSockectConnect();

    const result = nodes.filter((node) => node.id !== selectedNodes.id);
    const newNodes = result;
    initialNodes = newNodes;

    const newData = {
      node: newNodes,
      edge: initialEdges
    }

    ws.current.onopen = () => {
      ws.current.send(JSON.stringify(newData));
    }
  }

  const onEdgeUpdate = () => {
    // console.log(selectedEdges);
  }

  const onSelectNodes = (e, nodes) => {
    setSelectedNodes(nodes);
    console.log(selectedNodes)
  }

  const onSelectEdges = (e, edges) => {
    setSelectedEdges(edges);
    console.log(selectedEdges)
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
        onNodesDelete={onNodesDelete}
        onNodeClick={onSelectNodes}
        onEdgeClick={onSelectEdges}
        onSelectionChange={() => {console.log("change")}}
        onSelectionDragStop={onSelectionDragStop}
        onEdgeUpdate={onEdgeUpdate}
        onConnect={() => {}}
      >
        <Controls />
        <MiniMap />
        <Background variant="dots" gap={12} size={1} />
      </ReactFlow>
    </div>
  );
}