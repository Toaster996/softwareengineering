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
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Controller
public class RegistrationController {
    Map<String, User> UserMap = new HashMap<>();

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView showForm() {
        return new ModelAndView("home", "user", new User());
    }

    @RequestMapping(value = "/addUser", method = RequestMethod.POST)
    public String submit(@Valid @ModelAttribute("user") final User user, final BindingResult result, final ModelMap model) {
        if (result.hasErrors())
            return "error";

        //TODO check the content of user, check if password equals passwordConfirm
        model.addAttribute("name", user.getName());
        model.addAttribute("email", user.getEmail());
        model.addAttribute("password", user.getPassword());
        UserMap.put(user.getName(), user);
        System.out.println(user);

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        MySQL mySQL = MySQL.getInstance();

        Connection connection = mySQL.getConnection();

        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement("INSERT INTO 'users' ('username', 'email', 'password','registrationDate') VALUES(?,?,?,?);");
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setString(3, bCryptPasswordEncoder.encode(user.getPassword()));
            preparedStatement.setLong(4, System.currentTimeMillis());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "info";

    }
}
