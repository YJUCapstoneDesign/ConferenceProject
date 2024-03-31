import React from 'react';
import CytoscapeComponent from 'react-cytoscapejs';
import './style.css';

class CytoscapeComponentWrapper extends React.Component {
    render() {
        const { elements, layout } = this.props;
        return (
            <CytoscapeComponent
                elements={elements}
                style={{ width: '1200px', height: '900px' }}
                layout={layout}
                cy={(cy) => {
                    this.cy = cy;
                }}
            />
        );
    }
}

export default CytoscapeComponentWrapper;
