package team.broadcast.domain.meeting.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MeetingDTO {
    private String name;
    private LocalDateTime createTime;
    private LocalDateTime endTime;
}
