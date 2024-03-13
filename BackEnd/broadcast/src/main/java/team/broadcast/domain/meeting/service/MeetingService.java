package team.broadcast.domain.meeting.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import team.broadcast.domain.video_room.service.VideoRoomService;

@Slf4j
@Service
@RequiredArgsConstructor
public class MeetingService {
    private final VideoRoomService videoRoomService;
    // 아래는 비어둠
}
