package hello.oauth2demo.global.config.dto;

import hello.oauth2demo.domain.user.User;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class SessionUser implements Serializable {
    // 인증된 사용자 정보만 필요 => name, email, picture 필드만 선언
    private String name;
    private String email;
    private String picture;

    public SessionUser(User user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.picture = user.getPicture();
    }

    @Override
    public String toString() {
        return "SessionUser{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
