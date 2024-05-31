package team.broadcast.domain.video_room.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Room {
    private Long id;
    private String name;

    private int currentCount;

    private final int MaxCount = 6;

    private LocalDateTime createTime;

    public static Long generateRandomRoomId() {
        return Long.valueOf(RandomStringUtils.random(6, false, true));
    }
}
