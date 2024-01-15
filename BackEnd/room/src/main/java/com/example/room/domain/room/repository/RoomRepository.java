package com.example.room.domain.room.repository;

import com.example.room.domain.room.dto.RoomDTO;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Repository
public class RoomRepository {
    private Map<String, RoomDTO> store = new LinkedHashMap<>();

    public RoomDTO findById(String roomId) {
        return store.get(roomId);
    }

    public List<RoomDTO> findAll() {
        return new ArrayList<>(store.values());
    }

    public RoomDTO save(RoomDTO room) {
        store.put(room.getRoomId(), room);
        return room;
    }

    public void clear() {
        store.clear();
    }
}
