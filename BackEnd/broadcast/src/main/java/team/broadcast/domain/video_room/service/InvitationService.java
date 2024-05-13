package team.broadcast.domain.video_room.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import team.broadcast.domain.user.entity.User;
import team.broadcast.global.mail.MailUtil;
import team.broadcast.global.mail.dto.EmailMessage;

@Service
@RequiredArgsConstructor
public class InvitationService {
    private final MailUtil mailUtil;
    private final TemplateEngine templateEngine;


    public void sendInviteMail(User user, String invitationCode) {

        Context context = new Context();
        context.setVariable("name", user.getName());
        context.setVariable("invitationCode", invitationCode);

        String message = templateEngine.process("InviteMail", context);

        EmailMessage emailMessage = EmailMessage.builder()
                .to(user.getEmail())
                .subject("[Unmute] 화상회의 방 초대 코드")
                .message(message)
                .build();

        mailUtil.sendMail(emailMessage);
    }

}
