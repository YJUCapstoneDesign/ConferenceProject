import React from 'react';

class SaveComponent extends React.Component {
    render() {
        return (
            <div>
                <button onClick={this.props.onSaveButton}>저장</button>
            </div>
        );
    }
}

export default SaveComponent;
