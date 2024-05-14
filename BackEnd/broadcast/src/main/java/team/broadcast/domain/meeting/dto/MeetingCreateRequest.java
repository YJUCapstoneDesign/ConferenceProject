package team.broadcast.domain.meeting.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import team.broadcast.domain.meeting.entity.Meeting;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MeetingCreateRequest {
    private String name;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime startTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime endTime;

    public Meeting from() {
        return Meeting.builder()
                .name(name)
                .createTime(startTime)
                .endTime(endTime)
                .build();
    }
}
