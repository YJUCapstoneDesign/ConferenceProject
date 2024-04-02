package team.broadcast.domain.mindmap.node.entity;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collation = "nodes")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class Node {
    @Id
    private String id;

    private String label;
}
