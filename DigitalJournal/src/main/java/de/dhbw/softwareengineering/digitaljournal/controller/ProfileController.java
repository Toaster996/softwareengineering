package de.dhbw.softwareengineering.digitaljournal.controller;

import de.dhbw.softwareengineering.digitaljournal.business.GoalService;
import de.dhbw.softwareengineering.digitaljournal.business.JournalService;
import de.dhbw.softwareengineering.digitaljournal.business.UserService;
import de.dhbw.softwareengineering.digitaljournal.domain.ContactRequest;
import de.dhbw.softwareengineering.digitaljournal.domain.User;
import de.dhbw.softwareengineering.digitaljournal.util.Constants;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

import static de.dhbw.softwareengineering.digitaljournal.util.Constants.STATUSCODE_INVALID_CREDENTIALS;
import static de.dhbw.softwareengineering.digitaljournal.util.Constants.STATUSCODE_PWMISSMATCH;
import static de.dhbw.softwareengineering.digitaljournal.util.Constants.STATUSCODE_PWTOOLONG;
import static de.dhbw.softwareengineering.digitaljournal.util.Constants.STATUSCODE_PWTOOSHORT;
import static de.dhbw.softwareengineering.digitaljournal.util.Constants.STATUSCODE_SUCCESS;
import static de.dhbw.softwareengineering.digitaljournal.util.Constants.STATUS_ATTRIBUTE_NAME;

@Controller
public class ProfileController {

    private final UserService userService;
    private final JournalService journalService;
    private final GoalService goalService;
    private final BCryptPasswordEncoder passwordEncoder;


    public ProfileController(UserService userService, JournalService journalService, GoalService goalService, BCryptPasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.journalService = journalService;
        this.goalService = goalService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/profile")
    public String showProfile(Model model, Principal principal) {
        User user = userService.findByName(principal.getName());

        model.addAttribute(Constants.SESSION_CONTACTREQUEST, new ContactRequest());
        model.addAttribute("user", user);
        model.addAttribute("journalCount", journalService.countByUsername(user.getUsername()));
        model.addAttribute("activeGoals", goalService.getActiveGoals(user.getUsername()));

        return "profile";
    }

    @PostMapping("/profile/changepassword")
    public String changePassword(Model model, @RequestParam("old_password") String old_password, @RequestParam("password") String password, @RequestParam("password_confirm") String password_confirm, Principal principal) {
        User user = userService.findByName(principal.getName());

        if (user != null) {
            if (passwordEncoder.matches(old_password, user.getPassword())) {
                if (password.equals(password_confirm)) {
                    if (password.length() < 6) {
                        model.addAttribute(STATUS_ATTRIBUTE_NAME, STATUSCODE_PWTOOSHORT);
                    } else if (password.length() > 42) {
                        model.addAttribute(STATUS_ATTRIBUTE_NAME, STATUSCODE_PWTOOLONG);
                    } else {
                        model.addAttribute(STATUS_ATTRIBUTE_NAME, STATUSCODE_SUCCESS);
                        user.setPassword(passwordEncoder.encode(password));
                        userService.update(user);
                    }
                } else {
                    model.addAttribute(STATUS_ATTRIBUTE_NAME, STATUSCODE_PWMISSMATCH);
                }
            } else {
                model.addAttribute(STATUS_ATTRIBUTE_NAME, STATUSCODE_INVALID_CREDENTIALS);
            }
        }

        model.addAttribute(Constants.SESSION_CONTACTREQUEST, new ContactRequest());
        model.addAttribute("user", user);
        model.addAttribute("journalCount", journalService.countByUsername(user.getUsername()));
        model.addAttribute("activeGoals", goalService.getActiveGoals(user.getUsername()));

        return "profile";
    }
}
