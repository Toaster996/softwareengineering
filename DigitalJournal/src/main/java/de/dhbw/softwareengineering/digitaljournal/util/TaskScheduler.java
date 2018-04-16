package de.dhbw.softwareengineering.digitaljournal.util;

import de.dhbw.softwareengineering.digitaljournal.business.ContactRequestService;
import de.dhbw.softwareengineering.digitaljournal.business.EmailService;
import de.dhbw.softwareengineering.digitaljournal.business.PasswordRecoveryRequestService;
import de.dhbw.softwareengineering.digitaljournal.business.RegistrationRequestService;
import de.dhbw.softwareengineering.digitaljournal.domain.ContactRequest;
import de.dhbw.softwareengineering.digitaljournal.domain.RegistrationRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

import static de.dhbw.softwareengineering.digitaljournal.util.Constants.HOUR;
import static de.dhbw.softwareengineering.digitaljournal.util.Constants.MINUTE;

@Slf4j
@Component
public class TaskScheduler {

    private final EmailService emailService;
    private final ContactRequestService contactRequestService;
    private final RegistrationRequestService registrationRequestService;
    private final PasswordRecoveryRequestService passwordRecoveryRequestService;

    public TaskScheduler(EmailService emailService, RegistrationRequestService registrationRequestService, ContactRequestService contactRequestService, PasswordRecoveryRequestService passwordRecoveryRequestService) {
        this.emailService = emailService;
        this.contactRequestService = contactRequestService;
        this.registrationRequestService = registrationRequestService;
        this.passwordRecoveryRequestService = passwordRecoveryRequestService;
    }

    @Scheduled(initialDelay = MINUTE, fixedRate = 30 * MINUTE)
    private void executeEveryHour() {
        deleteRegistrationRequests();
        deleteOldPasswordRecoveryRequests();
        sendContactRequestsToSupport();
    }

    private void deleteRegistrationRequests() {
        registrationRequestService.deleteOldRequests();
    }

    private void deleteOldPasswordRecoveryRequests() {
        passwordRecoveryRequestService.deleteOldRequests();
    }

    private void sendContactRequestsToSupport() {
        List<ContactRequest> unsolvedRequests = contactRequestService.findAllUnsolvedRequests();
        for (ContactRequest contactRequest : unsolvedRequests) {
            emailService.sendSupportMail(contactRequest);
        }
    }
}
