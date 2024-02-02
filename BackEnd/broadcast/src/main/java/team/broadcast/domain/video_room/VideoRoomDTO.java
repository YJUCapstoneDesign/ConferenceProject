package team.broadcast.domain.video_room;


import lombok.*;
import team.broadcast.domain.chatuser.ChatUserDTO;
import team.broadcast.domain.enums.ChatType;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VideoRoomDTO {
  private String roomId; // 룸 아이디
  private String roomName; // 룸 이름

  private boolean secretCheck; // 룸 비밀 번호 설정 여부 (현재는 사용하지 않지만 추후를 위해 남겨놓음)
  private Integer roomPwd; // pin 번호로 비밀번호 설정, 비밀번호가 없는 경우 null로 지정

  private int currentMemberCount; // 현재 채팅방 인원수
  private int maxMemberCount; // 채팅방 최대 인원 수 지정

  private ChatType chatType; // 채팅 타입 여부

  private LocalDateTime startTime; // 방을 생성한 시간
  private LocalDateTime endTime; // 방 종료시간
  private Integer activeMinute; // 방 활성화 가능 시간(분 단위) null 인 경우 무제한

  // 테스트에서 아래 부분 에러 발생!!
//  private Map<Long, ?> chatUserList = new ConcurrentHashMap<>(); // 방안에 소속되어 있는 ChatUser 들의 리스트이다.

}
