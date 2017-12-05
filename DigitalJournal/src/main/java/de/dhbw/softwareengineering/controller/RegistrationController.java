package de.dhbw.softwareengineering.controller;

import de.dhbw.softwareengineering.model.*;
import de.dhbw.softwareengineering.model.dao.RegistrationRequestDAO;
import de.dhbw.softwareengineering.model.dao.UserDAO;
import de.dhbw.softwareengineering.utilities.Constants;
import de.dhbw.softwareengineering.utilities.Email;
import de.dhbw.softwareengineering.utilities.GeneralConfiguration;
import de.dhbw.softwareengineering.utilities.Templates;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.UUID;
import java.util.regex.Pattern;

import static de.dhbw.softwareengineering.utilities.Constants.applicationContext;


@Controller
public class RegistrationController {

    private final static String STATUS_ATTRIBUTE_NAME = "status";
    private final static String STATUSCODE_USERNAMETOOLONG = "nametoolong";
    private final static String STATUSCODE_EMAILALREADYINUSE = "usedemail";
    private final static String STATUSCODE_USERNAMEALREADYINUSE = "useduser";
    private final static String STATUSCODE_ALPHANUMERIC = "alphanumeric";

    private final static Pattern emailPattern = Pattern.compile("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])");
    private final static Pattern usernamePattern = Pattern.compile("[a-zA-Z0-9]+");

    private static String emailBody;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String showForm(Model m, HttpSession session) {
        if (session.getAttribute("loggedInUser") != null) {
            return "redirect:/journal";
        }

        m.addAttribute(Constants.STATUS_ATTRIBUTE_NAME, "new");
        m.addAttribute(new RegistrationUser());
        m.addAttribute(new LoginUser());
        m.addAttribute(new ContactRequest());

        return "home";
    }

    @RequestMapping(value = "/addUser", method = RequestMethod.POST)
    public String submit(@Valid @ModelAttribute("registrationUser") final RegistrationUser registrationUser, final BindingResult result, final ModelMap model, HttpSession session) {
        if (session.getAttribute("loggedInUser") != null) {
            return "redirect:/journal";
        }
        model.addAttribute(new ContactRequest());
        if (result.hasErrors())
            return "error";

        Constants.prettyPrinter.info(registrationUser.toString());

        if (registrationUser.getName().equals("") || registrationUser.getEmail().equals("") || registrationUser.getPassword().equals("") || registrationUser.getPasswordConfirm().equals("")) {
            model.addAttribute(Constants.STATUS_ATTRIBUTE_NAME, Constants.STATUSCODE_EMPTYFORM);
        } else if (!registrationUser.getPassword().equals(registrationUser.getPasswordConfirm())) {
            model.addAttribute(Constants.STATUS_ATTRIBUTE_NAME, Constants.STATUSCODE_PWMISSMATCH);
        } else if (registrationUser.getName().length() > 20) {
            model.addAttribute(Constants.STATUS_ATTRIBUTE_NAME, Constants.STATUSCODE_USERNAMETOOLONG);
        } else if (!usernamePattern.matcher(registrationUser.getName()).matches()) {
            model.addAttribute(Constants.STATUS_ATTRIBUTE_NAME, STATUSCODE_ALPHANUMERIC);
        } else if (registrationUser.getEmail().length() > 100) {
            model.addAttribute(Constants.STATUS_ATTRIBUTE_NAME, Constants.STATUSCODE_EMAILTOOLONG);
        } else if (registrationUser.getPassword().length() < 6) {
            model.addAttribute(Constants.STATUS_ATTRIBUTE_NAME, Constants.STATUSCODE_PWTOOSHORT);
        } else if (registrationUser.getPassword().length() > 42) {
            model.addAttribute(Constants.STATUS_ATTRIBUTE_NAME, Constants.STATUSCODE_PWTOOLONG);
        } else if (!emailPattern.matcher(registrationUser.getEmail()).matches()) {
            model.addAttribute(Constants.STATUS_ATTRIBUTE_NAME, Constants.STATUSCODE_EMAILINVALID);
        } else {
            // Check entered information against database
            applicationContext.refresh();
            UserDAO userDAO = applicationContext.getBean(UserDAO.class);
            if (userDAO.getUserByName(registrationUser.getName()) != null) {
                model.addAttribute(STATUS_ATTRIBUTE_NAME, STATUSCODE_USERNAMEALREADYINUSE);
            }
            if (userDAO.getUserByEMail(registrationUser.getEmail()) != null) {
                model.addAttribute(STATUS_ATTRIBUTE_NAME, STATUSCODE_EMAILALREADYINUSE);
            }
            applicationContext.close();
        }


        if (!model.containsAttribute(STATUS_ATTRIBUTE_NAME)) {
            model.addAttribute(Constants.STATUS_ATTRIBUTE_NAME, Constants.STATUSCODE_SUCREG);
            model.addAttribute("username", registrationUser.getName());
            model.addAttribute("email", registrationUser.getEmail());

            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

            String uuid = UUID.randomUUID().toString();

            User newUser = new User();
            newUser.setUsername(registrationUser.getName());
            newUser.setEmail(registrationUser.getEmail());
            newUser.setPassword(bCryptPasswordEncoder.encode(registrationUser.getPassword()));
            newUser.setRegistrationDate(System.currentTimeMillis());
            newUser.setVerified(false);

            RegistrationRequest request = new RegistrationRequest();
            request.setUsername(registrationUser.getName());
            request.setRegistration_uuid(uuid);

            // add user to database and insert registration request
            applicationContext.refresh();
            // add user
            UserDAO userDAO = applicationContext.getBean(UserDAO.class);
            userDAO.createNewUser(newUser);

            // insert request
            RegistrationRequestDAO requestDAO = applicationContext.getBean(RegistrationRequestDAO.class);
            requestDAO.addRequest(request);

            applicationContext.close();

            String url = "https://" + GeneralConfiguration.getInstance().getString("domain") + "/confirmEmail?uuid=" + uuid;
            String[] recipients = {registrationUser.getEmail()};
            Email.getInstance().sendEmailSSL(recipients, "DigitalJournal: Confirm your email", getEmailBody(url, registrationUser.getName()));
        }

        Constants.prettyPrinter.info(model.get(Constants.STATUS_ATTRIBUTE_NAME).toString());
        return "home";
    }

    private String getEmailBody(String url, String username) {
        emailBody = Templates.getInstance().getTemplate(Constants.SIGNUP_EMAIL_TEMPLATE);
        return emailBody.replace("{$username}", username).replace("{$link}", url);
    }
}
