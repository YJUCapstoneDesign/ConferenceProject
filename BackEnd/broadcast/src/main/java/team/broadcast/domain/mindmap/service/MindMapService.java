package team.broadcast.domain.mindmap.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team.broadcast.domain.mindmap.dto.MindMapDto;
import team.broadcast.domain.mindmap.entity.MindMap;
import team.broadcast.domain.mindmap.repository.MindMapRepository;

@Service
@RequiredArgsConstructor
public class MindMapService {

    private final MindMapRepository mindMapRepository;

    @Transactional
    public String save(MindMapDto request) {
        MindMap mindMap = from(request);
        MindMap savedMindMap = mindMapRepository.save(mindMap);
        return savedMindMap.getMeetingId();
    }

    @Transactional
    public MindMapDto findById(String id) {
        MindMap mindMap = mindMapRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("MindMap not found"));

        return of(mindMap);
    }

    public MindMapDto of(MindMap mindMap) {
        return MindMapDto.builder()
                .data(mindMap.getData())
                .meetingId(mindMap.getMeetingId())
                .build();
    }

    public MindMap from(MindMapDto dto) {
        return MindMap.builder()
                .data(dto.getData())
                .meetingId(dto.getMeetingId())
                .build();
    }
}
