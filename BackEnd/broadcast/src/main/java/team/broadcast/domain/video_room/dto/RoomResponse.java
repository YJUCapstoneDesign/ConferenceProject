package team.broadcast.domain.video_room.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import team.broadcast.domain.attender.dto.AttenderDTO;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoomResponse {
    private String name;
    private int currentCount;
    private int maxCount;
    private List<AttenderDTO> participants;
}
