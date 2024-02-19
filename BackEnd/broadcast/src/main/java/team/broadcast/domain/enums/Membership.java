package team.broadcast.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Membership {
    BASIC("0"), // 무료(기본) 사용자인 경우 0
    MEMBER("1"); // 가입자인 경우 1로 지정

    private final String value;

}
