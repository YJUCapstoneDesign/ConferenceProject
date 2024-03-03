function addNode() {
    const add = document.querySelector('.add');

    add.addEventListener('click', () => {
        console.log('addNode');
        const label = prompt('추가할 노드의 이름을 입력하세요.');
        const url = prompt('추가할 노드의 URL을 입력하세요.');
        const id = label;

        const newNode = {
            data: {
                id,
                url,
                label,
            },
        };

        nodesData.push(newNode);
        cy.add(newNode);
    });
}

export default addNode;
