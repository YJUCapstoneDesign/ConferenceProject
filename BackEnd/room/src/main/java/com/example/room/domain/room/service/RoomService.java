package com.example.room.domain.room.service;

import com.example.room.domain.room.dto.RoomDTO;
import com.example.room.domain.room.repository.RoomRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
public class RoomService {

    private final RoomRepository roomRepository;

    @PostConstruct
    private void init() {
        roomRepository.clear(); // 배열 초기화
    }

    //    @Autowired
    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    // 이름을 통한 방 생성
    public RoomDTO creatRoom(String roomName) {
        RoomDTO room = new RoomDTO().create(roomName);
        roomRepository.save(room);
        return room;
    }

    // 모든 방 조회
    public List<RoomDTO> findAllRooms() {
        List<RoomDTO> rooms = roomRepository.findAll();
        Collections.reverse(rooms); // 채팅방 생성 순서를 최근 순으로 변경
        return rooms;
    }

    public RoomDTO findRoomById(String roomId) {
        return roomRepository.findById(roomId);
    }

    public void plusUserCnt(String roomId) {
        RoomDTO room = findRoomById(roomId);
        room.setUserCount(room.getUserCount() + 1);
        roomRepository.save(room);
    }

    public void minusUserCnt(String roomId) {
        RoomDTO room = findRoomById(roomId);
        room.setUserCount(room.getUserCount() - 1);
        roomRepository.save(room);
    }

    // 채팅방 유저 리스트에 유저 추가
    public String addUser(String roomId, String username) {
        RoomDTO room = findRoomById(roomId);
        String userId = UUID.randomUUID().toString();

        // 아이디 중복 확인 후 추가
        room.getUserList().put(userId, username);
        return userId;
    }

    // 유저 이름 중복 확인
    public String isDuplicateName(String roomId, String username) {
        RoomDTO room = findRoomById(roomId);
        String finalUsername = username;

        // 만약 userName 이 중복이라면 랜덤한 숫자를 붙임
        // 이때 랜덤한 숫자를 붙였을 때 getUserList 안에 있는 닉네임이라면 다시 랜덤한 숫자 붙이기!
        while (room.getUserList().containsValue(username)) {
            int ranNum = (int) (Math.random() * 100) + 1;

            finalUsername = username + ranNum;
        }
        return finalUsername;
    }

    // 채팅방 유저 리스트 삭제
    public void delUser(String roomId, String userId) {
        RoomDTO room = findRoomById(roomId);
        room.getUserList().remove(userId);
    }

    // 채팅방 username 조회
    public String getUsername(String roomId, String userId) {
        RoomDTO room = findRoomById(roomId);
        return room.getUserList().get(userId);
    }

    // 채팅방 전체 userList 조회
    public List<String> getUserList(String roomId) {
        RoomDTO room = findRoomById(roomId);
        return new ArrayList<>(room.getUserList().values());
    }


}
