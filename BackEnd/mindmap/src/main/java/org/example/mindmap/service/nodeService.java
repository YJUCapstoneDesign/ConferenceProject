package org.example.mindmap.service;

import org.example.mindmap.entity.node;
import org.example.mindmap.repository.nodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class nodeService {

    @Autowired
    private nodeRepository nodeRepository;

    // 새로운 메모를 저장합니다.
    public node saveMemo(node memo) {
        return nodeRepository.save(memo);
    }

    // 모든 메모를 조회합니다.
    public List<node> getAllMemos() {
        return nodeRepository.findAll();
    }

    // ID에 해당하는 메모를 조회합니다.
    public Optional<node> getMemoById(Long id) {
        return nodeRepository.findById(id);
    }

    // ID에 해당하는 메모를 삭제합니다.
    public void deleteMemoById(Long id) {
        nodeRepository.deleteById(id);
    }

    // "node" 값을 처리하는 메서드
    public String processNode(String nodeValue) {
        // 이 부분에 "node" 값을 처리하는 로직을 추가합니다.
        // 예를 들어, "node" 값을 가지고 데이터베이스에서 정보를 조회하거나 다른 작업을 수행할 수 있습니다.
        return "Processed node value: " + nodeValue;
    }

    // 기타 메서드들...
}
