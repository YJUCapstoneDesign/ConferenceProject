package team.broadcast.domain.mindmap.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MindMapDto {
    private String meetingId;
    private Map<String, Object> data;

    /**
     * @return nodes
     */
    public List<Map<String, Object>> getNodes() {
        return (List<Map<String, Object>>) data.get("nodes");
    }

    /**
     * @return edges
     */
    public List<Map<String, Object>> getEdges() {
        return (List<Map<String, Object>>) data.get("edges");
    }
}
