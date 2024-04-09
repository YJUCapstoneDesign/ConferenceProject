package team.broadcast.domain.attender.mysql.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import team.broadcast.domain.attender.entity.Attender;

import java.util.List;
import java.util.Optional;

@Repository
public interface AttenderRepository extends JpaRepository<Attender, Long> {
    Optional<Attender> findById(Long id);

    List<Attender> findByMeetingId(Long meetingId);

    List<Attender> findByUserId(Long userId);
}
