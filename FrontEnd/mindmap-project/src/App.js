import React from 'react';
import CytoscapeComponent from 'react-cytoscapejs';
import './style.css';

class App extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      nodes: [
        { data: { id: '노드1', label: '노드1' }, position: { x: 600, y: 600 } },
        { data: { id: '노드2', label: '노드2' }, position: { x: 100, y: 0 } },
      ],
      edges: [
        { data: { id: 'edge1', source: '노드1', target: '노드2', label: 'Edge from Node1 to Node2' } }
      ]
    };
  }

  handleAddNode = () => {
    const label = prompt('Enter label for new node');
    const id = label.toLowerCase().replace(/\s+/g, '-');
    const newNode = { data: { id: id, label: label }, position: { x: 300, y: 300 } };
    this.setState(prevState => ({
      nodes: [...prevState.nodes, newNode]
    }));
  };

  handleRemoveNode = () => {
    const label = prompt('Enter label for node to remove');
    this.setState(prevState => ({
      nodes: prevState.nodes.filter(node => node.data.label !== label),
      edges: prevState.edges.filter(edge => edge.data.source !== label && edge.data.target !== label)
    }));
  };

  handleUpdateNode = () => {
    const label = prompt('Enter label for node to edit');
    const newLabel = prompt('Enter new label for node');
    this.setState(prevState => ({
      nodes: prevState.nodes.map(node => {
        if (node.data.label === label) {
          return { ...node, data: { ...node.data, label: newLabel } };
        }
        return node;
      })
    }));
  };

  handleConnectNodes = () => {
    const source = prompt('Enter source node label');
    const target = prompt('Enter target node label');

    if (this.state.edges.some(edge => (edge.data.source === source && edge.data.target === target) || (edge.data.source === target && edge.data.target === source))) {
      alert('이미 연결된 노드입니다.');
      return;
    }

    const newEdge = { data: { id: `edge${this.state.edges.length + 1}`, source: source, target: target, label: `Edge from ${source} to ${target}` } };
    this.setState(prevState => ({
      edges: [...prevState.edges, newEdge]
    }));
  };

  handleSaveButton = () => {
    console.log('Nodes:', this.state.nodes);
    console.log('Edges:', this.state.edges);
  };

  render(){
    const elements = [...this.state.nodes, ...this.state.edges];
    const layout = { name: 'preset' };

    return (
      <div>
        <button onClick={this.handleAddNode}>Add Node</button>
        <button onClick={this.handleUpdateNode}>Update Node</button>
        <button onClick={this.handleRemoveNode}>Remove Node</button>
        <button onClick={this.handleConnectNodes}>Connect Nodes</button>
        <button onClick={this.handleSaveButton}>Save</button>
        <CytoscapeComponent elements={elements} style={{ width: '1200px', height: '900px' }} layout={layout} />
      </div>
    );
  }
}

export default App;
