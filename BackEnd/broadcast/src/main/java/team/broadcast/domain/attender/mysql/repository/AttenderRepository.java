package team.broadcast.domain.attender.mysql.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import team.broadcast.domain.attender.entity.Attender;

import java.util.List;
import java.util.Optional;

@Repository
public interface AttenderRepository extends JpaRepository<Attender, Long> {
    Optional<Attender> findById(Long id);

    List<Attender> findByMeetingId(Long meetingId);

    List<Attender> findByUserId(Long userId);

    @Query("SELECT a FROM Attender a WHERE a.user.id = :userId AND a.meeting.id = :meetingId")
    Optional<Attender> findByUserIdAndMeetingId(Long userId, Long meetingId);
}
