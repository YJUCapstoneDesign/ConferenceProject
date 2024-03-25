import React from 'react';
import CytoscapeComponent from 'react-cytoscapejs';
import './style.css';

class App extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      nodes: [],
      edges: []
    };
  }

// 노드 추가
handleAddNode = () => {
  const label = prompt('Enter label for new node (한글 10글자, 영어 15글자):');
  if (!label || !/^[a-zA-Z가-힣0-9\s]*$/.test(label)) {
    alert('Invalid node label.');
    return;
  }
  let maxCharacters = /[a-zA-Z]/.test(label) ? 15 : 10;
  if (label.length > maxCharacters) {
    alert(`Node label should be maximum ${maxCharacters} characters long.`);
    return;
  }
  const id = label.toLowerCase().replace(/\s+/g, '-');
  const newNode = { data: { id: id, label: label }, position: { x: 300, y: 300 } };
  this.setState(prevState => ({
    nodes: [...prevState.nodes, newNode]
  }));
};

// 노드 업데이트
handleUpdateNode = () => {
  const label = prompt('Enter label for node to edit (한글 10글자, 영어 15글자):');
  const newLabel = prompt('Enter new label for node (한글 10글자, 영어 15글자):');
  if (!label || !newLabel || !/^[a-zA-Z가-힣0-9\s]*$/.test(newLabel)) {
    alert('Invalid node label.');
    return;
  }
  let maxCharacters = /[a-zA-Z]/.test(newLabel) ? 15 : 10;
  if (newLabel.length > maxCharacters) {
    alert(`Node label should be maximum ${maxCharacters} characters long.`);
    return;
  }
  this.setState(prevState => ({
    nodes: prevState.nodes.map(node => {
      if (node.data.label === label) {
        return { ...node, data: { ...node.data, label: newLabel } };
      }
      return node;
    })
  }));
};


  // 노드 연결
  handleConnectNodes = () => {
    const source = prompt('Enter source node label');
    const target = prompt('Enter target node label');

    if (!source || !target || !/^[a-zA-Z가-힣0-9\s]*$/.test(source) || !/^[a-zA-Z가-힣0-9\s]*$/.test(target)) {
      alert('유효하지 않은 노드 레이블입니다.');
      return;
    }

    if (this.state.edges.some(edge => (edge.data.source === source && edge.data.target === target) || (edge.data.source === target && edge.data.target === source))) {
      alert('이미 연결된 노드입니다.');
      return;
    }

    const newEdge = { data: { id: `edge${this.state.edges.length + 1}`, source: source, target: target, label: `Edge from ${source} to ${target}` } };
    this.setState(prevState => ({
      edges: [...prevState.edges, newEdge]
    }));
  };

  // 노드 연결 해제
  handleDisconnectNodes = () => {
    const source = prompt('Enter source node label');
    const target = prompt('Enter target node label');

    if (!source || !target || !/^[a-zA-Z가-힣0-9\s]*$/.test(source) || !/^[a-zA-Z가-힣0-9\s]*$/.test(target)) {
      alert('유효하지 않은 노드 레이블입니다.');
      return;
    }

    this.setState(prevState => ({
      edges: prevState.edges.filter(edge => (edge.data.source !== source || edge.data.target !== target) && (edge.data.source !== target || edge.data.target !== source))
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
        <button onClick={this.handleDisconnectNodes}>Disconnect Nodes</button>
        <button onClick={this.handleSaveButton}>Save</button>
        <CytoscapeComponent elements={elements} style={{ width: '1200px', height: '900px' }} layout={layout} />
      </div>
    );
  }
}

export default App;
