package team.broadcast.domain.mindmap.entity;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "mindmaps")
public class MindMap {
    @Id
    private String meetingId; // 회의와 마인드맵은 1:1 매핑으로 생각한다.

    private Map<String, Object> data;

    private LocalDateTime createTime;
}
