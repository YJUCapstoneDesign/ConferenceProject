import React from 'react';

class EdgeComponent extends React.Component {
    render() {
        return (
            <div>
                <button onClick={this.props.onConnectNodes}>노드 연결</button>
                <button onClick={this.props.onDisconnectNodes}>노드 연결 해제</button>
            </div>
        );
    }
}

export default EdgeComponent;
