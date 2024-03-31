import React from 'react';

class NodeComponent extends React.Component {
    render() {
        return (
            <div>
                <button onClick={this.props.onAddNode}>노드 추가</button>
                <button onClick={this.props.onUpdateNode}>노드 수정</button>
                <button onClick={this.props.onRemoveNode}>노드 제거</button>
            </div>
        );
    }
}

export default NodeComponent;
