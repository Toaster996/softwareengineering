package de.dhbw.softwareengineering.digitaljournal.util.tasks;

import de.dhbw.softwareengineering.digitaljournal.business.AbstractService;
import de.dhbw.softwareengineering.digitaljournal.business.ContactRequestService;
import de.dhbw.softwareengineering.digitaljournal.business.EmailService;
import de.dhbw.softwareengineering.digitaljournal.domain.ContactRequest;

import java.util.List;

public class SendSupportTask extends de.dhbw.softwareengineering.digitaljournal.util.tasks.Task {

    public SendSupportTask(AbstractService... service) {
        super(service);
    }

    @Override
    public void execute() {
        if (services.length == 2)
            if (services[0] instanceof ContactRequestService && services[1] instanceof EmailService) {
                ContactRequestService contactRequestService = (ContactRequestService) services[0];
                EmailService emailService = (EmailService) services[1];
                List<ContactRequest> unsolvedRequests = contactRequestService.findAllUnsolvedRequests();
                for (ContactRequest contactRequest : unsolvedRequests) {
                    emailService.sendSupportMail(contactRequest);
                }
            }
    }
}
