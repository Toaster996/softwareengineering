package de.dhbw.softwareengineering.digitaljournal.util.tasks;

import de.dhbw.softwareengineering.digitaljournal.business.AbstractService;
import de.dhbw.softwareengineering.digitaljournal.business.ChangeMailRequestService;
import de.dhbw.softwareengineering.digitaljournal.business.DeleteAccountRequestService;

public class DeleteChangeMailRequestTask extends Task {

    public DeleteChangeMailRequestTask(AbstractService... service) {
        super(service);
    }

    @Override
    public void execute() {
        if (services.length == 1 && services[0] instanceof ChangeMailRequestService) {
            ChangeMailRequestService requestService = (ChangeMailRequestService) services[0];
            requestService.deleteOldRequests();
        }
    }
}
