package team.broadcast.domain.user;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/* 아래 코드에 문제가 없는지 확인할 필요가 있음. */
@Data
public class PrincipalDetail implements UserDetails, OAuth2User {

  private User user;
  private Collection<? extends GrantedAuthority> authorities;
  private Map<String, Object> attributes;

  public PrincipalDetail(User user, Collection<? extends GrantedAuthority> authorities) {
    this.user = user;
    this.authorities = authorities;
  }

  public PrincipalDetail(User user, Collection<? extends GrantedAuthority> authorities, Map<String, Object> attributes) {
    this.user = user;
    this.authorities = authorities;
    this.attributes = attributes;
  }

  public Map<String, Object> getUserInfo() {
    Map<String, Object> info = new HashMap<>();
    info.put("name", user.getName());
    info.put("email", user.getEmail());
    info.put("role", user.getAdmin());
    return info;
  }

  @Override
  public String getName() {
    return user.getEmail();
  }

  @Override
  public Map<String, Object> getAttributes() {
    return attributes;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  @Override
  public String getPassword() {
    return user.getPwd();
  }

  @Override
  public String getUsername() {
    return user.getName();
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}