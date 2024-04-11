package team.broadcast.domain.video_room.repository;

import org.springframework.stereotype.Repository;
import team.broadcast.domain.video_room.dto.VideoRoom;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class VideoRoomRepository {

    private final Map<Long, VideoRoom> videoRooms = new ConcurrentHashMap<>();

    public VideoRoom save(VideoRoom videoRoom) {
        videoRooms.put(videoRoom.getRoomId(), videoRoom);
        return videoRoom;
    }

    public VideoRoom update(VideoRoom updatedRoom) {
        VideoRoom oldRoom = videoRooms.get(updatedRoom.getRoomId());
        oldRoom.setRoomName(updatedRoom.getRoomName());
        videoRooms.put(oldRoom.getRoomId(), oldRoom);
        return oldRoom;
    }

    public VideoRoom findById(Long roomId) {
        return videoRooms.get(roomId);
    }

    public List<VideoRoom> findAll() {
        return new ArrayList<>(videoRooms.values());
    }

    public void delete(Long roomId) {
        videoRooms.remove(roomId);

    }
}
