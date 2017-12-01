package de.dhbw.softwareengineering.controller;

import de.dhbw.softwareengineering.model.LoginUser;
import de.dhbw.softwareengineering.model.RegistrationUser;
import de.dhbw.softwareengineering.model.User;
import de.dhbw.softwareengineering.model.dao.UserDAO;
import de.dhbw.softwareengineering.utilities.Constants;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import static de.dhbw.softwareengineering.utilities.Constants.applicationContext;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class LoginController {

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String showLoginForm(ModelMap model) {
        return toHomepage(model);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String verifyLogin(@Valid @ModelAttribute("loginUser") final LoginUser loginUser, HttpSession session, ModelMap model) {
        User user = loginUser(loginUser.getLoginName(), loginUser.getLoginPassword());

        if (user == null) {
            model.addAttribute("loginError", "invalidCredentials");
            return toHomepage(model);
        }
        session.setAttribute("loggedInUser", user);

        return "redirect:/journal";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(HttpSession session, final ModelMap model) {
        session.removeAttribute("loggedInUser");
        return toHomepage(model);
    }

    private String toHomepage(ModelMap model) {
        model.addAttribute(Constants.STATUS_ATTRIBUTE_NAME, "new");
        model.addAttribute(new RegistrationUser());
        model.addAttribute(new LoginUser());
        return "home";
    }

    private User loginUser(String username, String password) {
        User user = null;
        BCryptPasswordEncoder encoder =  new BCryptPasswordEncoder();

        applicationContext.refresh();

            UserDAO userDAO = applicationContext.getBean(UserDAO.class);
            User possibleUser = userDAO.getUserByName(username);
            if(possibleUser != null && encoder.matches(password, possibleUser.getPassword())){
                if(possibleUser.isVerified()){
                    user = possibleUser;
                }
            }

        applicationContext.close();

        return user;
    }
}
