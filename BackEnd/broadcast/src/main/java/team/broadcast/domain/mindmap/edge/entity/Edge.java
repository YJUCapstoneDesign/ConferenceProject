package team.broadcast.domain.mindmap.edge.entity;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collation = "edges")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class Edge {
    @Id
    private String id;
    private String source;
    private String target;
}
