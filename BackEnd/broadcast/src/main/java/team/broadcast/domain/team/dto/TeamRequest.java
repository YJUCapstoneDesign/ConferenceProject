package team.broadcast.domain.team.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class TeamRequest {
    private Long teamId;
    private String password;
}
