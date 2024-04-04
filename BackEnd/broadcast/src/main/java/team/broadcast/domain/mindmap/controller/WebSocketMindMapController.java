package team.broadcast.domain.mindmap.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import team.broadcast.domain.mindmap.dto.UpdateMindMapDto;

@Slf4j
@Controller
public class WebSocketMindMapController {

    // 실시간으로 클라이언트에 작업하는 코드가 전달이 된다.
    @MessageMapping("/ws/mind-map")
    @SendTo("/topic/update")
    public UpdateMindMapDto update(UpdateMindMapDto updateMindMap) {
        return updateMindMap;
    }
}
