package org.example.mindmap.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.example.mindmap.entity.Memo;
import org.springframework.stereotype.Repository;

@Repository
public interface MemoRepository extends JpaRepository<Memo, Long> {
    // 추가적인 메서드 정의가 필요하다면 여기에 추가할 수 있습니다.
}
