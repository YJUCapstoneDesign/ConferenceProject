import React, { useState, useEffect, useCallback } from "react"; // useCallback 추가
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
import SockJS from "sockjs-client";
import Stomp from "stompjs";

const END_POINT = import.meta.env.VITE_REACT_APP_AWS_MDB_ENDPOINT;

export default function MindNode() {
    const [stompClient, setStompClient] = useState(null);
    const [nodes, setNodes, onNodesChange] = useNodesState([]);
    const [edges, setEdges, onEdgesChange] = useEdgesState([]);
    const [selectedNodeId, setSelectedNodeId] = useState(null); // 선택된 노드의 ID 상태 추가
    const [selectedNode, setSelectedNode] = useState(null); // 선택된 노드 상태 추가

    useEffect(() => {
        const socket = new SockJS("http://localhost:8080/ws/mind-map");
        const stomp = Stomp.over(socket);
        stomp.connect({}, onConnected, onError);
        setStompClient(stomp);

        return () => {
            if (stompClient) {
                stompClient.disconnect();
            }
        };
    }, []);

    function onConnected() {
        stompClient.subscribe("/topic/update", onMessageReceived);
        stompClient.send(
            "/ws/mind-map",
            {},
            JSON.stringify({ sender: "username", type: "JOIN" })
        );
    }

    function onError(error) {
        console.error("WebSocket error:", error);
    }

    useEffect(() => {
        const loadedData = loadMindMap();
        if (loadedData) {
            setNodes(loadedData.nodes);
            setEdges(loadedData.edges);
        }
    }, []);

    useBeforeunload(() => {
        saveMindMap(nodes, edges);
    });

    const handleSaveClick = async () => {
        console.log("Save button clicked.");
        const mindMapData = { nodes, edges };

        try {
            const response = await fetch(END_POINT, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify(mindMapData),
            });
            if (response.ok) {
                console.log("Mind map data sent successfully.");
            } else {
                console.error(
                    "Failed to send mind map data:",
                    response.statusText
                );
            }
        } catch (error) {
            console.error("Error sending mind map data:", error);
        }
    };

    const handleLoadClick = async () => {
        try {
            const response = await fetch(END_POINT);
            if (response.ok) {
                const responseData = await response.json();
                setNodes(responseData.nodes);
                setEdges(responseData.edges);
                console.log("Mind map data loaded successfully.");
            } else {
                console.error(
                    "Failed to load mind map data:",
                    response.statusText
                );
            }
        } catch (error) {
            console.error("Error loading mind map data:", error);
        }
    };

    const addNode = () => {
        const name = prompt("이름을 입력해주세요.");
        if (!name) return alert("이름을 입력해주세요.");

        if (nodes.some((node) => node.data.label === name)) {
            alert("이미 존재하는 노드입니다.");
            return;
        }

        const centerX = window.innerWidth / 2;
        const centerY = window.innerHeight / 2;

        const positionX = centerX - 100 + Math.random() * 300;
        const positionY = centerY - 100 + Math.random() * 300;

        const newNode = {
            id: `node_${Date.now()}`,
            data: { label: `${name}` },
            position: {
                x: positionX,
                y: positionY,
            },
            style: { border: "10px solid #9999" },
        };

        setNodes((prevNodes) => prevNodes.concat(newNode));

        if (selectedNodeId) {
            const newEdge = {
                id: `edge_${selectedNodeId}_${newNode.id}`,
                source: selectedNodeId,
                target: newNode.id,
                type: "default",
                style: connectionLineStyle,
            };
            setEdges((prevEdges) => prevEdges.concat(newEdge));
        }
    };

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

    const handleCanvasClick = () => {
        setSelectedNode(null);
    };

    const handleNodeClick = (event, node) => {
        setSelectedNodeId(node.id);
        setSelectedNode(node);
        event.stopPropagation();
    };

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
                            Add Node
                        </button>
                    </li>
                    <li>
                        <button id="three" type="button" onClick={renameNode}>
                            Rename Node
                        </button>
                    </li>
                    <li>
                        <button id="five" onClick={handleSaveClick}>
                            Save Mind Map
                        </button>
                    </li>
                    <li>
                        <button id="six" onClick={handleLoadClick}>
                            Load Mind Map
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
                onClick={handleCanvasClick}
                onNodeClick={handleNodeClick}
                onLoad={() => {}}
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
