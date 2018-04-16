package de.dhbw.softwareengineering.digitaljournal.controller;

import de.dhbw.softwareengineering.digitaljournal.domain.ContactRequest;
import de.dhbw.softwareengineering.digitaljournal.domain.form.LoginUser;
import de.dhbw.softwareengineering.digitaljournal.domain.form.RegistrationUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

import static de.dhbw.softwareengineering.digitaljournal.util.Constants.STATUS_ATTRIBUTE_NAME;

@Controller
public class ProfileController {

    @GetMapping("/profile")
    public String showProfile(Model model){
        model.addAttribute("contactRequest",new ContactRequest());

        return "profile";
    }
}
