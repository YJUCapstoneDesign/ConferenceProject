package team.broadcast.domain.mindmap.node.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import team.broadcast.domain.mindmap.node.entity.Node;

@Repository
public interface NodeRepository extends MongoRepository<Node, String> {
}
