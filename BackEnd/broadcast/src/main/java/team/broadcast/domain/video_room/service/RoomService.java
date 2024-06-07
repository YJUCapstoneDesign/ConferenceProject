package team.broadcast.domain.video_room.service;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import team.broadcast.domain.janus.exception.JanusError;
import team.broadcast.domain.janus.service.JanusClient;
import team.broadcast.domain.team.exception.TeamErrorCode;
import team.broadcast.domain.video_room.dto.janus.request.VideoRoomCreateRequest;
import team.broadcast.domain.video_room.dto.janus.request.VideoRoomDestroyRequest;
import team.broadcast.domain.video_room.dto.janus.request.VideoRoomExistRequest;
import team.broadcast.domain.video_room.dto.janus.response.VideoRoomResponse;
import team.broadcast.domain.video_room.dto.janus.response.VideoRoomResult;
import team.broadcast.domain.video_room.exception.RoomErrorCode;
import team.broadcast.global.exception.CustomException;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoomService {

    private final JanusClient janusClient;

    // janus webrtc 에러가 있는지 체크한다.
    private VideoRoomResponse checkExceptionResponse(Mono<VideoRoomResponse> responseMono) throws Exception {
        VideoRoomResponse response = responseMono.block();

        if (response == null) {
            throw new IllegalAccessException("Response Not Found");
        }

        if (response.isError()) {
            throw new JanusError(response.getError());
        }

        if (response.isPluginError()) {
            throw new JanusError(response.getResponse().getError_code(), response.getResponse().getError());
        }

        return response;
    }


    // 1. 비디오 생성
    @Transactional
    public Long createRoom(VideoRoomCreateRequest request) throws Exception {

        Mono<VideoRoomResponse> send = janusClient.send(request, VideoRoomResponse.class);

        VideoRoomResponse block = checkExceptionResponse(send);
        VideoRoomResult response = block.getResponse();

        return response.getRoom();
    }

    // 2. 비디오 삭제
    @Transactional
    public void destroyRoom(VideoRoomDestroyRequest request) throws Exception {

        Mono<VideoRoomResponse> send = janusClient.send(request, VideoRoomResponse.class);

        // 오류가 없는지 검사하는 로직
        checkExceptionResponse(send);
    }

    // 방이 있는지 확인하는 코드
    public Long existRoom(VideoRoomExistRequest request) throws Exception {
        Mono<VideoRoomResponse> send = janusClient.send(request, VideoRoomResponse.class);

        VideoRoomResponse block = checkExceptionResponse(send);
        VideoRoomResult response = block.getResponse();

        // 방이 없으면 null 반환
        if (!response.isExists()) {
            throw new CustomException(RoomErrorCode.ROOM_NOT_FOUND);
        }

        return response.getRoom();
    }
}
