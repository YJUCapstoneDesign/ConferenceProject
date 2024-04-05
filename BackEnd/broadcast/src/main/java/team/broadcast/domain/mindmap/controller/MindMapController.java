package team.broadcast.domain.mindmap.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;
import team.broadcast.domain.mindmap.dto.MindMapDto;
import team.broadcast.domain.mindmap.service.MindMapService;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/mind-map")
public class MindMapController {
    private final MindMapService mindMapService;

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody MindMapDto request) {
        if (request == null || request.getData().isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        log.info("MindMap request={}", request);

        String mindMapId = mindMapService.save(request);

        log.info("MindMap response={}", mindMapId);
        return new ResponseEntity<>(mindMapId, HttpStatus.OK);
    }
}
