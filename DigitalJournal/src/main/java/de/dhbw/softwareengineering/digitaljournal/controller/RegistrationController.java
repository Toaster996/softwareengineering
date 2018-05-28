package de.dhbw.softwareengineering.digitaljournal.controller;

import de.dhbw.softwareengineering.digitaljournal.business.EmailService;
import de.dhbw.softwareengineering.digitaljournal.business.RegistrationRequestService;
import de.dhbw.softwareengineering.digitaljournal.business.UserService;
import de.dhbw.softwareengineering.digitaljournal.domain.ContactRequest;
import de.dhbw.softwareengineering.digitaljournal.domain.RegistrationRequest;
import de.dhbw.softwareengineering.digitaljournal.domain.User;
import de.dhbw.softwareengineering.digitaljournal.domain.form.RegistrationUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

import static de.dhbw.softwareengineering.digitaljournal.util.Constants.*;

@Controller
public class RegistrationController {

    private final UserService userService;
    private final EmailService emailService;
    private final RegistrationRequestService registrationRequestService;

    public RegistrationController(UserService userService, EmailService emailService, RegistrationRequestService registrationRequestService) {
        this.userService = userService;
        this.emailService = emailService;
        this.registrationRequestService = registrationRequestService;
    }

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("registrationUser") RegistrationUser registrationUser, BindingResult result, Model model) {
        model.addAttribute("contactRequest", new ContactRequest());

        if (result.hasErrors()) {
            return "error";
        }

        if (registrationUser.getName().equals("") || registrationUser.getEmail().equals("") || registrationUser.getPassword().equals("") || registrationUser.getPasswordConfirm().equals("")) {
            model.addAttribute(STATUS_ATTRIBUTE_NAME, STATUSCODE_EMPTYFORM);
        } else if (!registrationUser.getPassword().equals(registrationUser.getPasswordConfirm())) {
            model.addAttribute(STATUS_ATTRIBUTE_NAME, STATUSCODE_PWMISSMATCH);
        } else if (registrationUser.getName().length() > 20) {
            model.addAttribute(STATUS_ATTRIBUTE_NAME, STATUSCODE_USERNAMETOOLONG);
        } else if (!usernamePattern.matcher(registrationUser.getName()).matches()) {
            model.addAttribute(STATUS_ATTRIBUTE_NAME, STATUSCODE_ALPHANUMERIC);
        } else if (registrationUser.getEmail().length() > 100) {
            model.addAttribute(STATUS_ATTRIBUTE_NAME, STATUSCODE_EMAILTOOLONG);
        } else if (registrationUser.getPassword().length() < 6) {
            model.addAttribute(STATUS_ATTRIBUTE_NAME, STATUSCODE_PWTOOSHORT);
        } else if (registrationUser.getPassword().length() > 42) {
            model.addAttribute(STATUS_ATTRIBUTE_NAME, STATUSCODE_PWTOOLONG);
        } else if (!emailPattern.matcher(registrationUser.getEmail()).matches()) {
            model.addAttribute(STATUS_ATTRIBUTE_NAME, STATUSCODE_EMAILINVALID);
        } else {

            // Check entered information against database
            if (userService.existByUsername(registrationUser.getName())) {
                model.addAttribute("username", registrationUser.getName());
                model.addAttribute(STATUS_ATTRIBUTE_NAME, STATUSCODE_USERNAMEALREADYINUSE);
                return "home";
            }
            if (userService.existByEmail(registrationUser.getEmail())) {
                model.addAttribute("email", registrationUser.getEmail());
                model.addAttribute(STATUS_ATTRIBUTE_NAME, STATUSCODE_EMAILALREADYINUSE);
                return "home";
            }
        }

        if (!model.containsAttribute(STATUS_ATTRIBUTE_NAME)) {
            model.addAttribute(STATUS_ATTRIBUTE_NAME, STATUSCODE_SUCREG);
            model.addAttribute("username", registrationUser.getName());
            model.addAttribute("email", registrationUser.getEmail());

            User user = userService.create(registrationUser);
            RegistrationRequest request = registrationRequestService.create(registrationUser.getName());

            emailService.sendRegistrationMail(user, request);
        }

        return "home";
    }

    @GetMapping("/confirmemail/{uuid}")
    public String confirmEmail(@PathVariable String uuid, Model model) {
        model.addAttribute("contactRequest", new ContactRequest());

        if (registrationRequestService.confirmRequest(uuid, userService)) {
            model.addAttribute(STATUS_RESPONSE_ATTRIBUTE_NAME, String.valueOf(true));
        } else {
            model.addAttribute(STATUS_RESPONSE_ATTRIBUTE_NAME, String.valueOf(false));
        }

        return "confirmemail";
    }
}
