package work.lince.project.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import work.lince.commons.authentication.AuthenticationService;
import work.lince.project.model.Email;
import work.lince.project.model.EmailFormat;
import work.lince.project.model.EmailStatus;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.UUID;

@Slf4j
@Service
public class EmailService {

    @Autowired
    protected AuthenticationService authenticationService;

    @Autowired
    protected DateService dateService;

    @Autowired
    public JavaMailSender emailSender;

    public Email create(Email email) {
        email.setId(UUID.randomUUID().toString());
        email.setOwner(authenticationService.getAuthenticatedUser());
        email.setCreate(dateService.offsetDateTimeNow());
        email.setStatus(EmailStatus.READY_TO_SEND);
        if (email.getType() == null) {
            email.setType(EmailFormat.TEXT);
        }
        return save(send(email));
    }

    protected Email save(Email email) {
        //TODO: save on kvs
        return email;
    }

    protected Email send(Email email) {
        if (email.getType() == EmailFormat.HTML) {
            sendHtmlMessage(email);
        } else {
            sendSimpleMessage(email);
        }
        return email;
    }

    public void sendSimpleMessage(Email email) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email.getTo().toArray(new String[email.getTo().size()]));
        message.setSubject(email.getSubject());
        message.setText(email.getBody());
        emailSender.send(message);
        email.setStatus(EmailStatus.SENT);
    }

    public void sendHtmlMessage(Email email) {

        MimeMessage mimeMessage = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
        try {
            helper.setText(email.getBody(), true); // Use this or above line.
            helper.setTo(email.getTo().toArray(new String[email.getTo().size()]));
            helper.setSubject(email.getSubject());
            emailSender.send(mimeMessage);
            email.setStatus(EmailStatus.SENT);
        } catch (MessagingException e) {
            email.setStatus(EmailStatus.SEND_ERROR);
        }

    }
}