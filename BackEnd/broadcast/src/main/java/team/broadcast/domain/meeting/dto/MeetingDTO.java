package team.broadcast.domain.meeting.dto;

import lombok.*;
import team.broadcast.domain.meeting.entity.Meeting;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MeetingDTO {
    private Long id; // Meeting 식별
    private String name;
    private LocalDateTime createTime;
    private LocalDateTime endTime;

    public static MeetingDTO from(Meeting meeting) {
        return MeetingDTO.builder()
                .id(meeting.getId())
                .name(meeting.getName())
                .createTime(meeting.getCreateTime())
                .endTime(meeting.getEndTime())
                .build();
    }
}
