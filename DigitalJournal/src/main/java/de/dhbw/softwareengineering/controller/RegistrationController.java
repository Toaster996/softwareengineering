package de.dhbw.softwareengineering.controller;

import de.dhbw.softwareengineering.model.User;
import de.dhbw.softwareengineering.utilities.MySQL;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


@Controller
public class RegistrationController {

    private final static String STATUS_ATTRIBUTE_NAME = "status";
    private final static String STATUSCODE_PWMISSMATCH = "pwmissmatch";
    private final static String STATUSCODE_USERNAMETOOLONG = "nametoolong";
    private final static String STATUSCODE_EMAILTOOLONG = "emailtoolong";
    private final static String STATUSCODE_EMPTYFORM = "emptyform";
    private final static String STATUSCODE_SUCREG = "sucreg";
    private final static String STATUSCODE_PWTOOSHORT = "pwtooshort";
    private final static String STATUSCODE_PWTOOLONG = "pwtoolong";
    private final static String STATUSCODE_EMAILINVALID = "emailinvalid";
    private final static String STATUSCODE_EMAILALREADYINUSE = "usedemail";
    private final static String STATUSCODE_USERNAMEALREADYINUSE = "useduser";

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView showForm() {
        return new ModelAndView("home", "user", new User());
    }

    @RequestMapping(value = "/addUser", method = RequestMethod.POST)
    public String submit(@Valid @ModelAttribute("user") final User user, final BindingResult result, final ModelMap model) {
        if (result.hasErrors())
            return "error";
        System.out.println("[RegistrationController] " + user);

        if (user.getName().equals("") || user.getEmail().equals("") || user.getPassword().equals("") || user.getPasswordConfirm().equals("")) {
            model.addAttribute(STATUS_ATTRIBUTE_NAME, STATUSCODE_EMPTYFORM);
        } else if (user.getPassword().equals(user.getPasswordConfirm())) {
            model.addAttribute(STATUS_ATTRIBUTE_NAME, STATUSCODE_PWMISSMATCH);
        } else if (user.getName().length() > 20) {
            model.addAttribute(STATUS_ATTRIBUTE_NAME, STATUSCODE_USERNAMETOOLONG);
        } else if (user.getEmail().length() > 100) {
            model.addAttribute(STATUS_ATTRIBUTE_NAME, STATUSCODE_EMAILTOOLONG);
        } else if (user.getEmail().matches("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])")) {
            model.addAttribute(STATUS_ATTRIBUTE_NAME, STATUSCODE_EMAILINVALID);
        } else if (user.getEmail().length() > 100) {
            model.addAttribute(STATUS_ATTRIBUTE_NAME, STATUSCODE_EMAILTOOLONG);
        } else if (user.getPassword().length() < 6) {

            model.addAttribute(STATUS_ATTRIBUTE_NAME, STATUSCODE_PWTOOSHORT);
        } else if (user.getPassword().length() > 42) {
            model.addAttribute(STATUS_ATTRIBUTE_NAME, STATUSCODE_PWTOOLONG);
        } else {

            MySQL mySQL = MySQL.getInstance();

            Connection connection = mySQL.getConnection();

            PreparedStatement preparedStatementUsername;
            ResultSet resultSetUsername;

            PreparedStatement preparedStatementEmail;
            ResultSet resultSetEmail;


            try {
                preparedStatementUsername = connection.prepareStatement("SELECT COUNT(*) AS count FROM `user` WHERE username = ?");
                preparedStatementUsername.setString(1, user.getName());
                resultSetUsername = preparedStatementUsername.executeQuery();

                resultSetUsername.next();
                if (resultSetUsername.getInt("count") != 0) {
                    model.addAttribute(STATUS_ATTRIBUTE_NAME, STATUSCODE_USERNAMEALREADYINUSE);
                }

                preparedStatementEmail = connection.prepareStatement("SELECT COUNT(*) AS count FROM `user` WHERE email = ?");
                preparedStatementEmail.setString(1, user.getName());
                resultSetEmail = preparedStatementUsername.executeQuery();

                resultSetEmail.next();
                if (resultSetEmail.getInt("count") != 0) {
                    model.addAttribute(STATUS_ATTRIBUTE_NAME, STATUSCODE_EMAILALREADYINUSE);
                }

                preparedStatementEmail.close();
                preparedStatementUsername.close();

                resultSetEmail.close();
                resultSetUsername.close();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


        if (!model.containsAttribute(STATUS_ATTRIBUTE_NAME)) {

            model.addAttribute(STATUS_ATTRIBUTE_NAME, STATUSCODE_SUCREG);
            model.addAttribute("name", user.getName());
            model.addAttribute("email", user.getEmail());

            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

            MySQL mySQL = MySQL.getInstance();

            Connection connection = mySQL.getConnection();

            PreparedStatement preparedStatement;
            try {
                preparedStatement = connection.prepareStatement("INSERT INTO `users` (`username`, `email`, `password`, `registrationDate`, `verified`) VALUES(?,?,?,?,?);");
                preparedStatement.setString(1, user.getName());
                preparedStatement.setString(2, user.getEmail());
                preparedStatement.setString(3, bCryptPasswordEncoder.encode(user.getPassword()));
                preparedStatement.setLong(4, System.currentTimeMillis());
                preparedStatement.setBoolean(5, false);
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return "home";

    }
}
