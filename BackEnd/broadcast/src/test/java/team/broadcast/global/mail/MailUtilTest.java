package team.broadcast.global.mail;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MailUtilTest {

    @Autowired
    private MailUtil mailUtil;
    @Test
    @DisplayName("메일 보내기 테스트")
    void sendMail() {
        mailUtil.sendMail("hisu7789@gmail.com", "test", "test code");
    }
}