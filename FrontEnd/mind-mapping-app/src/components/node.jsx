import React, { useState, useCallback, useEffect } from "react";
import ReactFlow, {
    MiniMap,
    Controls,
    useNodesState,
    useEdgesState,
    addEdge,
    Background,
} from "reactflow";
import { saveMindMap, loadMindMap } from "./storage";
import "reactflow/dist/style.css";
import "../index.css"
import WebSocket from "ws";
import { useBeforeunload } from "react-beforeunload";

// 초기 노드 설정
const initialNodes = [
    {
        id: "1",
        type: "input",
        data: { label: "Mind Map" },
        position: { x: window.innerWidth / 2, y: window.innerHeight / 2 },
        style: { border: "3px solid #9999" },
    },
];

// const ws = new WebSocket("ws://localhost:8080", {
//     perMessageDeflate: false
// });

const initialEdges = [];
const onLoad = (reactFlowInstance) => {
    reactFlowInstance.fitView();
};

export default function MindNode() {
    useBeforeunload((event) => event.preventDefault());

    const [nodes, setNodes, onNodesChange] = useNodesState(initialNodes);
    const [edges, setEdges, onEdgesChange] = useEdgesState(initialEdges);
    const [selectedNode, setSelectedNode] = useState(null);

    // 노드 추가
    const addNode = () => {
        const name = prompt("이름을 입력해주세요.");
        if (!name) return alert("이름을 입력해주세요.");

        // 중복 노드 검사
        if (nodes.some(node => node.data.label === name)) {
            alert("이미 존재하는 노드입니다.");
            return;
        }

        // 화면 중앙을 기준으로 200px 안에서 랜덤으로 생성
        const centerX = window.innerWidth / 2;
        const centerY = window.innerHeight / 2;

        const positionX = centerX - 100 + Math.random() * 300; // x 위치
        const positionY = centerY - 100 + Math.random() * 300; // y 위치

        // 새로운 노드 추가
        setNodes((prevNodes) =>
            prevNodes.concat({
                id: `node_${Date.now()}`, // 새로운 ID를 생성하여 사용
                data: { label: `${name}` },
                position: {
                    x: positionX,
                    y: positionY,
                },
                style: { border: "10px solid #9999" },
            })
        );
    };


    // 노드를 선택해서 노드 이름 변경
    const renameNode = () => {
        if (!selectedNode) {
            alert("노드가 선택되지 않았습니다.");
            return;
        }
        const name = prompt("이름을 입력해주세요.");
        if (!name) return alert("이름을 입력해주세요.");
        setNodes((prevNodes) =>
            prevNodes.map((node) =>
                node.id === selectedNode.id
                    ? { ...node, data: { label: `${name}` } }
                    : node
            )
        );
    };

    const onConnect = useCallback(
        (params) => setEdges((eds) => addEdge(params, eds)),
        [setEdges]
    );

    // 세이브 버튼
    const handleSaveClick = async () => {
        // 로컬 스토리지에 저장
        saveMindMap(nodes, edges);
        console.log('Mind Map saved successfully.');

        // 서버에 저장
        // try {
        //     const data = JSON.stringify({ nodes: nodes, edges: edges });
        //     saveMindMap(nodes, edges);
        //     const response = await fetch('/api/mind-map/save', {
        //         method: 'POST',
        //         headers: {
        //             'Content-Type': 'application/json',
        //         },
        //         body: data,
        //     });
        //     if (response.ok) {
        //         console.log('Mind Map saved successfully.');
        //     } else {
        //         console.error('Failed to save Mind Map.');
        //     }
        // } catch (error) {
        //     console.error('Error saving Mind Map:', error);
        // }
    };

    // 불러오기 버튼
    const handleLoadClick = async () => {
        // 로컬 스토리지에서 불러오기
        const loadedData = loadMindMap();
        if (loadedData) {
            setNodes(loadedData.nodes);
            setEdges(loadedData.edges);
            console.log(loadedData);
        }

        // 서버에서 불러오기
        // try {
        //     const response = await fetch('/api/mind-map/load');

        //     if (response.ok) {
        //         const loadedData = await response.json();
        //         setNodes(loadedData.nodes);
        //         setEdges(loadedData.edges);
        //         console.log(loadedData);
        //     } else {
        //         console.error('Failed to load Mind Map data.');
        //     }
        // } catch (error) {
        //     console.error('Error loading Mind Map data:', error);
        // }
    };

    // 선택해서 버튼으로 노드 삭제
    const deleteNode = (e) => {
        if (!selectedNode) {
            alert("노드가 선택되지 않았습니다.");
            return;
        }
        if (e) {
            setNodes((prevNodes) =>
                prevNodes.filter((node) => node.id !== selectedNode.id)
            );
            setEdges((prevEdges) =>
                prevEdges.filter(
                    (edge) =>
                        edge.source !== selectedNode.id &&
                        edge.target !== selectedNode.id
                )
            );
            setSelectedNode(null);
            return;
        }
    };

    // 캔버스 클릭시 선택된 노드 해제
    const handleCanvasClick = () => {
        // console.log("캔버스 클릭");
        setSelectedNode(null);
    }

    // 노드 클릭시 선택된 노드 설정
    const handleNodeClick = (e, node) => {
        // console.log("노드 클릭");
        setSelectedNode(node);
        e.stopPropagation();
    }

    // 엣지를 선택해서 버튼으로 연결 삭제
    const deleteEdge = () => {
        if (!selectedNode) {
            alert("연결선이 선택되지 않았습니다.");
            return;
        }
        setEdges((prevEdges) =>
            prevEdges.filter(
                (edge) =>
                    edge.source !== selectedNode.id &&
                    edge.target !== selectedNode.id
            )
        );
        setSelectedNode(null);
    };

    // 웹 소켓 연결
    // useEffect(() => {
    //     ws.on("connection", (ws) => {
    //         ws.on("message", (message) => {
    //             console.log(`Received message => ${message}`);
    //         });
    //         ws.send("Hello! Message From Server!!");
    //     });
    //     return () => {
    //         ws.on('close', function close() {
    //             console.log('disconnected');
    //         })
    //     };
    // }, [nodes, edges]);

    const connectionLineStyle = {
        stroke: "#9999",
        strokeWidth: 3,

    };
    const defaultEdgeOptions = { style: connectionLineStyle, type: "mindmap" };

    return (
        <div id="container">
            <div className="button">
                <ul>
                    <li>
                        <button id="one" type="button" onClick={addNode}>
                            노드 추가
                        </button>
                    </li>
                    <li>
                        <button id="three" type="button" onClick={renameNode}>
                            노드 이름 변경
                        </button>
                    </li>
                    <li>
                        <button id="two" type="button" onClick={deleteNode}>
                            노드 삭제
                        </button>
                    </li>
                    <li>
                        <button id="four" type="button" onClick={deleteEdge}>
                            연결 삭제
                        </button>
                    </li>
                    <li>
                        <button id="five" onClick={handleSaveClick}>
                            마인드맵 저장
                        </button>
                    </li>
                    <li>
                        <button id="six" onClick={handleLoadClick}>
                            마인드맵 불러오기
                        </button>
                    </li>
                </ul>
            </div>
            <ReactFlow
                nodes={nodes}
                edges={edges}
                onNodesChange={onNodesChange}
                onEdgesChange={onEdgesChange}
                connectionLineStyle={connectionLineStyle}
                defaultEdgeOptions={defaultEdgeOptions}
                onConnect={onConnect}
                onLoad={onLoad}
                onNodeClick={handleNodeClick}
                onClick={handleCanvasClick}
            >
                <Controls />
                <Background variant="dots" gap={12} size={1} />
                <MiniMap
                    nodeColor={(n) => {
                        if (n.type === "input") return "blue";

                        return "#FFCC00";
                    }}
                />
            </ReactFlow>
        </div>
    );
}