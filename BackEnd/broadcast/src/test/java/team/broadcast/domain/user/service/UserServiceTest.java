package team.broadcast.domain.user.service;

import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    @DisplayName("랜덤한 문자열 생성")
    void randomName() {
        int length = 15;
        String name = userService.generateRandomName(length);

        // name length == 15
        Assertions.assertThat(name).hasSize(length);
    }
}