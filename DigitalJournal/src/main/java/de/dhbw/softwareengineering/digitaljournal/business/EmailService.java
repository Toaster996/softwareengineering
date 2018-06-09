package de.dhbw.softwareengineering.digitaljournal.business;

import de.dhbw.softwareengineering.digitaljournal.domain.ChangeMailRequest;
import de.dhbw.softwareengineering.digitaljournal.domain.ContactRequest;
import de.dhbw.softwareengineering.digitaljournal.domain.DeleteAccountRequest;
import de.dhbw.softwareengineering.digitaljournal.domain.PasswordRecoveryRequest;
import de.dhbw.softwareengineering.digitaljournal.domain.RegistrationRequest;
import de.dhbw.softwareengineering.digitaljournal.domain.User;
import de.dhbw.softwareengineering.digitaljournal.util.MailContentBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import static de.dhbw.softwareengineering.digitaljournal.util.Constants.BASE_URL;
import static de.dhbw.softwareengineering.digitaljournal.util.Constants.SUPPORT_RECIPIENT;

@Slf4j
@Component
public class EmailService implements AbstractService {

    @Value("${spring.mail.username}")
    private String senderAddress;

    private final JavaMailSender emailSender;
    private final MailContentBuilder mailContentBuilder;
    private final ContactRequestService contactRequestService;

    @Autowired
    public EmailService(JavaMailSender emailSender, MailContentBuilder mailContentBuilder, ContactRequestService contactRequestService) {
        this.emailSender = emailSender;
        this.mailContentBuilder = mailContentBuilder;
        this.contactRequestService = contactRequestService;
    }

    @Async
    public void sendRegistrationMail(User user, RegistrationRequest request) {
        log.info("Sending registration mail to " + user.getEmail());

        String title = "Digital Journal | Registration";
        String top = "To activate your account click on Activate.";
        String action_target = BASE_URL + "/confirmemail/" + request.getRegistrationUUID();
        String action_name = "Activate";
        String bottom = "That's all! You can now log in to DigitalJournal with your credentials.";

        prepareAndSend(title, user.getUsername(), top, action_name, action_target, bottom, user.getEmail(), true);
    }

    @Async
    public void sendPasswordRecoveryMail(User user, PasswordRecoveryRequest request) {
        log.info("Sending password recovery mail to " + user.getEmail());

        String title = "Digital Journal | Recover your password";
        String top = "To reset your password click on Recover.";
        String action_target = BASE_URL + "/recover/" + request.getRecoveryUUID();
        String action_name = "Recover";
        String bottom = "If you have not requested an password recovery, please ignore this message.";

        prepareAndSend(title, user.getUsername(), top, action_name, action_target, bottom, user.getEmail(), true);
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
        } catch (MailException e) {
            log.error(e.getMessage());
        }
    }

    @Async
    public void sendDeleteAccountMail(User user, DeleteAccountRequest request) {
        log.info("Sending delete account request to " + user.getEmail());

        String title = "Digital Journal | Delete your account";
        String top = "We are sad to see you go :-( If you still want to delete your account click on Delete. This will happen instantly. Keep in mind that this is not reversible.";
        String action_target = BASE_URL + "/profile/delete/" + request.getRequestid();
        String action_name = "Delete";
        String bottom = "If you have not requested an account deletion, please ignore this message.";

        prepareAndSend(title, user.getUsername(), top, action_name, action_target, bottom, user.getEmail(), true);
    }

    @Async
    public void sendMailChangeMail(User user, ChangeMailRequest request) {
        log.info("Sending change mail requests to " + user.getEmail() + " and " + request.getNewmail());

        String title = "Digital Journal | Change your email";
        String top = "to confirm the change of your email address please click Confirm.";
        String action_target = BASE_URL + "/profile/mail/confirm/";
        String action_name = "Confirm";
        String bottom = "If you have not requested an email change, please ignore this message.";

        prepareAndSend(title, user.getUsername(), top + " This will allow us to change this mail address in our records to the newly requested one " + request.getNewmail(), action_name, action_target + request.getOldmailid(), bottom, user.getEmail(), true);
        prepareAndSend(title, user.getUsername(), top + " Performing that will update your old email address to this one.", action_name, action_target + request.getNewmailid(), bottom, request.getNewmail(), true);
    }

    public void prepareAndSend(String subject, String name, String top, String action_name, String action_target, String bottom, String recipient, boolean hasAction) {
        MimeMessagePreparator message = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom(senderAddress);
            messageHelper.setValidateAddresses(true);
            messageHelper.setTo(recipient);
            messageHelper.setSubject(subject);

            String content = mailContentBuilder.build(subject, name, top, action_target, action_name, bottom, hasAction);
            messageHelper.setText(content, true);
        };

        try {
            emailSender.send(message);
        } catch (MailException e) {
            System.out.println(e.getMessage());
        }

    }
}
