package de.dhbw.softwareengineering.digitaljournal.util.tasks;

import de.dhbw.softwareengineering.digitaljournal.business.AbstractService;
import de.dhbw.softwareengineering.digitaljournal.business.DeleteAccountRequestService;

public class DeleteDeleteAccoountRequestTask extends Task {

    public DeleteDeleteAccoountRequestTask(AbstractService... service) {
        super(service);
    }

    @Override
    public void execute() {
        if (services.length == 1 && services[0] instanceof DeleteAccountRequestService) {
            DeleteAccountRequestService requestService = (DeleteAccountRequestService) services[0];
            requestService.deleteOldRequests();
        }
    }
}
