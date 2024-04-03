package team.broadcast.domain.mindmap.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import team.broadcast.domain.mindmap.dto.MindMapDto;

@Slf4j
@RestController
public class WebSocketMindMapController {
    @MessageMapping("/ws")
    @SendTo("/topic/mind-map")
    public String update(MindMapDto dto) {
        log.info("update MindMap={}", dto);
        return "ok";
    }
}
