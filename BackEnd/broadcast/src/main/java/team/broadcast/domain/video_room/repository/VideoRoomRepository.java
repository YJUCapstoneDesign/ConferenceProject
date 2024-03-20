package team.broadcast.domain.video_room.repository;

import org.springframework.stereotype.Repository;
import team.broadcast.domain.video_room.dto.VideoRoomDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class VideoRoomRepository {

    private final Map<Long, VideoRoomDTO> videoRooms = new ConcurrentHashMap<>();

    public VideoRoomDTO save(VideoRoomDTO videoRoom) {
        videoRooms.put(videoRoom.getRoomId(), videoRoom);
        return videoRoom;
    }

    public VideoRoomDTO update(VideoRoomDTO updatedRoom) {
        VideoRoomDTO oldRoom = videoRooms.get(updatedRoom.getRoomId());
        oldRoom.setRoomName(updatedRoom.getRoomName());
        oldRoom.setRoomPwd(updatedRoom.getRoomPwd());
        videoRooms.put(oldRoom.getRoomId(), oldRoom);
        return oldRoom;
    }

    public VideoRoomDTO findById(Long roomId) {
        return videoRooms.get(roomId);
    }

    public List<VideoRoomDTO> findAll() {
        return new ArrayList<>(videoRooms.values());
    }

    public void delete(Long roomId) {
        videoRooms.remove(roomId);

    }
}
