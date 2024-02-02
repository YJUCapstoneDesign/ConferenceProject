package team.broadcast.domain.janus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JanusRequestDTO {
  private String janus;
  private String plugin;
  private String Transaction;
  private String adminSecret;
  private Object request;

  /**
   *
   * janus - janus 요청 구분
   * plugin - 어떤 plugin 인지에 대한 요청 구분
   * transaction - 요청에 대한 구분을 위한 랜덤한 것
   * request - 요청을 위한 payload
   */
  public static JanusRequestDTO create(String transaction, String adminSecret, Object request) {
    return new JanusRequestDTO("message_plugin", "janus.plugin.videoroom", transaction, adminSecret, request);
  }
}
