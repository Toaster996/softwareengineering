package de.dhbw.softwareengineering.controller;

import de.dhbw.softwareengineering.utilities.MySQL;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Controller
public class EmailConfirmationController {

    @RequestMapping(value = "confirmEmail",
            params = { "uuid" })
    public String index (Model model, @RequestParam(value="uuid") String uuid){

        MySQL mySQL = MySQL.getInstance();

        Connection connection = mySQL.getConnection();

        System.out.println(uuid);

        try(PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM `registration_request` WHERE `registration_uuid` = ?")) {
            preparedStatement.setString(1, uuid);

            ResultSet resultSet = preparedStatement.executeQuery();

            System.out.println(preparedStatement);
            if(resultSet.next()){

                String username = resultSet.getString("username");

                PreparedStatement preparedStatementValidateUser = connection.prepareStatement("UPDATE `users` SET verified = ? WHERE username = ?");
                preparedStatementValidateUser.setBoolean(1, true);
                preparedStatementValidateUser.setString(2, username);
                preparedStatementValidateUser.executeUpdate();

                PreparedStatement preparedStatementDeleteRequest = connection.prepareStatement("DELETE FROM `registration_request` WHERE username = ?");
                preparedStatementDeleteRequest.setString(1, username);
                preparedStatementDeleteRequest.executeUpdate();

                model.addAttribute("response", "Your registration was successful. You can now log in");

                preparedStatementValidateUser.close();
                preparedStatementDeleteRequest.close();
            } else {
                model.addAttribute("response", "Could not confirm email address! Please note that these links are only valid for 24h. If your registration request ist older than 24h please make a new one.");
            }

            resultSet.close();
        } catch (SQLException e){
            e.printStackTrace();
        }

        return "emailConfirmed";
    }
}
