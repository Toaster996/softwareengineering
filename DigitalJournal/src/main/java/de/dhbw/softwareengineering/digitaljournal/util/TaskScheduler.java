package de.dhbw.softwareengineering.digitaljournal.util;

import de.dhbw.softwareengineering.digitaljournal.business.ContactRequestService;
import de.dhbw.softwareengineering.digitaljournal.business.EmailService;
import de.dhbw.softwareengineering.digitaljournal.business.PasswordRecoveryRequestService;
import de.dhbw.softwareengineering.digitaljournal.business.RegistrationRequestService;
import de.dhbw.softwareengineering.digitaljournal.util.Tasks.DeletePWRecoveryTask;
import de.dhbw.softwareengineering.digitaljournal.util.Tasks.DeleteRegistrationRequestTask;
import de.dhbw.softwareengineering.digitaljournal.util.Tasks.SendSupportTask;
import de.dhbw.softwareengineering.digitaljournal.util.Tasks.Task;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static de.dhbw.softwareengineering.digitaljournal.util.Constants.MINUTE;

@Slf4j
@Component
public class TaskScheduler {

    private final EmailService emailService;
    private final ContactRequestService contactRequestService;
    private final RegistrationRequestService registrationRequestService;
    private final PasswordRecoveryRequestService passwordRecoveryRequestService;

    private List<Task> taskList;

    public TaskScheduler(EmailService emailService, RegistrationRequestService registrationRequestService, ContactRequestService contactRequestService, PasswordRecoveryRequestService passwordRecoveryRequestService) {
        this.emailService = emailService;
        this.contactRequestService = contactRequestService;
        this.registrationRequestService = registrationRequestService;
        this.passwordRecoveryRequestService = passwordRecoveryRequestService;

        this.taskList = new ArrayList<>();

        addTasks();
    }

    private void addTasks() {
        taskList.add(new DeletePWRecoveryTask(passwordRecoveryRequestService));
        taskList.add(new DeleteRegistrationRequestTask(registrationRequestService));
        taskList.add(new SendSupportTask(contactRequestService, emailService));
    }

    @Scheduled(initialDelay = MINUTE, fixedRate = 30 * MINUTE)
    private void execute() {
        for (Task task : taskList) {
            task.execute();
        }
    }
}
