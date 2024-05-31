package team.broadcast.domain.team.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TeamUpdateRequest {
    private String oldPwd;
    private String newPwd;
}
