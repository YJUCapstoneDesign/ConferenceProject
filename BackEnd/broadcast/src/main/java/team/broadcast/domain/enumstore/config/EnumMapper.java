package team.broadcast.domain.enumstore.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import team.broadcast.domain.enumstore.enums.TeamRole;
import team.broadcast.domain.enumstore.enums.UserRole;
import team.broadcast.domain.enumstore.mapper.EnumMapperFactory;

import java.util.LinkedHashMap;

@Configuration
public class EnumMapper {

    // Factory에 등록하여 한번에 관리한다.
    @Bean
    public EnumMapperFactory createEnumMapperFactory() {
        EnumMapperFactory enumMapperFactory = new EnumMapperFactory(new LinkedHashMap<>());
        enumMapperFactory.put("TeamRole", TeamRole.class);
        enumMapperFactory.put("UserRole", UserRole.class);
        return enumMapperFactory;
    }

}
