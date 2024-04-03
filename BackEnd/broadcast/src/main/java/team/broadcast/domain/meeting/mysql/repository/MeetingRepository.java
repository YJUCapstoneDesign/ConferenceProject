package team.broadcast.domain.meeting.mysql.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import team.broadcast.domain.meeting.entity.Meeting;

import java.util.List;
import java.util.Optional;

@Repository
public interface MeetingRepository extends JpaRepository<Meeting, Long> {
    Optional<Meeting> findById(Long id);

    Optional<Meeting> findByName(String name);

    List<Meeting> findAll();
}
