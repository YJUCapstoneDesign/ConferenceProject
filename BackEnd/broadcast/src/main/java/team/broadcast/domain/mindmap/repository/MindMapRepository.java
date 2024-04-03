package team.broadcast.domain.mindmap.repository;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import team.broadcast.domain.mindmap.entity.MindMap;

@Repository
public interface MindMapRepository extends MongoRepository<MindMap, String> {
}
