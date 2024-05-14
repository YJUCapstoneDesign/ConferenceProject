import React, { useCallback, useEffect } from 'react';
import ReactFlow, {
  MiniMap,
  Controls,
  Background,
  useNodesState,
  useEdgesState,
  addEdge,
} from 'reactflow';
 
import 'reactflow/dist/style.css';

const initialEdges = [{ id: 'e1-2', source: '1', target: '2' }];
 
let initialNodes = [
    { id: '1', position: { x: 0, y: 0 }, data: { label: '1' } },
    { id: '2', position: { x: 0, y: 100 }, data: { label: '2' } },
  ];

export default function App() {
  const [nodes, setNodes, onNodesChange] = useNodesState(initialNodes);
  const [edges, setEdges, onEdgesChange] = useEdgesState(initialEdges);

    useEffect(() => {
        console.log(nodes)
        return () => {
            console.log('finish')
        }
    },[nodes])

  const onConnect = useCallback(
    (params) => setEdges((eds) => addEdge(params, eds)),
    [setEdges],
  );

  const addNode = useCallback(
    () => {
        setNodes((initialNodes)=>[...initialNodes, { id: `${initialNodes.length + 1}`, position: { x: 10, y: 200 }, data: { label: `${initialNodes.length + 1}` } }])
    }
);

  return (
    <div style={{ width: '100vw', height: '100vh' }}>
        <button onClick={addNode}>추가 버튼</button>
      <ReactFlow
        nodes={nodes}
        edges={edges}
        onNodesChange={onNodesChange}
        onEdgesChange={onEdgesChange}
        onConnect={onConnect}
      >
        <Controls />
        <MiniMap />
        <Background variant="dots" gap={12} size={1} />
      </ReactFlow>
    </div>
  );
}