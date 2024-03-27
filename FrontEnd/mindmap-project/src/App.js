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

  componentDidMount() {
    // Cytoscape.js 인스턴스에 이벤트 리스너를 추가합니다.
    this.cy.on('keydown', this.handleKeyDown);
  }

  handleAddNode = () => {
    const label = prompt('새 노드의 레이블을 입력하세요 (한글 10글자, 영어 15글자):');
    if (!label || !/^[a-zA-Z가-힣0-9\s]*$/.test(label)) {
      alert('유효하지 않은 노드 레이블입니다.');
      return;
    }
    let maxCharacters = /[a-zA-Z]/.test(label) ? 15 : 10;
    if (label.length > maxCharacters) {
      alert(`노드 레이블은 최대 ${maxCharacters}글자여야 합니다.`);
      return;
    }
    const id = label.toLowerCase().replace(/\s+/g, '-');
    const newNode = { data: { id: id, label: label }, position: { x: 300, y: 300 } };
    this.setState(prevState => ({
      nodes: [...prevState.nodes, newNode]
    }));
  };

  handleUpdateNode = () => {
    const label = prompt('편집할 노드의 레이블을 입력하세요 (한글 10글자, 영어 15글자):');
    const newLabel = prompt('노드의 새 레이블을 입력하세요 (한글 10글자, 영어 15글자):');
    if (!label || !newLabel || !/^[a-zA-Z가-힣0-9\s]*$/.test(newLabel)) {
      alert('유효하지 않은 노드 레이블입니다.');
      return;
    }
    let maxCharacters = /[a-zA-Z]/.test(newLabel) ? 15 : 10;
    if (newLabel.length > maxCharacters) {
      alert(`노드 레이블은 최대 ${maxCharacters}글자여야 합니다.`);
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

  handleRemoveNode = () => {
    const label = prompt('삭제할 노드의 레이블을 입력하세요');
    if (!label || !/^[a-zA-Z가-힣0-9\s]*$/.test(label)) {
      alert('유효하지 않은 노드 레이블입니다.');
      return;
    }
    this.setState(prevState => ({
      nodes: prevState.nodes.filter(node => node.data.label !== label),
      edges: prevState.edges.filter(edge => edge.data.source !== label && edge.data.target !== label)
    }));
  };

  handleConnectNodes = () => {
    const source = prompt('소스 노드의 레이블을 입력하세요');
    const target = prompt('대상 노드의 레이블을 입력하세요');

    if (!source || !target || !/^[a-zA-Z가-힣0-9\s]*$/.test(source) || !/^[a-zA-Z가-힣0-9\s]*$/.test(target)) {
      alert('유효하지 않은 노드 레이블입니다.');
      return;
    }

    if (this.state.edges.some(edge => (edge.data.source === source && edge.data.target === target) || (edge.data.source === target && edge.data.target === source))) {
      alert('이미 연결된 노드입니다.');
      return;
    }

    const newEdge = { data: { id: `edge${this.state.edges.length + 1}`, source: source, target: target, label: `${source}에서 ${target}로의 연결` } };
    this.setState(prevState => ({
      edges: [...prevState.edges, newEdge]
    }));
  };

  handleDisconnectNodes = () => {
    const source = prompt('소스 노드의 레이블을 입력하세요');
    const target = prompt('대상 노드의 레이블을 입력하세요');

    if (!source || !target || !/^[a-zA-Z가-힣0-9\s]*$/.test(source) || !/^[a-zA-Z가-힣0-9\s]*$/.test(target)) {
      alert('유효하지 않은 노드 레이블입니다.');
      return;
    }

    this.setState(prevState => ({
      edges: prevState.edges.filter(edge => (edge.data.source !== source || edge.data.target !== target) && (edge.data.source !== target || edge.data.target !== source))
    }));
  };

  handleKeyDown = (e) => {
    if (e.key === 'Delete') {
      const selectedNodes = this.cy.$('node:selected');
      if (selectedNodes.length > 0) {
        const labels = selectedNodes.map(node => node.data('label'));
        this.setState(prevState => ({
          nodes: prevState.nodes.filter(node => !labels.includes(node.data.label)),
          edges: prevState.edges.filter(edge => !labels.includes(edge.data.source) && !labels.includes(edge.data.target))
        }));
      }
    }
  };

  handleSaveButton = () => {
    console.log('노드:', this.state.nodes);
    console.log('엣지:', this.state.edges);
  };

  render(){
    const elements = [...this.state.nodes, ...this.state.edges];
    const layout = { name: 'preset' };

    return (
      <div>
        <button onClick={this.handleAddNode}>노드 추가</button>
        <button onClick={this.handleUpdateNode}>노드 수정</button>
        <button onClick={this.handleRemoveNode}>노드 제거</button>
        <button onClick={this.handleConnectNodes}>노드 연결</button>
        <button onClick={this.handleDisconnectNodes}>노드 연결 해제</button>
        <button onClick={this.handleSaveButton}>저장</button>
        <CytoscapeComponent elements={elements} style={{ width: '1200px', height: '900px' }} layout={layout} cy={(cy) => { this.cy = cy; }} />
      </div>
    );
  }
}

export default App;
