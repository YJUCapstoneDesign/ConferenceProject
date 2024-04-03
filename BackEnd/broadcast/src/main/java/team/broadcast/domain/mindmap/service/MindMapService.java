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
    public String save(Long roomId, MindMapDto request) {
        request.setRoomId(roomId);
        MindMap mindMap = from(request);
        MindMap savedMindMap = mindMapRepository.save(mindMap);
        return savedMindMap.getId();
    }

    public MindMap from(MindMapDto dto) {
        return MindMap.builder()
                .data(dto.getData())
                .roomId(dto.getRoomId())
                .build();
    }
}
