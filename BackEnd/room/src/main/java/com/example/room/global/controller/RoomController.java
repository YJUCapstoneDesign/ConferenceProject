package com.example.room.global.controller;

import com.example.room.domain.room.dto.RoomDTO;
import com.example.room.domain.room.service.RoomService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@Controller
public class RoomController {

    @Autowired
    private RoomService roomService;

    // 채팅방 리스트 화면
    @GetMapping("/")
    public String goChatRoom(Model model) {
        model.addAttribute("list", roomService.findAllRooms());
        log.info("Show all chatList = {}", roomService.findAllRooms());
        return "room-list";

    }

    // 채팅방 생성
    @PostMapping("/chat/create-room")
    public String createRoom(@RequestParam String name, RedirectAttributes redirectAttributes) {
        RoomDTO room = roomService.creatRoom(name);
        log.info("Create Chat Room = {}", room);
        redirectAttributes.addFlashAttribute("roomName", room);

        return "redirect:/"; // 홈 화면으로 리다이렉트

    }

    // 채팅방 입장 화면
    @GetMapping("/chat/room")
    public String roomDetail(Model model, String roomId) {
        log.info("roomId = {}", roomId);
        model.addAttribute("room", roomService.findRoomById(roomId));
        return "chat-room";
    }

}
