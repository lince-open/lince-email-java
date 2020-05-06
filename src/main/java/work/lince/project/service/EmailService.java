package work.lince.project.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import work.lince.commons.authentication.AuthenticationService;
import work.lince.project.model.Email;
import work.lince.project.model.EmailAttachment;
import work.lince.project.model.EmailFormat;
import work.lince.project.model.EmailStatus;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.mail.util.ByteArrayDataSource;
import java.util.Base64;
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
        sendHtmlMessage(email);
        return email;
    }


    public void sendHtmlMessage(Email email) {

        MimeMessage mimeMessage = emailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "utf-8");
            helper.setText(email.getBody(), email.getType() == EmailFormat.HTML);
            helper.setTo(email.getTo().toArray(new String[email.getTo().size()]));
            helper.setSubject(email.getSubject());
            if (email.getAttachments() != null) {
                email.getAttachments().forEach(attachment -> addAttachment(helper, attachment));
            }
            emailSender.send(mimeMessage);
            email.setStatus(EmailStatus.SENT);
        } catch (MessagingException e) {
            email.setStatus(EmailStatus.SEND_ERROR);
        }

    }

    protected void addAttachment(MimeMessageHelper helper, EmailAttachment attachment) {
        try {
            byte[] bytes = Base64.getDecoder().decode(attachment.getData());
            ByteArrayDataSource ds = new ByteArrayDataSource(bytes, attachment.getType());
            helper.addAttachment(attachment.getName(), ds);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

}