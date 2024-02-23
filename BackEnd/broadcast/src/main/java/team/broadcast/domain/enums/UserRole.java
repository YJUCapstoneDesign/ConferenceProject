package team.broadcast.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserRole {
  USER("0"),
  ADMIN("1");

  private final String value;
}
