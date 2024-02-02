package team.broadcast.domain.enums;

public enum UserRole {
  USER(0), // 일반 사용자면 0
  HOST(1); // 호스트인 경우 1

  private final int value;

  UserRole(int value) {
    this.value = value;
  }

  public int getValue() {
    return value;
  }
}
