package team.broadcast.domain.attender.dto;

import lombok.*;
import team.broadcast.domain.janus.enums.JanusPlugin;
import team.broadcast.domain.user.dto.UserDto;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AttenderDTO {
    private Long id;
    private Map<Long, UserDto> users;
    private JanusPlugin host; // 그 사람의 역할을 나타내는 것

}
