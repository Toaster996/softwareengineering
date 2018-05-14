package de.dhbw.softwareengineering.digitaljournal.util.Tasks;

import de.dhbw.softwareengineering.digitaljournal.business.AbstractService;
import de.dhbw.softwareengineering.digitaljournal.business.ContactRequestService;
import de.dhbw.softwareengineering.digitaljournal.business.EmailService;
import de.dhbw.softwareengineering.digitaljournal.domain.ContactRequest;

import java.util.List;

public class SendSupportTask implements Task {
    @Override
    public void execute(AbstractService... service) {
        if (service.length == 2)
            if (service[0] instanceof ContactRequestService && service[1] instanceof EmailService) {
                ContactRequestService contactRequestService = (ContactRequestService) service[0];
                EmailService emailService = (EmailService) service[1];
                List<ContactRequest> unsolvedRequests = contactRequestService.findAllUnsolvedRequests();
                for (ContactRequest contactRequest : unsolvedRequests) {
                    emailService.sendSupportMail(contactRequest);
                }
            }
    }
}
