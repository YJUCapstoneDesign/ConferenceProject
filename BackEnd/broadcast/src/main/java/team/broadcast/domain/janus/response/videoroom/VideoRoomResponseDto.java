package team.broadcast.domain.janus.response.videoroom;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class VideoRoomResponseDto {
    private String janus;
    private String plugin;
    private String transaction;
    private Data data;
}
