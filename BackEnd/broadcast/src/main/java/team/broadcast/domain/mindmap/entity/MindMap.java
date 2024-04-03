package team.broadcast.domain.mindmap.entity;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "mindmaps")
public class MindMap {
    @Id
    private String id;

    private Long roomId;

    private Map<String, Object> data;
}
