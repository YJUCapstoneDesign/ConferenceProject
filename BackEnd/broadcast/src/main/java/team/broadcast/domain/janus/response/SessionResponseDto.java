package team.broadcast.domain.janus.response;

import lombok.*;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class SessionResponseDto {
    private String janus;
    private String transaction;
    private Data data;

    public String getId() {
        return data.getId();
    }

    @lombok.Data
    @AllArgsConstructor
    @NoArgsConstructor
    static class Data {
        private String id;
    }

}
