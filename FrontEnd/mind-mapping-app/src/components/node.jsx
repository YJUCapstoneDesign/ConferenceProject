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

const initialNodes = [
    {
        id: "1",
        type: "input",
        data: { label: "Mind Map" },
        position: { x: window.innerWidth / 2, y: window.innerHeight / 2 },
        style: { border: "3px solid #9999" },
    },
];

// const wss = new WebSocketServer({
//     port: 8080,
//     perMessageDeflate: {
//         zlibDeflateOptions: {
//             chunkSize: 1024,
//             memLevel: 7,
//             level: 3,
//         },
//     },
//     zlibDeflateOptions: {
//         chunkSize: 10 * 1024
//     },
//     clientNoContextTakeover: true,
//     serverNoContextTakeover: true,
//     clientMaxWindowBits: 10,
//     concurrencyLimit: 10,
//     threshold: 1024,
// });

const ws = new WebSocket("ws://localhost:8080", {
    perMessageDeflate: false
});

const initialEdges = [];
const onLoad = (reactFlowInstance) => {
    reactFlowInstance.fitView();
};

export default function MindNode() {
    const [nodes, setNodes, onNodesChange] = useNodesState(initialNodes);
    const [edges, setEdges, onEdgesChange] = useEdgesState(initialEdges);
    const [name, setName] = useState("");

    const addNode = () => {
        setNodes((prevNodes) =>
            prevNodes.concat({
                id: (prevNodes.length + 1).toString(),
                data: { label: `${name}` },
                position: {
                    x: Math.random() * window.innerWidth / 3,
                    y: Math.random() * window.innerHeight / 3,
                },
                style: { border: "10px solid #9999" },
            })
        );
        setName("");
    };

    useEffect(() => {
        ws.on("connection", (ws) => {
            ws.on("message", (message) => {
                console.log(`Received message => ${message}`);
            });
            ws.send("Hello! Message From Server!!");
        });
        return () => {
            ws.on('close', function close() {
                console.log('disconnected');
            })
        };
    }, [nodes, edges]);

    const onConnect = useCallback(
        (params) => setEdges((eds) => addEdge(params, eds)),
        [setEdges]
    );

    const handleSaveClick = async () => {
        try {
            saveMindMap(nodes, edges);
            const response = await fetch('/api/mind-map/save', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({ nodes: nodes, edges: edges }),
            });
            if (response.ok) {
                console.log('Mind Map saved successfully.');
            } else {
                console.error('Failed to save Mind Map.');
            }
        } catch (error) {
            console.error('Error saving Mind Map:', error);
        }
    };



    const handleLoadClick = async () => {
        // const loadedData = loadMindMap();
        // if (loadedData) {
        //     setNodes(loadedData.nodes);
        //     setEdges(loadedData.edges);
        //     console.log(loadedData);
        // }
        try {
            const response = await fetch('/api/mind-map/load');

            if (response.ok) {
                const loadedData = await response.json();
                setNodes(loadedData.nodes);
                setEdges(loadedData.edges);
                console.log(loadedData);
            } else {
                console.error('Failed to load Mind Map data.');
            }
        } catch (error) {
            console.error('Error loading Mind Map data:', error);
        }
    };

    const refreshPage = () => {
        useBeforeunload((event) => event.preventDefault());
        // window.location.reload();
    };

    const connectionLineStyle = {
        stroke: "#9999",
        strokeWidth: 3,

    };
    const defaultEdgeOptions = { style: connectionLineStyle, type: "mindmap" };

    const proOptions = { hideAttribution: true };

    return (
        <div id="container">
            <div className="button">
                <ul>
                    <li>
                        <input
                            type="text"
                            onChange={(e) => setName(e.target.value)}
                            name="title"
                        />
                        <button id="one" type="button" onClick={addNode}>
                            노드 추가
                        </button>
                    </li>
                    <li>
                        <button id="two" type="button" onClick={{}}>
                            노드 삭제
                        </button>
                    </li>
                    <li>
                        <button id="three" type="button" onClick={{}}>
                            연결 삭제
                        </button>
                    </li>
                    <li>
                        <button id="two" onClick={handleSaveClick}>
                            마인드맵 저장
                        </button>
                    </li>
                    <li>
                        <button id="three" onClick={handleLoadClick}>
                            마인드맵 불러오기
                        </button>
                    </li>
                    <li>
                        <button id="four" onClick={refreshPage}>
                            새로고침
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