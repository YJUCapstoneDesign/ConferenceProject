package team.broadcast.domain.team.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.broadcast.domain.team.entity.Team;

import java.util.Optional;

public interface TeamRepository extends JpaRepository<Team, Long> {
    Optional<Team> findById(Long id);
}
