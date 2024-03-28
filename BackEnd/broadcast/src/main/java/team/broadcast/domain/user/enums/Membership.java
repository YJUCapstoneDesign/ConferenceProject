package team.broadcast.domain.user.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Membership {
    BASIC("ROLE_BASIC"),
    MEMBER("ROLE_MEMBER");

    private final String value;

}
