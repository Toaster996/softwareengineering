package de.dhbw.softwareengineering.controller;

import de.dhbw.softwareengineering.Heartbeat;
import de.dhbw.softwareengineering.utilities.Constants;
import de.dhbw.softwareengineering.utilities.FileConfiguration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

@Component
class StartupHousekeeper {

    @EventListener(ContextRefreshedEvent.class)
    void contextRefreshedEvent() {
        initMySQLConfiguration();
        initEmailConfiguration();
        initSignUpEmailTemplate();

        new Heartbeat();
    }

    private void initMySQLConfiguration() {
        File configurationDirectory = new File(Constants.CONFIGURATION_DIRECTORY);
        if (!configurationDirectory.exists()) {
            configurationDirectory.mkdir();
        }

        FileConfiguration mysqlConfiguration = new FileConfiguration(new File(configurationDirectory.getAbsolutePath() + File.separator + "mysql.conf"));

        mysqlConfiguration.setDefaultValue("user", "root");
        mysqlConfiguration.setDefaultValue("database", "digital_journal");
        mysqlConfiguration.setDefaultValue("password", "asdf");
        mysqlConfiguration.setDefaultValue("port", "3306");
        mysqlConfiguration.setDefaultValue("hostname", "localhost");

        try {
            mysqlConfiguration.save();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initEmailConfiguration() {
        FileConfiguration emailConfiguration = new FileConfiguration(new File(Constants.CONFIGURATION_DIRECTORY + File.separator + Constants.EMAIL_CONFIGURATION_NAME));

        emailConfiguration.setDefaultValue("host", "localhost");
        emailConfiguration.setDefaultValue("port", "587");
        emailConfiguration.setDefaultValue("simpleName", "DigitalJournal");
        emailConfiguration.setDefaultValue("accountName", "digitaljournal");
        emailConfiguration.setDefaultValue("password", "password");

        try {
            emailConfiguration.save();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initSignUpEmailTemplate() {

        File configurationDirectory = new File(Constants.TEMPLATE_DIRECTORY);
        if (!configurationDirectory.exists()) {
            configurationDirectory.mkdir();
        }

        File file = new File(Constants.TEMPLATE_DIRECTORY + File.separator + Constants.SIGNUP_EMAIL_TEMPLATE);

        if (!file.exists()) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, false))) {
                writer.write("Hello {$username},<br/>");
                writer.newLine();
                writer.write("to finish the registration process click on the following link to confirm your email: <br/><br/>");
                writer.newLine();
                writer.newLine();
                writer.write("<a href=\"{$link}\">Confirm your email</a> <br/><br/>");
                writer.newLine();
                writer.newLine();
                writer.write("Kind regards, <br/>");
                writer.newLine();
                writer.write("Your DigitalJournal-Team");

                writer.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        File file2 = new File(Constants.TEMPLATE_DIRECTORY + File.separator + Constants.SUPPORT_EMAIL_TEMPLATE);

        if (!file2.exists()) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file2, false))) {
                writer.write("New Contact Request,<br/>");
                writer.newLine();
                writer.write("Email: {$email} <br/><br/>");
                writer.newLine();
                writer.newLine();
                writer.write("Name: {$name} <br/><br/>");
                writer.newLine();
                writer.newLine();
                writer.write("Message: {$message} <br/><br/>");
                writer.newLine();
                writer.newLine();

                writer.flush();
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        File file3 = new File(Constants.TEMPLATE_DIRECTORY + File.separator + Constants.RECOVER_PASSWORD_EMAIL_TEMPLATE);

        if (!file3.exists()) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file3, false))) {
                writer.write("Hello {$username},<br/>");
                writer.newLine();
                writer.write("to change your password click on the following link: <br/><br/>");
                writer.newLine();
                writer.newLine();
                writer.write("<a href=\"{$link}\">Change Password</a> <br/><br/>");
                writer.newLine();
                writer.newLine();
                writer.write("If you did not requested a password change just ignore this mail!");
                writer.newLine();
                writer.newLine();
                writer.write("Kind regards, <br/>");
                writer.newLine();
                writer.write("Your DigitalJournal-Team");

                writer.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
