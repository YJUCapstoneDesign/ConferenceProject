import React, { useState, useCallback, useEffect, useRef } from "react";
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
import "../index.css";
import { useBeforeunload } from "react-beforeunload";
import { CompatClient } from "@stomp/stompjs";

const client = useRef = useRef<CompatClient>();

// const connectHaner = () => {
//     client.current = Stromp.over(() => {
//         const sock = new SockJS("엔드포인트")
//     });
//     client.current.connect(
//         {
//             // 여기에서 유효성 검증을 위해 header를 넣어줄 수 있음.
//             // ex) Authorization: token
//         },
//         () => {
//             client.current.subscribe(
//                 `/백엔드와 협의한 api주소/{구독하고 싶은 방의 id}`,
//                 (message) => {
//                     setMessage(JSON.parse(message.body));
//                 }
//             )
//         },
//         {
//             // 여기에도 유효성 검증을 위한 header 넣어 줄 수 있음
//         }
//     )
// }

// const sendHandler = () => {
// 	client.current.send(
//       "/백엔드와 협의한 api주소",
//       {헤더},
//       JSON.stringify({
//         type: "TALK",
//         roomId: roomId,
//         sender: user.name,
//         message: inputMessage
//       })
//     );
// };

// 초기 노드 설정
const initialNodes = [
    {
        id: "1",
        type: "input",
        data: { label: "UNMUTE" },
        position: { x: window.innerWidth / 2, y: window.innerHeight / 2 },
        style: { border: "3px solid #9999" },
    },
];

const initialEdges = [];
const onLoad = (reactFlowInstance) => {
    reactFlowInstance.fitView();
};

export default function MindNode() {
    useBeforeunload((event) => event.preventDefault());

    const [nodes, setNodes, onNodesChange] = useNodesState(initialNodes);
    const [edges, setEdges, onEdgesChange] = useEdgesState(initialEdges);
    const [selectedNode, setSelectedNode] = useState(null);
    const [selectedNodeId, setSelectedNodeId] = useState(null);

    // 노드 추가
    const addNode = () => {
        const name = prompt("이름을 입력해주세요.");
        if (!name) return alert("이름을 입력해주세요.");

        // 중복 노드 검사
        if (nodes.some((node) => node.data.label === name)) {
            alert("이미 존재하는 노드입니다.");
            return;
        }

        // 화면 중앙을 기준으로 200px 안에서 랜덤으로 생성
        const centerX = window.innerWidth / 2;
        const centerY = window.innerHeight / 2;

        const positionX = centerX - 100 + Math.random() * 300; // x 위치
        const positionY = centerY - 100 + Math.random() * 300; // y 위치

        // 새로운 노드 추가
        const newNode = {
            id: `node_${Date.now()}`, // 새로운 ID를 생성하여 사용
            data: { label: `${name}` },
            position: {
                x: positionX,
                y: positionY,
            },
            style: { border: "10px solid #9999" },
        };

        setNodes((prevNodes) => prevNodes.concat(newNode));

        // 선택한 노드와 연결되도록 엣지 추가
        if (selectedNodeId) {
            const newEdge = {
                id: `edge_${selectedNodeId}_${newNode.id}`, // 새로운 ID를 생성하여 사용
                source: selectedNodeId,
                target: newNode.id,
                type: "default",
                style: connectionLineStyle,
            };
            setEdges((prevEdges) => prevEdges.concat(newEdge));
        }
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
        console.log("Mind Map saved successfully.");
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
    };

    // 선택해서 버튼으로 노드 삭제
    // const deleteNode = (e) => {
    //     if (!selectedNode) {
    //         alert("노드가 선택되지 않았습니다.");
    //         return;
    //     }
    //     if (e) {
    //         setNodes((prevNodes) =>
    //             prevNodes.filter((node) => node.id !== selectedNode.id)
    //         );
    //         setEdges((prevEdges) =>
    //             prevEdges.filter(
    //                 (edge) =>
    //                     edge.source !== selectedNode.id &&
    //                     edge.target !== selectedNode.id
    //             )
    //         );
    //         setSelectedNode(null);
    //         return;
    //     }
    // };

    // 캔버스 클릭시 선택된 노드 해제
    const handleCanvasClick = () => {
        setSelectedNode(null);
    };

    // 노드 클릭시 선택된 노드 설정
    const handleNodeClick = (event, node) => {
        setSelectedNodeId(node.id);
        setSelectedNode(node);
        event.stopPropagation();
    };

    // 연결 삭제 함수 수정
    // const deleteEdge = () => {
    //     if (!selectedNode) {
    //         alert("연결선이 선택되지 않았습니다.");
    //         return;
    //     }

    //     // 선택한 노드와 연결된 모든 엣지를 제거
    //     setEdges((prevEdges) =>
    //         prevEdges.filter(
    //             (edge) =>
    //                 edge.source !== selectedNode.id &&
    //                 edge.target !== selectedNode.id
    //         )
    //     );
    //     setSelectedNode(null);
    // };

    const connectionLineStyle = {
        stroke: "#9999",
        strokeWidth: 3,
    };
    const defaultEdgeOptions = {
        style: connectionLineStyle,
        type: "default",
    };

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
                <Background
                    variant="dots"
                    gap={12}
                    size={1}
                    onClick={() => setSelectedNode(null)}
                />
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
