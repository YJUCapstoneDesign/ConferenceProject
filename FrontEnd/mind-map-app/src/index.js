import cytoscape from 'cytoscape';
import './style.css';
import coseBilkent from 'cytoscape-cose-bilkent';
import nodesData from './nodes';
import edgesData from './edges';
import addNode from './addNode';

// console.log(nodesData);
// console.log(edgesData);
console.log(addNode);

cytoscape.use(coseBilkent);

// nodes와 edges를 연결하는 함수
function mergeData(nodes, edges) {
    // 연결된 데이터를 담을 배열
    const mergedData = [];

    // edges를 배열로 변환
    edges = Object.values(edges);

    // 각 edge를 돌면서 source와 target에 해당하는 node를 찾아서 연결
    edges.forEach((edge) => {
        const sourceNode = nodes.find((node) => node.data.id === edge.data.source);
        const targetNode = nodes.find((node) => node.data.id === edge.data.target);

        // source와 target 모두 찾았을 때에만 연결된 edge를 추가
        if (sourceNode && targetNode) {
            mergedData.push({
                data: {
                    id: edge.data.id,
                    source: edge.data.source,
                    target: edge.data.target,
                },
            });
        }
    });

    return mergedData;
}

// nodes와 edges를 연결하여 결과를 출력
const connectedData = mergeData(nodesData, edgesData); // edges 데이터는 불러온 것으로 가정
// console.log(connectedData);

const cy = cytoscape({
    container: document.getElementById('cy'),
    elements: {
        nodes: nodesData, // 불러온 nodes 데이터를 사용
        edges: connectedData, // 연결된 edges 데이터를 사용
    },
    style: [
        {
            selector: 'node',
            style: {
                'background-color': '#000080',
                label: 'data(label)',
            },
        },
        {
            selector: 'edge',
            style: {
                width: 1,
                'curve-style': 'bezier',
                'line-color': '#ccc',
                'source-arrow-color': '#ccc',
                'source-arrow-shape': 'vee',
            },
        },
    ],
    layout: {
        // 레이아웃을 설정
        name: 'cose-bilkent',
        animate: false,
        gravityRangeCompound: 1.5,
        fit: true,
        tile: true,
    },
});
