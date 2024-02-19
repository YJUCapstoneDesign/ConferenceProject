package team.broadcast.domain.enums;

public enum ChatUserRole {
  USER(0), // 일반 사용자면 0
  HOST(1); // 호스트인 경우 1

  private final int value;

  ChatUserRole(int value) {
    this.value = value;
  }

  public int getValue() {
    return value;
  }
}
