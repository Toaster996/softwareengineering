package de.dhbw.softwareengineering.digitaljournal.controller;

import de.dhbw.softwareengineering.digitaljournal.domain.ContactRequest;
import de.dhbw.softwareengineering.digitaljournal.domain.Goal;
import de.dhbw.softwareengineering.digitaljournal.domain.Journal;
import de.dhbw.softwareengineering.digitaljournal.domain.form.LoginUser;
import de.dhbw.softwareengineering.digitaljournal.domain.form.RegistrationUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import static de.dhbw.softwareengineering.digitaljournal.util.Constants.*;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(Model model, Principal principal){
        if (principal != null)
            return "redirect:journal";

        model.addAttribute(STATUS_ATTRIBUTE_NAME, "new");
        model.addAttribute("registrationUser", new RegistrationUser());
        model.addAttribute("contactRequest",new ContactRequest());
        model.addAttribute("loginUser", new LoginUser());
        return "home";
    }

    @GetMapping("/unauthorized")
    public String unauthorized(Model model){
        model.addAttribute("contactRequest",new ContactRequest());
        return "notloggedin";
    }

    @GetMapping("/login-error")
    public String loginError(Model model){
        model.addAttribute(STATUS_ATTRIBUTE_NAME, "invalidCredentials");
        model.addAttribute("registrationUser", new RegistrationUser());
        model.addAttribute("contactRequest",new ContactRequest());
        model.addAttribute("loginUser", new LoginUser());
        return "home";
    }

    @GetMapping("/changepassword")
    public String changePassword(Model model){
        model.addAttribute("contactRequest",new ContactRequest());
        model.addAttribute("username", "alf");
        model.addAttribute("email", "alf@mail.com");
        return "changepassword";
    }

}
