package de.dhbw.softwareengineering.controller;

import de.dhbw.softwareengineering.model.LoginUser;
import de.dhbw.softwareengineering.model.User;
import de.dhbw.softwareengineering.utilities.MySQL;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
import java.util.regex.Pattern;
import de.dhbw.softwareengineering.utilities.Constants;


@Controller
public class RegistrationController {


    private final static Pattern emailPattern = Pattern.compile("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])");

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String showForm(Model m) {
        m.addAttribute(Constants.STATUS_ATTRIBUTE_NAME, "new");
        m.addAttribute(new User());
        m.addAttribute(new LoginUser());
        return "home";
     //   return new ModelAndView("home", "user", new User());
    }

    @RequestMapping(value = "/addUser", method = RequestMethod.POST)
    public String submit(@Valid @ModelAttribute("user") final User user, final BindingResult result, final ModelMap model) {
        if (result.hasErrors())
            return "error";
        System.out.println("[RegistrationController] " + user);

        if (user.getName().equals("") || user.getEmail().equals("") || user.getPassword().equals("") || user.getPasswordConfirm().equals("")) {
            model.addAttribute(Constants.STATUS_ATTRIBUTE_NAME, Constants.STATUSCODE_EMPTYFORM);
        } else if (!user.getPassword().equals(user.getPasswordConfirm())) {
            model.addAttribute(Constants.STATUS_ATTRIBUTE_NAME, Constants.STATUSCODE_PWMISSMATCH);
        } else if (user.getName().length() > 20) {
            model.addAttribute(Constants.STATUS_ATTRIBUTE_NAME, Constants.STATUSCODE_USERNAMETOOLONG);
        } else if (user.getEmail().length() > 100) {
            model.addAttribute(Constants.STATUS_ATTRIBUTE_NAME, Constants.STATUSCODE_EMAILTOOLONG);
        } else if (user.getPassword().length() < 6) {
            model.addAttribute(Constants.STATUS_ATTRIBUTE_NAME, Constants.STATUSCODE_PWTOOSHORT);
        } else if (user.getPassword().length() > 42) {
            model.addAttribute(Constants.STATUS_ATTRIBUTE_NAME, Constants.STATUSCODE_PWTOOLONG);
        } else if (!emailPattern.matcher(user.getEmail()).matches()) {
            model.addAttribute(Constants.STATUS_ATTRIBUTE_NAME, Constants.STATUSCODE_EMAILINVALID);
        } else {
            model.addAttribute(Constants.STATUS_ATTRIBUTE_NAME, Constants.STATUSCODE_SUCREG);
    /*
            MySQL mySQL = MySQL.getInstance();

            Connection connection = mySQL.getConnection();

            PreparedStatement preparedStatementUsername;
            ResultSet resultSetUsername;

            PreparedStatement preparedStatementEmail;
            ResultSet resultSetEmail;


            try {
                preparedStatementUsername = connection.prepareStatement("SELECT COUNT(*) AS count FROM `user` WHERE username = ?");
                preparedStatementUsername.setString(1, user.getLoginName());
                resultSetUsername = preparedStatementUsername.executeQuery();

                resultSetUsername.next();
                if (resultSetUsername.getInt("count") != 0) {
                    model.addAttribute(STATUS_ATTRIBUTE_NAME, STATUSCODE_USERNAMEALREADYINUSE);
                }

                preparedStatementEmail = connection.prepareStatement("SELECT COUNT(*) AS count FROM `user` WHERE email = ?");
                preparedStatementEmail.setString(1, user.getLoginName());
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

            model.addAttribute(Constants.STATUS_ATTRIBUTE_NAME, Constants.STATUSCODE_SUCREG);
            model.addAttribute("name", user.getLoginName());
            model.addAttribute("email", user.getEmail());

            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

            MySQL mySQL = MySQL.getInstance();

            Connection connection = mySQL.getConnection();

            PreparedStatement preparedStatement;
            try {
                preparedStatement = connection.prepareStatement("INSERT INTO `users` (`username`, `email`, `password`, `registrationDate`, `verified`) VALUES(?,?,?,?,?);");
                preparedStatement.setString(1, user.getLoginName());
                preparedStatement.setString(2, user.getEmail());
                preparedStatement.setString(3, bCryptPasswordEncoder.encode(user.getPassword()));
                preparedStatement.setLong(4, System.currentTimeMillis());
                preparedStatement.setBoolean(5, false);
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            } */
        }
        System.out.println("[RegistrationController] " + model.get(Constants.STATUS_ATTRIBUTE_NAME));
        return "home";

    }
}
