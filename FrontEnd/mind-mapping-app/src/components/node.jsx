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

const initialNodes = [
    {
        id: "1",
        type: "input",
        data: { label: "Mind Map" },
        position: { x: window.innerWidth / 2, y: window.innerHeight / 2 },
        style: { border: "3px solid #9999" },
    },
];

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



    const onConnect = useCallback(
        (params) => setEdges((eds) => addEdge(params, eds)),
        [setEdges]
    );
    const handleSaveClick = () => {
        saveMindMap(nodes, edges);
        console.log(nodes);
    };
    const handleLoadClick = () => {
        const loadedData = loadMindMap();
        if (loadedData) {
            setNodes(loadedData.nodes);
            setEdges(loadedData.edges);
            console.log(loadedData);
        }
    };

    const refreshPage = () => {
        window.location.reload();
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
                            Add Node
                        </button>
                    </li>
                    <li>
                        <button id="two" type="button" onClick={{}}>
                            Remove Node
                        </button>
                    </li>
                    <li>
                        <button id="three" type="button" onClick={{}}>
                            Remove Edge
                        </button>
                    </li>
                    <li>
                        <button id="two" onClick={handleSaveClick}>
                            Save Mind Map
                        </button>
                    </li>
                    <li>
                        <button id="three" onClick={handleLoadClick}>
                            Load Mind Map
                        </button>
                    </li>
                    <li>
                        <button id="four" onClick={refreshPage}>
                            Refresh
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