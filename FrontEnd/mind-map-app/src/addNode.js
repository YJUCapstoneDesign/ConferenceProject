function addNode() {
    const label = prompt('추가할 노드의 이름을 입력하세요.');
    const url = prompt('추가할 노드의 URL을 입력하세요.');
    const id = label;

    const node = [
        {
            data: {
                id,
                url,
                label
            },
        }
        
    ]

    fetch('http://localhost:3001/data', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(node),
    })
}