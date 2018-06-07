package de.dhbw.softwareengineering.digitaljournal.util.tasks;

import de.dhbw.softwareengineering.digitaljournal.business.AbstractService;
import de.dhbw.softwareengineering.digitaljournal.business.PasswordRecoveryRequestService;

public class DeletePWRecoveryTask extends de.dhbw.softwareengineering.digitaljournal.util.tasks.Task {

    public DeletePWRecoveryTask(AbstractService... service) {
        super(service);
    }

    @Override
    public void execute() {
        if (services.length == 1 && services[0] instanceof PasswordRecoveryRequestService) {
            PasswordRecoveryRequestService passwordRecoveryRequestService = (PasswordRecoveryRequestService) services[0];
            passwordRecoveryRequestService.deleteOldRequests();
        }
    }
}
