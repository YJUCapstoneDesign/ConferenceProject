package team.broadcast.domain.meeting.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import team.broadcast.domain.user.dto.SignupUser;
import team.broadcast.domain.user.dto.UserResponse;
import team.broadcast.domain.user.entity.User;
import team.broadcast.domain.user.service.UserService;

import java.util.List;

@SpringBootTest
class MeetingServiceTest {
    @Autowired
    private MeetingService meetingService;

    @Autowired
    private UserService userService;

    @Test
    @DisplayName("회의 참석자 리스트 불러오기")
    void findAttenders() {
        Long meetingId = 1L;

//        SignupUser test1 = SignupUser.builder()
//                .username("test1")
//                .email("test@gmail.com")
//                .phone("01044444444")
//                .password("Test123456!")
//                .build();
//
//        SignupUser test2 = SignupUser.builder()
//                .username("test2")
//                .email("test2@gmail.com")
//                .phone("01044444444")
//                .password("Test123456!")
//                .build();
//
//        userService.join(test1);
//        userService.join(test2);

        User user1 = userService.findUser(1L);
        User user2 = userService.findUser(2L);
        // 참석자 추가
        meetingService.addAttender(1L, user1);
        meetingService.addAttender(1L, user2);

        // 현재 참석되어 있는 유저 확인
        List<UserResponse> attenders = meetingService.findAttenders(meetingId);

        Assertions.assertThat(attenders).isNotEmpty(); // 비어 있지 않음
    }
}