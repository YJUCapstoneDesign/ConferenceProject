package team.broadcast.domain.jwt.refresh;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class RefreshToken {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false, unique=true)
    private Long userId;

    @Column(nullable=false)
    private String refreshToken;
}
