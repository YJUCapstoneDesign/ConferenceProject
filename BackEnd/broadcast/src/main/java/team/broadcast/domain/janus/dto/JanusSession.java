package team.broadcast.domain.janus.dto;

import lombok.*;
import team.broadcast.domain.janus.enums.JanusPlugin;

import java.util.List;

// 임시로 아래와 같이 테스트 코드 (클라이언트에서 세션을 처리하도록 변경 예정)
//TODO Session id와 handle id를 저장하는 DTO를 어떻게 구성할 지 찾아보고 수정한다.
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JanusSession {
    private Long id;
    private Long UserId;
    private Long sessionId;
    private List<JanusPlugin> pluginTypes;
}
