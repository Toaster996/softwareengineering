package de.dhbw.softwareengineering.digitaljournal.util.Tasks;

import de.dhbw.softwareengineering.digitaljournal.business.AbstractService;
import de.dhbw.softwareengineering.digitaljournal.business.RegistrationRequestService;

public class DeleteRegistrationRequestTask implements Task {

    @Override
    public void execute(AbstractService... service) {
        if (service[0] instanceof RegistrationRequestService) {
            RegistrationRequestService registrationRequestService = (RegistrationRequestService) service[0];
            registrationRequestService.deleteOldRequests();
        }
    }
}
