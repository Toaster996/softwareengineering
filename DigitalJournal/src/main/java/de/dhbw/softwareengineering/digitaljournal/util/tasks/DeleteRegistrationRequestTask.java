package de.dhbw.softwareengineering.digitaljournal.util.tasks;

import de.dhbw.softwareengineering.digitaljournal.business.AbstractService;
import de.dhbw.softwareengineering.digitaljournal.business.RegistrationRequestService;

public class DeleteRegistrationRequestTask extends Task {

    public DeleteRegistrationRequestTask(AbstractService... service) {
        super(service);
    }

    @Override
    public void execute() {
        if (services[0] instanceof RegistrationRequestService) {
            RegistrationRequestService registrationRequestService = (RegistrationRequestService) services[0];
            registrationRequestService.deleteOldRequests();
        }
    }
}
