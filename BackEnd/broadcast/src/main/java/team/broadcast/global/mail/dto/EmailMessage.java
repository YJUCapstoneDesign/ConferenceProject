package team.broadcast.global.mail.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmailMessage {
    //수신자
    private String to;
    //제목
    private String subject;
}
