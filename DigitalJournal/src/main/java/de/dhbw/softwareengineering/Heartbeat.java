package de.dhbw.softwareengineering;

import de.dhbw.softwareengineering.utilities.MySQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
        MySQL mySQL = MySQL.getInstance();

        Connection connection = mySQL.getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT `registration_request`.username, `users`.registrationDate " +
                "FROM `registration_request` " +
                "LEFT JOIN `users` ON `registration_request`.username = `users`.username WHERE `users`.registrationDate < ?")) {

            preparedStatement.setLong(1, System.currentTimeMillis() - TWENTYFOUR_HOURS_IN_MILLIS);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String username = resultSet.getString("username");

                PreparedStatement preparedStatementDeleteRequest = connection.prepareStatement("DELETE FROM `registration_request` WHERE `username` = ?");
                preparedStatementDeleteRequest.setString(1, username);
                preparedStatementDeleteRequest.executeUpdate();

                PreparedStatement preparedStatementDeleteUser = connection.prepareStatement("DELETE FROM `users` WHERE `username` = ?");
                preparedStatementDeleteUser.setString(1, username);
                preparedStatementDeleteUser.executeUpdate();

                System.out.println("[Heartbeat] deleted request for user: " + username);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
