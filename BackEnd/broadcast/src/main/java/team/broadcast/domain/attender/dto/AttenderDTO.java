package team.broadcast.domain.attender.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import team.broadcast.domain.attender.entity.Attender;
import team.broadcast.domain.enumstore.enums.MeetingRole;
import team.broadcast.domain.meeting.entity.Meeting;
import team.broadcast.domain.user.entity.User;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AttenderDTO {
    private Long userId;
    private Long meetingId;
    private MeetingRole role; // 그 사람의 역할을 나타내는 것

    public boolean isHost() {
        return role == MeetingRole.HOST;
    }

    // dto -> entity로 변환화는 메서드
    public Attender from(User user, Meeting meeting) { // 연관관계를 가지는 것은 파라미터로 받게 처리한다.
        return Attender.builder()
                .user(user)
                .meeting(meeting)
                .role(role)
                .build();
    }

    public static AttenderDTO from(Attender attender) {
        return builder()
                .userId(attender.getUser().getId())
                .meetingId(attender.getMeeting().getId())
                .role(attender.getRole())
                .build();
    }
}
