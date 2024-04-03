package team.broadcast.domain.user.mysql.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.broadcast.domain.user.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    Optional<User> findById(Long id);
}
