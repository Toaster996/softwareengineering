package de.dhbw.softwareengineering.digitaljournal.business;

import de.dhbw.softwareengineering.digitaljournal.domain.ChangeMailRequest;
import de.dhbw.softwareengineering.digitaljournal.domain.ContactRequest;
import de.dhbw.softwareengineering.digitaljournal.domain.DeleteAccountRequest;
import de.dhbw.softwareengineering.digitaljournal.domain.PasswordRecoveryRequest;
import de.dhbw.softwareengineering.digitaljournal.domain.RegistrationRequest;
import de.dhbw.softwareengineering.digitaljournal.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import static de.dhbw.softwareengineering.digitaljournal.util.Constants.BASE_URL;
import static de.dhbw.softwareengineering.digitaljournal.util.Constants.SUPPORT_RECIPIENT;

@Slf4j
@Component
public class EmailService extends AbstractService{

    @Value("${spring.mail.username}")
    private String senderAddress;

    private final JavaMailSender emailSender;
    private final ContactRequestService contactRequestService;

    @Autowired
    public EmailService(JavaMailSender emailSender, ContactRequestService contactRequestService) {
        this.emailSender = emailSender;
        this.contactRequestService = contactRequestService;
    }

    @Async
    public void sendRegistrationMail(User user, RegistrationRequest request) {
        log.info("Sending registration mail to " + user.getEmail());

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getEmail());
        message.setSubject("Digital Journal | Registration");
        message.setText("Dear " + user.getUsername() + ",\n\nTo activate your account click on the following link: "+BASE_URL + "/confirmemail/" + request.getRegistrationUUID());
        message.setFrom(senderAddress);

        emailSender.send(message);
    }

    @Async
    public void sendPasswordRecoveryMail(User user, PasswordRecoveryRequest request) {
        log.info("Sending password recovery mail to " + user.getEmail());

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getEmail());
        message.setSubject("Digital Journal | Change your password");
        message.setText("Dear " + user.getUsername() + ",\n\nTo reset your password click on the following link: "+BASE_URL + "/recover/" + request.getRecoveryUUID()+"\n\nIf you have not requested a recovery ignore this email.");
        message.setFrom(senderAddress);

        emailSender.send(message);
    }

    @Async
    public void sendSupportMail(ContactRequest contactRequest) {
        log.info("Sending contact request mail by " + contactRequest.getEmail() + " to " + SUPPORT_RECIPIENT);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(SUPPORT_RECIPIENT);
        message.setSubject("Digital Journal | Contact Request from " + contactRequest.getName());
        message.setText("New contact request,\n\nEmail: " + contactRequest.getEmail() + "\n\nName: " + contactRequest.getName() + "\n\nMessage: " + contactRequest.getMessage());
        message.setFrom(senderAddress);

        try {
            emailSender.send(message);
            contactRequestService.solve(contactRequest);
        }catch (MailException e){
            log.error(e.getMessage());
        }
    }

    @Async
    public void sendDeleteAccountMail(User user, DeleteAccountRequest request) {
        log.info("Sending delete account request to " + user.getEmail());

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getEmail());
        message.setSubject("Digital Journal | Delete your account");
        message.setText("Dear " + user.getUsername() + ",\n\nWe are sad to see you go :-(\n\nBy clicking to the following link you can delete your account and everything associated with it: "+BASE_URL + "/profile/delete/" + request.getRequestid()+"\n\nIf you have not requested a deletion ignore this email.");
        message.setFrom(senderAddress);

        emailSender.send(message);
    }

    @Async
    public void sendMailChangeMail(User user, ChangeMailRequest request) {
        log.info("Sending change mail requests to " + user.getEmail() + " and " + request.getNewmail());

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getEmail());
        message.setSubject("Digital Journal | Change your email");
        message.setText("Dear " + user.getUsername() + ",\n\nPlease click the following link to change your email address to ["+request.getNewmail()+"] : "+BASE_URL + "/profile/mail/confirm/" + request.getOldmailid()+"\n\nIf you have not requested a change ignore this email.");
        message.setFrom(senderAddress);

        emailSender.send(message);

        message = new SimpleMailMessage();
        message.setTo(request.getNewmail());
        message.setSubject("Digital Journal | Change your email");
        message.setText("Dear " + user.getUsername() + ",\n\nPlease click the following link to change your email address from ["+user.getEmail()+"] to this one: "+BASE_URL + "/profile/mail/confirm/" + request.getNewmailid()+"\n\nIf you have not requested a change ignore this email.");
        message.setFrom(senderAddress);

        emailSender.send(message);
    }
}
