package de.dhbw.softwareengineering.digitaljournal.controller;

import de.dhbw.softwareengineering.digitaljournal.business.JournalService;
import de.dhbw.softwareengineering.digitaljournal.business.UserService;
import de.dhbw.softwareengineering.digitaljournal.domain.ContactRequest;
import de.dhbw.softwareengineering.digitaljournal.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class ProfileController {

    private final UserService userService;
    private final JournalService journalService;


    public ProfileController(UserService userService, JournalService journalService) {
        this.userService = userService;
        this.journalService = journalService;
    }

    @GetMapping("/profile")
    public String showProfile(Model model, Principal principal){
        User user = userService.findByName(principal.getName());

        model.addAttribute("contactRequest",new ContactRequest());
        model.addAttribute("user", user);
        model.addAttribute("journalCount", journalService.countByUsername(user.getUsername()));

        return "profile";
    }
}
