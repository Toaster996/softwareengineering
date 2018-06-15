package de.dhbw.softwareengineering.digitaljournal.controller;

import de.dhbw.softwareengineering.digitaljournal.domain.ContactRequest;
import de.dhbw.softwareengineering.digitaljournal.domain.form.LoginUser;
import de.dhbw.softwareengineering.digitaljournal.domain.form.RegistrationUser;
import de.dhbw.softwareengineering.digitaljournal.util.Constants;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.security.Principal;

import static de.dhbw.softwareengineering.digitaljournal.util.Constants.STATUSCODE_INVALID_CREDENTIALS;
import static de.dhbw.softwareengineering.digitaljournal.util.Constants.STATUS_ATTRIBUTE_NAME;

@Controller
public class HomeController {

    @GetMapping("/")
    @ResponseStatus(code = HttpStatus.OK)
    public String home(Model model, Principal principal) {
        if (principal != null) {
            return "forward:/journal";
        }

        model.addAttribute(STATUS_ATTRIBUTE_NAME, "new");
        model.addAttribute("registrationUser", new RegistrationUser());
        model.addAttribute("contactRequest", new ContactRequest());
        model.addAttribute("loginUser", new LoginUser());
        return "home";
    }

    @GetMapping("/unauthorized")
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    public String unauthorized(Model model) {
        model.addAttribute("contactRequest", new ContactRequest());
        return "notloggedin";
    }

    @GetMapping("/login-error")
    @ResponseStatus(code = HttpStatus.OK)
    public String loginError(Model model) {
        model.addAttribute(STATUS_ATTRIBUTE_NAME, STATUSCODE_INVALID_CREDENTIALS);
        model.addAttribute(Constants.SESSION_REGISTRATIONUSER, new RegistrationUser());
        model.addAttribute(Constants.SESSION_CONTACTREQUEST, new ContactRequest());
        model.addAttribute(Constants.SESSION_LOGINUSER, new LoginUser());
        return "home";
    }
}
