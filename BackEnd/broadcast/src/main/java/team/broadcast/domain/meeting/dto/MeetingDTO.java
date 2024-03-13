package team.broadcast.domain.meeting.dto;

import lombok.*;
import team.broadcast.domain.attender.dto.AttenderDTO;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MeetingDTO {
    private Long id;
    private String name;

    private String secret; // 방 수정이나 삭제시 사용되는 비밀번호
    private String pin; // 방 참여 비밀번호

    private AttenderDTO participants;

    private LocalDateTime createTime;
    private LocalDateTime endTime;
}
