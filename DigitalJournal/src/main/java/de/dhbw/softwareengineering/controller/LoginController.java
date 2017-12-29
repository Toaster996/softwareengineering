package de.dhbw.softwareengineering.controller;

import de.dhbw.softwareengineering.model.ContactRequest;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import static de.dhbw.softwareengineering.utilities.Constants.applicationContext;

@Controller
public class LoginController {

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String showLoginForm(ModelMap model, HttpSession session, RedirectAttributes redir) {
        if(session.getAttribute("loggedInUser") != null)return "redirect:/journal";
        return toHomepage(redir);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String verifyLogin(@Valid @ModelAttribute("loginUser") final LoginUser loginUser, HttpSession session, ModelMap model, RedirectAttributes redir) {
        if(session.getAttribute("loggedInUser") == null){
            User user = loginUser(loginUser.getLoginName(), loginUser.getLoginPassword());

            if (user == null) {
                redir.addFlashAttribute("loginError", "invalidCredentials");
                return toHomepage(redir);
            }
            user.setUsername(user.getUsername().trim());
            session.setAttribute("loggedInUser", user);
        }
        return "redirect:/journal";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(HttpSession session, final ModelMap model, RedirectAttributes redir) {
        session.removeAttribute("loggedInUser");
        return toHomepage(redir);
    }

    private String toHomepage(RedirectAttributes redir) {
        redir.addFlashAttribute(Constants.STATUS_ATTRIBUTE_NAME, "new");
        redir.addFlashAttribute(new RegistrationUser());
        redir.addFlashAttribute(new LoginUser());
        redir.addFlashAttribute(new ContactRequest());
        return "redirect:/";
    }

    private User loginUser(String username, String password) {
        User user = null;
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        applicationContext.refresh();

        UserDAO userDAO = applicationContext.getBean(UserDAO.class);
        User possibleUser = userDAO.getUserByName(username);
        if (possibleUser != null && encoder.matches(password, possibleUser.getPassword())) {
            if (possibleUser.isVerified()) {
                user = possibleUser;
            }
        }

        applicationContext.close();
        return user;
    }
}
