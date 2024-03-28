package team.broadcast.domain.enumstore.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import team.broadcast.domain.enumstore.mapper.EnumMapperType;

@Getter
@AllArgsConstructor
public enum Membership implements EnumMapperType {
    BASIC("ROLE_BASIC"),
    MEMBER("ROLE_MEMBER");

    private final String value;
    @Override
    public String getCode() {
        return name();
    }
}
