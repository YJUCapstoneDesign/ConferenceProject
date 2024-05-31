package team.broadcast.domain.video_room.entity;

import lombok.*;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Room {
    // 방 아이디 == teamId가 된다.
    private Long id;
    // 방 이름
    private String name;

    private List<String> userList;
    // 현재 참석한 사람의 수
    @Setter
    private int currentCount;

    // 최대 참석자 수
    @Builder.Default
    private final int MaxCount = 6;

    public void addUser(String username) {
        userList.add(username);
    }

    public void removeUser(String username) {
        userList.remove(username);
    }

}
