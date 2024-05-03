package team.broadcast.domain.mindmap.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@RequestMapping("/api/mind-map")
@Tag(name = "마인드맵 API", description = "화상회의 중에서 다함께 사용할 수 있는 마인드맵")
public class MindMapController {
    private final MindMapService mindMapService;

    @PostMapping("/save")
    @Operation(summary = "마인드맵 저장", description = "최종 결과물을 저장합니다.")
    public ResponseEntity<String> save(@RequestBody MindMapDto request) {
        if (request == null || request.getData().isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        log.info("MindMap request={}", request);

        String mindMapId = mindMapService.save(request);

        log.info("MindMap response={}", mindMapId);
        return new ResponseEntity<>(mindMapId, HttpStatus.OK);
    }

    @GetMapping("/load/{id}")
    @Operation(summary = "마인드맵 불러오기", description = "아이디를 통해 마인드맵을 불러옵니다.")
    public ResponseEntity<MindMapDto> load(@PathVariable("id") String id) {
        MindMapDto dto = mindMapService.findById(id);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
}
