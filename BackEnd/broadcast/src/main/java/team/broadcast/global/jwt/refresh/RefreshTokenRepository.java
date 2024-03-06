package team.broadcast.global.jwt.refresh;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    public Optional<RefreshToken> findByUserId(Long userId);

    public Optional<RefreshToken> findByToken(String token);
}
