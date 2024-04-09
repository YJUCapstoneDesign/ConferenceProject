package team.broadcast.domain.video_room.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import team.broadcast.domain.attender.dto.AttenderDTO;
import team.broadcast.domain.user.entity.User;
import team.broadcast.domain.user.exception.UserErrorCode;
import team.broadcast.domain.user.mysql.repository.UserRepository;
import team.broadcast.global.exception.CustomException;
import team.broadcast.global.mail.MailUtil;
import team.broadcast.global.mail.dto.EmailMessage;

@Service
@RequiredArgsConstructor
public class InvitationAttenderService {
    private final MailUtil mailUtil;
    private final TemplateEngine templateEngine;
    private final UserRepository userRepository;


    public void sendInviteMail(AttenderDTO attender, String invitationCode) {

        User user = userRepository.findById(attender.getUserId())
                .orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND));

        Context context = new Context();
        context.setVariable("name", user.getName());
        context.setVariable("invitationCode", invitationCode);

        String message = templateEngine.process("InviteMail", context);

        EmailMessage emailMessage = EmailMessage.builder()
                .to(user.getEmail())
                .subject("[Unmute] 화상회의 초대 코드")
                .message(message)
                .build();

        mailUtil.sendMail(emailMessage);
    }

}
