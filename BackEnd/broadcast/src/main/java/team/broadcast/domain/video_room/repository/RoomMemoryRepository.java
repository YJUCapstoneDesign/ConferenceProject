package team.broadcast.domain.video_room.repository;

import org.springframework.stereotype.Repository;
import team.broadcast.domain.video_room.entity.Room;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class RoomMemoryRepository {

    private final Map<Long, Room> videoRooms = new ConcurrentHashMap<>();

    public Room save(Room room) {
        videoRooms.put(room.getId(), room);
        return room;
    }

    public Room update(Room updatedRoom) {
        Room oldRoom = videoRooms.get(updatedRoom.getId());
        videoRooms.put(oldRoom.getId(), oldRoom);
        return oldRoom;
    }

    public Room findById(Long roomId) {
        return videoRooms.get(roomId);
    }

    public List<Room> findAll() {
        return new ArrayList<>(videoRooms.values());
    }

    public void delete(Long roomId) {
        videoRooms.remove(roomId);

    }
}
