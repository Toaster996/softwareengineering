package de.dhbw.softwareengineering.digitaljournal.util.Tasks;

import de.dhbw.softwareengineering.digitaljournal.business.AbstractService;
import de.dhbw.softwareengineering.digitaljournal.business.PasswordRecoveryRequestService;

public class DeletePWRecoveryTask implements Task {
    @Override
    public void execute(AbstractService... service) {
        if(service.length == 1 && service[0] instanceof PasswordRecoveryRequestService){
            PasswordRecoveryRequestService passwordRecoveryRequestService = (PasswordRecoveryRequestService) service[0];
            passwordRecoveryRequestService.deleteOldRequests();
        }
    }
}
