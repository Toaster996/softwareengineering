package de.dhbw.softwareengineering.controller;

import de.dhbw.softwareengineering.model.PasswordRecoveryRequest;
import de.dhbw.softwareengineering.model.User;
import de.dhbw.softwareengineering.model.dao.PasswordRecoveryRequestDAO;
import de.dhbw.softwareengineering.model.dao.UserDAO;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

import static de.dhbw.softwareengineering.utilities.Constants.applicationContext;

public class RecoverPasswordController {

    @RequestMapping(value = "/recoverpassword", method = RequestMethod.POST)
    public String index(String email, Model model, @RequestParam(value = "uuid") String uuid) {
        return "";
    }

    @RequestMapping(value = "/changepassword", method = RequestMethod.POST)
    public String index(String password, String password_confirm, Model model, @RequestParam(value = "uuid") String uuid) {
        return "redirect:/";
    }

    @RequestMapping(value = "/recoverpassword", params = {"uuid"}, method = RequestMethod.GET)
    public String index(Model model, @RequestParam(value = "uuid") String uuid) {

        applicationContext.refresh();
            PasswordRecoveryRequestDAO recoveryRequestDAO = applicationContext.getBean(PasswordRecoveryRequestDAO.class);
            PasswordRecoveryRequest passwordRecoveryRequest = recoveryRequestDAO.getRequestByUUID(uuid);

            UserDAO userDAO = applicationContext.getBean(UserDAO.class);
            User user       = userDAO.getUserByName(passwordRecoveryRequest.getUsername());
        applicationContext.close();

        if (passwordRecoveryRequest == null || user == null) {
            model.addAttribute("status", "notFound");
            return "error";
        } else {
            model.addAttribute("status", "success");
            model.addAttribute("username", user.getUsername());
            model.addAttribute("email",    user.getEmail());
        }
        return "changePassword";
    }

}
