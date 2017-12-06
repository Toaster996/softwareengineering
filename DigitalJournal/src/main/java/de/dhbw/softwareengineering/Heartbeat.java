package de.dhbw.softwareengineering;

import de.dhbw.softwareengineering.model.ContactRequest;
import de.dhbw.softwareengineering.model.PasswordRecoveryRequest;
import de.dhbw.softwareengineering.model.RegistrationRequest;
import de.dhbw.softwareengineering.model.dao.ContactRequestDAO;
import de.dhbw.softwareengineering.model.dao.PasswordRecoveryRequestDAO;
import de.dhbw.softwareengineering.model.dao.RegistrationRequestDAO;
import de.dhbw.softwareengineering.model.dao.UserDAO;
import de.dhbw.softwareengineering.utilities.Constants;
import de.dhbw.softwareengineering.utilities.Email;
import de.dhbw.softwareengineering.utilities.Templates;

import java.util.List;

import static de.dhbw.softwareengineering.utilities.Constants.applicationContext;
import static de.dhbw.softwareengineering.utilities.Constants.prettyPrinter;

public class Heartbeat implements Runnable {

    public void run() {
        Constants.prettyPrinter.info("Heartbeat started");

        int counter = 0;
        while (true) {
            try {
                counter++;

                executeEverySecond();

                if (counter % 60 == 0) {
                    executeEveryMinute();
                }

                if (counter % 3600 == 0) {
                    executeEveryHour();
                }

                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public Heartbeat() {
        new Thread(this).start();
    }

    private void executeEverySecond() {}

    private void executeEveryMinute() {
        deleteOldRegistrationRequests();
        deleteOldPasswordRecoveryRequests();
        sendContactRequestsToSupport();
    }

    private void executeEveryHour() {}

    private void deleteOldPasswordRecoveryRequests() {
        applicationContext.refresh();
            PasswordRecoveryRequestDAO recoveryRequestDAO = applicationContext.getBean(PasswordRecoveryRequestDAO.class);

            for(PasswordRecoveryRequest recoveryRequest : recoveryRequestDAO.getOldRequests()){
                recoveryRequestDAO.deleteRequest(recoveryRequest);
                Constants.prettyPrinter.info("Deleted old password recovery request from user: " + recoveryRequest.getUsername());
            }
        applicationContext.close();
    }

    private void sendContactRequestsToSupport() {
        applicationContext.refresh();
            ContactRequestDAO requestDAO = applicationContext.getBean(ContactRequestDAO.class);
            List<ContactRequest> requests = requestDAO.getUnsentRequests();
            for(ContactRequest request : requests){
                if(Email.getInstance().sendEmailSSL(Constants.SUPPORT_RECIPIENT, "DigitalJournal: ContactRequest from " + request.getName(), getEmailBody(request))){
                    requestDAO.solveRequest(request);
                }else{
                    //prettyPrinter.error(new Exception("Could not send mail to support! Deleting request..."));
                    //requestDAO.deleteRequest(request);
                }
            }
        applicationContext.close();
    }

    private void deleteOldRegistrationRequests() {
        applicationContext.refresh();

            RegistrationRequestDAO requestDAO = applicationContext.getBean(RegistrationRequestDAO.class);
            List<RegistrationRequest> requests = requestDAO.getOldRequests(System.currentTimeMillis() - Constants.ONE_DAY_IN_MILLIS);

            if (requests != null && requests.size() > 0) {
                UserDAO userDAO = applicationContext.getBean(UserDAO.class);

                for (RegistrationRequest request : requests) {
                    requestDAO.removeRequest(request.getRegistration_uuid());
                    userDAO.removeUser(request.getUsername());
                    Constants.prettyPrinter.info("Deleted old registration request from user: " + request.getUsername());
                }
            }

        applicationContext.close();
    }

    private String getEmailBody(ContactRequest request) {
        String emailBody = Templates.getInstance().getTemplate(Constants.SIGNUP_EMAIL_TEMPLATE);
        return emailBody.replace("{$email}", request.getEmail()).replace("{$name}", request.getName()).replace("{$message}", request.getMessage());
    }
}
