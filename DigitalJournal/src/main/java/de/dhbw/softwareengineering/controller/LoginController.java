package de.dhbw.softwareengineering.controller;

import de.dhbw.softwareengineering.model.LoginUser;
import de.dhbw.softwareengineering.model.User;
import de.dhbw.softwareengineering.utilities.Constants;
import de.dhbw.softwareengineering.utilities.MySQL;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class LoginController {

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String showLoginForm(ModelMap model) {
        return toHomepage(model);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String verifyLogin(@Valid @ModelAttribute("loginUser") final LoginUser loginUser, HttpSession session, ModelMap model) {
       User user = loginUser(loginUser.getLoginName(), loginUser.getLoginPassword());

        if(user == null){
            model.addAttribute("loginError","invalidCredentials");
            return toHomepage(model);
        }
        session.setAttribute("loggedInUser", user);

        return "redirect:/journal";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(HttpSession session, final ModelMap model){
        session.removeAttribute("loggedInUser");
        return toHomepage(model);
    }

    private String toHomepage(ModelMap model) {
        model.addAttribute(Constants.STATUS_ATTRIBUTE_NAME, "new");
        model.addAttribute(new User());
        model.addAttribute(new LoginUser());
        return "home";
    }

    // TODO: Needs to be in the business logic
    private User loginUser(String username, String password) {
        User user = null;

        MySQL mySQL = MySQL.getInstance();

        Connection connection = mySQL.getConnection();

        PreparedStatement preparedStatementUser;
        ResultSet resultUser;

        BCryptPasswordEncoder encoder =  new BCryptPasswordEncoder();

        //System.out.println(String.format("Looking for User { \n\tusername = %s\n\tpassword = %s\n\thashed = %s\n}", username, password, encoder.encode(password)));
        try{
            preparedStatementUser = connection.prepareStatement("SELECT username, email, password, registrationDate, verified FROM `users` WHERE username = ?");
            preparedStatementUser.setString(1, username);
            resultUser = preparedStatementUser.executeQuery();
            resultUser.next();

            if(encoder.matches(password, resultUser.getString("password"))){
                if(!resultUser.getBoolean("verified")){
                    System.out.println("[LoginController] Not verfied user login detected! :O");
                }

                user = new User();
                user.setName(resultUser.getString("username"));
                user.setPassword(resultUser.getString("password"));
                user.setPasswordConfirm(resultUser.getString("password"));
                user.setEmail(resultUser.getString("email"));

                DateFormat df = new SimpleDateFormat("yyyy-mm-dd HH:MM:SS");
                Date result =  df.parse(resultUser.getString("registrationDate"));
                user.setRegist_date(result.getTime());
            }

        }catch (SQLException e) {
            System.err.println("[LoginController] Caught exception while looking for user: [" + e.getMessage() + "]");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return user;
    }


}
