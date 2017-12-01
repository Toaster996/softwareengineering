package de.dhbw.softwareengineering.controller;

import de.dhbw.softwareengineering.model.RegistrationRequest;
import de.dhbw.softwareengineering.model.User;
import de.dhbw.softwareengineering.model.dao.RegistrationRequestDAO;
import de.dhbw.softwareengineering.model.dao.UserDAO;
import de.dhbw.softwareengineering.utilities.MySQL;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static de.dhbw.softwareengineering.utilities.Constants.applicationContext;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Controller
public class EmailConfirmationController {

    @RequestMapping(value = "confirmEmail",
            params = {"uuid"})
    public String index(Model model, @RequestParam(value = "uuid") String uuid) {
        // Check UUID against database and update if valid
        applicationContext.refresh();

            RegistrationRequestDAO requestDAO = applicationContext.getBean(RegistrationRequestDAO.class);
            UserDAO userDAO = applicationContext.getBean(UserDAO.class);
            RegistrationRequest request = requestDAO.getRequestByUUID(uuid);

            // Check if UUID matches a user
            if (request != null) {
                // If so remove the request
                requestDAO.removeRequest(request.getRegistration_uuid());

                // Update the user
                User user = userDAO.getUserByName(request.getUsername());
                     user.setVerified(true);

                userDAO.updateUser(user);

                model.addAttribute("response", "Your registration was successful. You can now log in");
            }else{
                model.addAttribute("response", "Could not confirm email address! Please note that these links are only valid for 24h. If your registration request ist older than 24h please make a new one.");
            }

        applicationContext.close();

        return "emailConfirmed";
    }
}
