package de.dhbw.softwareengineering;

import de.dhbw.softwareengineering.model.RegistrationRequest;
import de.dhbw.softwareengineering.model.dao.RegistrationRequestDAO;
import de.dhbw.softwareengineering.model.dao.UserDAO;

import java.util.List;

import static de.dhbw.softwareengineering.utilities.Constants.applicationContext;

public class Heartbeat implements Runnable {

    private static int TWENTYFOUR_HOURS_IN_MILLIS = 24 * 60 * 60 * 1000;

    @Override
    public void run() {
        System.out.println("Heartbeat started");
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

    private void executeEverySecond() {

    }

    private void executeEveryMinute() {
        deleteOldRegistrationRequests();
    }

    private void executeEveryHour() {

    }

    private void deleteOldRegistrationRequests() {
        applicationContext.refresh();

            RegistrationRequestDAO requestDAO = applicationContext.getBean(RegistrationRequestDAO.class);
            List<RegistrationRequest> requests = requestDAO.getOldRequests(System.currentTimeMillis() - TWENTYFOUR_HOURS_IN_MILLIS);

            if (requests != null && requests.size() > 0) {
                UserDAO userDAO = applicationContext.getBean(UserDAO.class);

                for (RegistrationRequest request : requests) {
                    requestDAO.removeRequest(request.getRegistration_uuid());
                    userDAO.removeUser(request.getUsername());
                    System.out.println("[Heartbeat] deleted request for user: " + request.getUsername());
                }
            }

        applicationContext.close();
    }
}
