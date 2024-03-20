package team.broadcast.domain.janus.dto;

import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import team.broadcast.domain.janus.enums.JanusPlugin;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JanusSession {
    private Long UserId;
    private Long sessionId;
    private Long handleId;
}
