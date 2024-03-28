package team.broadcast.domain.enumstore.mapper;

import lombok.Getter;

@Getter
public class EnumMapperValue {
    private String code;
    private String value;

    public EnumMapperValue(EnumMapperType enumType) {
        code = enumType.getCode();
        value = enumType.getValue();
    }
}
