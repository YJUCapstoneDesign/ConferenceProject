package team.broadcast.domain.mindmap.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team.broadcast.domain.mindmap.edge.entity.Edge;
import team.broadcast.domain.mindmap.edge.repository.EdgeRepository;
import team.broadcast.domain.mindmap.node.entity.Node;
import team.broadcast.domain.mindmap.node.repository.NodeRepository;

@Service
@RequiredArgsConstructor
public class MindMapService {
    private final EdgeRepository edgeRepository;
    private final NodeRepository nodeRepository;

    // TODO: 마인드맵 추가 수정 삭제 코드 만들기
    @Transactional
    public String testCode() {
        Node node = new Node("1", "test");
        Edge edge = new Edge("1", "1", "2");
        nodeRepository.save(node);
        edgeRepository.save(edge);
        return "stored";
    }
}
