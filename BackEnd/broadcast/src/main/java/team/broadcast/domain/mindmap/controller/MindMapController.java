package team.broadcast.domain.mindmap.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team.broadcast.domain.mindmap.dto.MindMapDto;
import team.broadcast.domain.mindmap.service.MindMapService;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/{roomId}/mind-map")
public class MindMapController {
    private final MindMapService mindMapService;

    @PostMapping("/save")
    public ResponseEntity<String> save(@PathVariable Long roomId, @RequestBody MindMapDto request) {
        if (request == null || request.getData().isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        log.info("MindMap request={}", request);
        log.info("MindMap nodes={}", request.getNodes());
        log.info("MindMap edges={}", request.getEdges());
        String mindMapId = mindMapService.save(roomId, request);

        return new ResponseEntity<>(mindMapId, HttpStatus.OK);
    }
}
