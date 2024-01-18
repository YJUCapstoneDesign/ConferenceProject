package hello.oauth2demo.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    // 스프링 시큐리티에서는 권한 코드에 항상 ROLE_이 있어야 한다.
    // DB 저장시 ADMIN, USER로 ENUM으로 들어간다.
    ADMIN("ROLE_ADMIN", "관리자"),
    USER("ROLE_USER", "사용자");

    private final String key;
    private final String title;
}
