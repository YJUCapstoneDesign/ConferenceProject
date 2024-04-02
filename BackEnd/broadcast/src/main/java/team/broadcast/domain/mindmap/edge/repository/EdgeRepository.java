package team.broadcast.domain.mindmap.edge.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import team.broadcast.domain.mindmap.edge.entity.Edge;

@Repository
public interface EdgeRepository extends MongoRepository<Edge, String> {
}
