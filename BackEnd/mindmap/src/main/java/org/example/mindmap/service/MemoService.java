package org.example.mindmap.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.example.mindmap.entity.Memo;
import org.example.mindmap.repository.MemoRepository;

import java.util.List;
import java.util.Optional;

@Service
public class MemoService {

    @Autowired
    private MemoRepository memoRepository;

    // 새로운 메모를 저장합니다.
    public Memo saveMemo(Memo memo) {
        return memoRepository.save(memo);
    }

    // 모든 메모를 조회합니다.
    public List<Memo> getAllMemos() {
        return memoRepository.findAll();
    }

    // ID에 해당하는 메모를 조회합니다.
    public Optional<Memo> getMemoById(Long id) {
        return memoRepository.findById(id);
    }

    // ID에 해당하는 메모를 삭제합니다.
    public void deleteMemoById(Long id) {
        memoRepository.deleteById(id);
    }

    // 기타 메서드들...
}
