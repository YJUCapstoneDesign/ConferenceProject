package team.broadcast.domain.enumstore.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import team.broadcast.domain.enumstore.mapper.EnumMapperType;

@Getter
@RequiredArgsConstructor
public enum TeamRole implements EnumMapperType {
    PARTICIPANT("ROLE_PARTICIPANT"),
    HOST("ROLE_HOST");

    private final String value;

    @Override
    public String getCode() {
        return name();
    }
}
