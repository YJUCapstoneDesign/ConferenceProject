package team.broadcast.domain.video_room.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import team.broadcast.domain.attender.entity.Attender;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Room {
    private Long id;
    private String name;

    private int currentCount;
    private int MaxCount = 6;

    private Long meetingId;

    private List<Attender> participants; // 회의 참석자에 대한 정보를 받고 있다.

    public static Long generateRandomRoomId() {
        return Long.valueOf(RandomStringUtils.random(6, false, true));
    }

    public void updateRoomName(String name) {
        this.name = name;
    }

    // 기존 리스트를 수정할 수 없도록 분리를 한다.
    public List<Attender> getParticipants() {
        return List.copyOf(participants);
    }

    public void updateParticipants(List<Attender> attenders) {
        this.participants = attenders;
        this.currentCount = attenders.size();
    }


}
