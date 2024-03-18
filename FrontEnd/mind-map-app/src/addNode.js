const axios = require('axios');

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
    ];

    axios.post('http://localhost:8080/api/nodes', node)
        .then((response) => {
            console.log(response.data);
        })
        .catch((error) => {
            console.error(error);
        });
}

const addButton = document.getElementsByClassName('add');

addButton.addEventListener('click', addNode);
