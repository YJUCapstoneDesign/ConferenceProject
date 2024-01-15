package com.example.room.domain.room.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter
@Setter
@ToString
public class RoomDTO {
    private String roomId; // 채팅방 아이디
    private String roomName; //채팅방 이름
    private long userCount; // 채팅방 인원수

    private Map<String, String> userList = new HashMap<>();

    public RoomDTO create(String roomName) {
        RoomDTO room = new RoomDTO();
        room.roomId = UUID.randomUUID().toString(); //랜덤으로 room id 생성
        room.roomName = roomName;
        return room;
    }

}
