package de.dhbw.softwareengineering.controller;

import de.dhbw.softwareengineering.model.*;
import de.dhbw.softwareengineering.model.dao.PasswordRecoveryRequestDAO;
import de.dhbw.softwareengineering.model.dao.UserDAO;
import de.dhbw.softwareengineering.utilities.Constants;
import de.dhbw.softwareengineering.utilities.Email;
import de.dhbw.softwareengineering.utilities.GeneralConfiguration;
import de.dhbw.softwareengineering.utilities.Templates;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

import java.util.UUID;

import static de.dhbw.softwareengineering.utilities.Constants.applicationContext;
import static de.dhbw.softwareengineering.utilities.Constants.prettyPrinter;

@Controller
public class RecoverPasswordController {

    /**
     * Send recovery url via email if email exists
     *
     * @param email
     * @param model
     *
     * @return
     */
    @RequestMapping(value = "/recoverpassword", method = RequestMethod.POST)
    public String index(@RequestParam("email") String email, Model model) {
        model.addAttribute(new RegistrationUser());
        model.addAttribute(new LoginUser());
        model.addAttribute(new ContactRequest());

        applicationContext.refresh();

            UserDAO userDAO = applicationContext.getBean(UserDAO.class);
            User user       = userDAO.getUserByEMail(email);

        applicationContext.close();

        if(user != null){
            model.addAttribute("recover", "true");
            model.addAttribute("email", email);

            String url = "https://" + GeneralConfiguration.getInstance().getString("domain") + "/recoverpassword?uuid=" + UUID.randomUUID().toString();
            String[] recipients = {user.getEmail()};

            Email.getInstance().sendEmailSSL(recipients, "DigitalJournal: Change your password", getEmailBody(url, user.getUsername()));
        } else{
            model.addAttribute("recover", "false");
        }

        return "home";
    }

    @RequestMapping(value = "/changepassword", method = RequestMethod.POST)
    public String index(@RequestParam("password") String password, @RequestParam("password_confirm") String password_confirm, ModelMap model, @RequestParam(value = "uuid") String uuid) {

        prettyPrinter.info(model.get("username"));

        return "redirect:/";
    }

    @RequestMapping(value = "/recoverpassword", params = {"uuid"}, method = RequestMethod.GET)
    public String index(Model model, @RequestParam(value = "uuid") String uuid) {

        applicationContext.refresh();
            PasswordRecoveryRequestDAO recoveryRequestDAO = applicationContext.getBean(PasswordRecoveryRequestDAO.class);
            PasswordRecoveryRequest passwordRecoveryRequest = recoveryRequestDAO.getRequestByUUID(uuid);

            UserDAO userDAO = applicationContext.getBean(UserDAO.class);
            User user = userDAO.getUserByName(passwordRecoveryRequest.getUsername());
        applicationContext.close();

        if (passwordRecoveryRequest == null || user == null) {
            model.addAttribute("status", "notFound");
            return "error";
        } else {
            model.addAttribute("status", "success");
            model.addAttribute("username", user.getUsername());
            model.addAttribute("email", user.getEmail());
        }
        return "changePassword";
    }

    private String getEmailBody(String url, String username) {
        String emailBody = Templates.getInstance().getTemplate(Constants.RECOVER_PASSWORD_EMAIL_TEMPLATE);
        return emailBody.replace("{$username}", username).replace("{$link}", url);
    }

}
