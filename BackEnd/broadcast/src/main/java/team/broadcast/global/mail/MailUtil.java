package team.broadcast.global.mail;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import team.broadcast.global.mail.dto.EmailMessage;

@Slf4j
@Component
@RequiredArgsConstructor
public class MailUtil {
    private final JavaMailSender mailSender;

    public void sendMail(EmailMessage emailMessage) {
        MimeMessage mimeMailMessage = mailSender.createMimeMessage();

        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMailMessage,
                    false, "UTF-8");

            mimeMessageHelper.setTo(emailMessage.getTo());
            mimeMessageHelper.setSubject(emailMessage.getSubject());
            mimeMessageHelper.setText(emailMessage.getMessage(), true);

            // mail 보내기
            mailSender.send(mimeMailMessage);

            // 메일 메시지 로그 출력
            log.info("Sent email: {}", emailMessage.getMessage());
        } catch (MessagingException e) {
            log.error("Failed to send email", e);
        }
    }
}
