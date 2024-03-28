package team.broadcast.domain.enumstore.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import team.broadcast.domain.enumstore.mapper.EnumMapperType;

@Getter
@AllArgsConstructor
public enum UserRole implements EnumMapperType {
    USER("ROLE_USER"),
    ADMIN("ROLE_ADMIN");

    private final String value;

    @Override
    public String getCode() {
        return name();
    }
}
