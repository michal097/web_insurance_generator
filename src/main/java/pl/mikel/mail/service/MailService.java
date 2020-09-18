package pl.mikel.mail.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Objects;

@Service
public class MailService {

    private JavaMailSender javaMailSender;

    @Autowired
    public MailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;

    }

    @Async
    public void sendSimpleMail(String to, String subject, String content, Long docNumber) {
        MimeMessage msg = javaMailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(msg, true);

            helper.setTo(to);
            helper.setReplyTo("slusarczyk.michal23@gmail.com");
            helper.setFrom("slusarczyk.michal23@gmail.com");
            helper.setSubject(subject);
            helper.setText(content, true);

            FileSystemResource fileSystemResource = new FileSystemResource("src/main/resources/pdf/insurance_number"+ docNumber +".pdf");
            helper.addAttachment(Objects.requireNonNull(fileSystemResource.getFilename()),fileSystemResource);

        } catch (MessagingException ex) {
            ex.printStackTrace();
        }

        javaMailSender.send(msg);
    }

    public String getEmailAdress(){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();

    }



}


