import React, { useState, useEffect, useCallback } from "react";
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
import SockJS from "sockjs-client";
import Stomp from "stompjs";

export default function MindNode() {
    const [stompClient, setStompClient] = useState(null); 
    const [nodes, setNodes, onNodesChange] = useNodesState([]);
    const [edges, setEdges, onEdgesChange] = useEdgesState([]);
    const [selectedNodeId, setSelectedNodeId] = useState(null);
    const [selectedNode, setSelectedNode] = useState(null);
    

    useEffect(() => {
        const socket = new SockJS("http://192.168.219.100:8080/ws");
        const stomp = Stomp.over(socket);
        stomp.connect({}, () => onConnected(stomp), onError);
        setStompClient(stomp);
    
        return () => {
            if (stompClient) {
                stompClient.disconnect();
            }
        };
    }, []);

    function onConnected(stompClient) {
        const mindMapData = {data: { nodes, edges }};
        stompClient.subscribe("/topic/update/1", onMessageReceived);
        stompClient.send(
            "/app/ws/mind-map/1",
            {},
            JSON.stringify(mindMapData)
        );
    }
    
    function onError(error) {
        console.error("WebSocket error:", error);
    }
    
    const onMessageReceived = useCallback((payload) => {
        console.log("메시지 수신:", payload);
        const message = JSON.parse(payload.body);
        setNodes(message.data.nodes);
        setEdges(message.data.edges);
        console.log("메시지 수신:", message);
        switch (message.type) {
            case "NODE_ADDED":
                const newNode = message.node;
                setNodes((prevNodes) => [...prevNodes, newNode]);
                setEdges((prevEdges) => [...prevEdges, ...message.edges]);
                break;
            case "NODE_REMOVED":
                const nodeId = message.nodeId;
                setNodes((prevNodes) => prevNodes.filter((node) => node.id !== nodeId));
                setEdges((prevEdges) =>
                    prevEdges.filter(
                        (edge) => edge.source !== nodeId && edge.target !== nodeId
                    )
                );
                break;
            case "EDGE_ADDED":
                const newEdge = message.edge;
                setEdges((prevEdges) => [...prevEdges, newEdge]);
                break;
            default:
                console.error("알 수 없는 메시지 유형:", message.type);
                break;
        }
    }, [setNodes, setEdges]);
    
    useEffect(() => {
        const loadedData = loadMindMap();
        if (loadedData) {
            setNodes(loadedData.nodes);
            setEdges(loadedData.edges);
        }
    }, [loadMindMap]);
    
    useEffect(() => {
        const handleUnload = () => {
            saveMindMap(nodes, edges);
        };
        window.addEventListener("beforeunload", handleUnload);
        return () => {
            window.removeEventListener("beforeunload", handleUnload);
        };
    }, [nodes, edges]);
    

    const handleSaveClick = async () => {
        const mindMapData = {data: { nodes, edges }};
        try {
            const response = await fetch("/api/mind-map/save", {
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

    const handleLoadClick = async (id) => {
        try {
            const response = await fetch(`/api/mind-map/load/${id}`);
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

    const addNode = (event) => {
        if (selectedNode) {
            const offsetX = 150;
            const offsetY = 100;
            const newNode = {
                id: `node_${Date.now()}`,
                data: { label: "New Node" },
                position: {
                    x: selectedNode.position.x + offsetX,
                    y: selectedNode.position.y + offsetY,
                },
                style: { border: "5px solid #9999" },
            };
    
            const newEdge = {
                id: `edge_${selectedNode.id}_${newNode.id}`,
                source: selectedNode.id,
                target: newNode.id,
                type: "default",
                style: connectionLineStyle,
            };
    
            setNodes((prevNodes) => [...prevNodes, newNode]);
            setEdges((prevEdges) => [...prevEdges, newEdge]);
    
            const mindMapData = { data: { nodes: [...nodes, newNode], edges: [...edges, newEdge] } };
            stompClient.send("/app/ws/mind-map/1", {}, JSON.stringify(mindMapData));
        } else {
            const offsetX = event.clientX;
            const offsetY = event.clientY;
            const newNode = {
                id: `node_${Date.now()}`,
                data: { label: "New Node" },
                position: {
                    x: offsetX,
                    y: offsetY,
                },
                style: { border: "5px solid #9999" },
            };
    
            const mindMapData = { data: { nodes: [...nodes, newNode], edges } };
            stompClient.send("/app/ws/mind-map/1", {}, JSON.stringify(mindMapData));
    
            setNodes((prevNodes) => [...prevNodes, newNode]);
        }
    };

    const removeNode = (nodeId) => {
        setNodes((prevNodes) => prevNodes.filter((node) => node.id !== nodeId));
        setEdges((prevEdges) =>
            prevEdges.filter(
                (edge) => edge.source !== nodeId && edge.target !== nodeId
            )
        );
    
        const updatedNodes = nodes.filter((node) => node.id !== nodeId);
        const mindMapData = { data: { nodes: updatedNodes, edges } };
        stompClient.send("/app/ws/mind-map/1", {}, JSON.stringify(mindMapData));
    };
    
    const handleNodeContextMenu = (event, node) => {
        event.preventDefault(); // 기본 우클릭 메뉴 표시 방지
        removeNode(node.id);
    };
    
    
    const handleNodeDelete = (nodeId) => {
        removeNode(nodeId);
    };
    
    
    const renameNode = (nodeId, newName) => {
        setNodes((prevNodes) =>
            prevNodes.map((node) =>
                node.id === nodeId
                    ? { ...node, data: { label: newName } }
                    : node
            )
        );
    };

    const handleNodeDoubleClick = (e, node) => {
        const newName = prompt("Enter a new name for the node:", node.data.label);
        if (newName !== null) {
            renameNode(node.id, newName);
            const updatedNode = { ...node, data: { label: newName } };
            const updatedNodes = nodes.map((n) => (n.id === node.id ? updatedNode : n));
            const mindMapData = { data: { nodes: updatedNodes, edges } };
            stompClient.send("/app/ws/mind-map/1", {}, JSON.stringify(mindMapData));
        }
    };

    const handleNodeDragStop = (event, node) => {
        const updatedNodes = nodes.map((n) => {
            if (n.id === node.id) {
                return {
                    ...n,
                    position: { x: node.position.x, y: node.position.y },
                };
            }
            return n;
        });
    
        const mindMapData = { data: { nodes: updatedNodes, edges } };
        stompClient.send("/app/ws/mind-map/1", {}, JSON.stringify(mindMapData));
    };
    

    const onConnect = useCallback(
        (params) => setEdges((eds) => addEdge(params, eds)),
        [setEdges]
    );

    const handleCanvasClick = () => {
        setSelectedNode(null);
        setSelectedNodeId(null);
    };

    const handleNodeClick = (event, node) => {
        setSelectedNodeId(node.id);
        setSelectedNode(node);
        event.stopPropagation();
    };

    const handleClearMindMap = () => {
        setNodes([]);
        setEdges([]);

        const mindMapData = {data: { nodes: [], edges: [] }};
        stompClient.send(
            "/app/ws/mind-map/1",
            {},
            JSON.stringify(mindMapData)
        );
    }

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
                        <button id="three" onClick={handleSaveClick}>
                            마인드맵 저장
                        </button>
                    </li>
                    <li>
                        <input
                            type="text"
                            placeholder="Enter ID"
                            onChange={(e) => setSelectedNodeId(e.target.value)}
                        />
                        <button
                            id="four"
                            onClick={() => handleLoadClick(selectedNodeId)}
                        >
                            마인드맵 불러오기
                        </button>
                    </li>
                    <li>
                        <button id="five" onClick={handleClearMindMap}>
                            초기화
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
                onNodeDragStop={handleNodeDragStop}
                onNodeDoubleClick={handleNodeDoubleClick}
                // onNodesDelete={handleNodeDelete}
                onNodeContextMenu={handleNodeContextMenu}
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