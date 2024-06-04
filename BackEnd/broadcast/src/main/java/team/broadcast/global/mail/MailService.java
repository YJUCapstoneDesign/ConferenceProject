package team.broadcast.global.mail;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import team.broadcast.global.mail.dto.EmailMessage;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailService {
    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;

    public void sendMail(EmailMessage emailMessage, Map<String, String> values) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setTo(emailMessage.getTo());
        helper.setSubject(emailMessage.getSubject());

        // 여기서 변경을 해야 한다. 나를 위한 방법이 있을 것이다. 참고하자.
        Context context = new Context();
        values.forEach(context::setVariable);

        String html = templateEngine.process("resetPassword", context);
        helper.setText(html, true);

        // 메일 보내기
        mailSender.send(message);
    }
}
