package com.example.room.global.controller;

import com.example.room.domain.chat.dto.ChatDTO;
import com.example.room.domain.room.service.RoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
public class ChatController {

    private final SimpMessageSendingOperations template;

    @Autowired
    RoomService roomService;


    @MessageMapping("/chat/enterUser")
    public void enterUser(@Payload ChatDTO chat, SimpMessageHeaderAccessor headerAccessor) {

        String roomId = chat.getRoomId();
        // 채팅방 유저 +1
        roomService.plusUserCnt(roomId);

        String userId = roomService.addUser(roomId, chat.getSender());
        headerAccessor.getSessionAttributes().put("userId", userId);
        headerAccessor.getSessionAttributes().put("roomId", roomId);

        chat.setMessage(chat.getSender() + "님 입장!!");
        template.convertAndSend("/sub/chat/room/" + roomId, chat);
    }

    @MessageMapping("/chat/sendMessage")
    public void sendMessage(@Payload ChatDTO chat) {
        log.info("chat={}", chat);
        chat.setMessage(chat.getMessage());
        template.convertAndSend("/sub/chat/room/" + chat.getRoomId(), chat);

    }

    @EventListener
    public void webSocketConnectListener(SessionDisconnectEvent event) {
        log.info("Disconnect event = {}", event);

        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

        String userId = (String) headerAccessor.getSessionAttributes().get("userId");
        String roomId = (String) headerAccessor.getSessionAttributes().get("roomId");

        log.info("headerAccessor = {}", headerAccessor);

        // 채팅방 유저 - 1
        roomService.minusUserCnt(roomId);
        String username = roomService.getUsername(roomId, userId);
        roomService.delUser(roomId, userId);

        if (username != null) {
            log.info("user disconnected : {}", username);

            ChatDTO chat = ChatDTO.builder()
                    .type(ChatDTO.MessageType.LEAVE)
                    .sender(username)
                    .message(username + "님 퇴장!!")
                    .build();
            template.convertAndSend("/sub/chat/room/" + roomId, chat);
        }
    }

    // 채팅에 참여한 유저 리스트 반환
    @GetMapping("/chat/user-list")
    @ResponseBody
    public List<String> userList(String roomId) {
        return roomService.getUserList(roomId);
    }

    @GetMapping("/chat/duplicateName")
    @ResponseBody
    public String isDuplicateName(@RequestParam("roomId") String roomId, @RequestParam("username") String username) {
        String userName = roomService.isDuplicateName(roomId, username);
        log.info("동작확인={}", username);
        return userName;
    }

}
