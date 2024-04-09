package team.broadcast.domain.meeting.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MeetingUpdateRequest {
    private String name;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
