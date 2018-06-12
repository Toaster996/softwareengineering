package de.dhbw.softwareengineering.digitaljournal.controller;

import de.dhbw.softwareengineering.digitaljournal.business.ChangeMailRequestService;
import de.dhbw.softwareengineering.digitaljournal.business.DeleteAccountRequestService;
import de.dhbw.softwareengineering.digitaljournal.business.EmailService;
import de.dhbw.softwareengineering.digitaljournal.business.GoalService;
import de.dhbw.softwareengineering.digitaljournal.business.JournalService;
import de.dhbw.softwareengineering.digitaljournal.business.UserService;
import de.dhbw.softwareengineering.digitaljournal.domain.ChangeMailRequest;
import de.dhbw.softwareengineering.digitaljournal.domain.ContactRequest;
import de.dhbw.softwareengineering.digitaljournal.domain.DeleteAccountRequest;
import de.dhbw.softwareengineering.digitaljournal.domain.User;
import de.dhbw.softwareengineering.digitaljournal.util.Constants;
import de.dhbw.softwareengineering.digitaljournal.util.exceptions.DeleteAccountRequestException;
import de.dhbw.softwareengineering.digitaljournal.util.exceptions.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

import static de.dhbw.softwareengineering.digitaljournal.util.Constants.*;

@Slf4j
@Controller
@RequestMapping("/profile")
public class ProfileController {

    private final UserService userService;
    private final JournalService journalService;
    private final GoalService goalService;
    private final EmailService emailService;
    private final DeleteAccountRequestService deleteAccountRequestService;
    private final ChangeMailRequestService changeMailRequestService;
    private final BCryptPasswordEncoder passwordEncoder;

    public ProfileController(UserService userService, JournalService journalService, GoalService goalService, EmailService emailService, DeleteAccountRequestService deleteAccountRequestService, ChangeMailRequestService changeMailRequestService, BCryptPasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.journalService = journalService;
        this.goalService = goalService;
        this.emailService = emailService;
        this.deleteAccountRequestService = deleteAccountRequestService;
        this.changeMailRequestService = changeMailRequestService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/")
    public String showProfile(Model model, Principal principal) {
        try {
            User user = userService.findByName(principal.getName());
            setModelAttribs(model, user);
        } catch (UserNotFoundException e) {
            log.error(e.getMessage());
        }

        return TEMPLATE_PROFILE;
    }

    @PostMapping("/changepassword")
    public String changePassword(Model model, @RequestParam("old_password") String oldPassword, @RequestParam("password") String password, @RequestParam("password_confirm") String passwordConfirm, Principal principal) {
        try {
            User user = userService.findByName(principal.getName());

            if (passwordEncoder.matches(oldPassword, user.getPassword())) {
                if (password.equals(passwordConfirm)) {
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

            setModelAttribs(model, user);
        } catch (UserNotFoundException e) {
            log.error(e.getMessage());
        }

        return TEMPLATE_PROFILE;
    }

    @PostMapping("/deleteaccount")
    public String deleteAccount(Model model, Principal principal) {
        try {
            User user = userService.findByName(principal.getName());

            DeleteAccountRequest request = deleteAccountRequestService.create(user.getUsername());

            emailService.sendDeleteAccountMail(user, request);

            model.addAttribute(STATUS_ATTRIBUTE_NAME, Constants.STATUSCODE_SUCCESS);
            setModelAttribs(model, user);
        } catch (UserNotFoundException e) {
            model.addAttribute(STATUS_ATTRIBUTE_NAME, Constants.STATUSCODE_REQUEST_FAILED);
            log.error(e.getMessage());
        }

        return TEMPLATE_PROFILE;
    }

    @PostMapping("/mail/change")
    public String changeMail(Model model, @RequestParam("new_mail") String newMail, Principal principal) {
        User user;

        try {
            user = userService.findByName(principal.getName());
        } catch (UserNotFoundException e) {
            model.addAttribute(STATUS_ATTRIBUTE_NAME, Constants.STATUSCODE_REQUEST_FAILED);
            log.error(e.getMessage());

            return REDIRECT_JOURNAL;
        }

        setModelAttribs(model, user);

        if (newMail.length() > 100) {
            model.addAttribute(STATUS_ATTRIBUTE_NAME, STATUSCODE_EMAILTOOLONG);
        } else if (Constants.emailPattern.matcher(newMail).matches()) {
            if (!userService.existByEmail(newMail)) {
                ChangeMailRequest request = changeMailRequestService.create(user.getUsername(), newMail);

                if (request != null) {
                    emailService.sendMailChangeMail(user, request);
                    model.addAttribute(STATUS_ATTRIBUTE_NAME, STATUSCODE_SUCCESS);
                }
            } else {
                model.addAttribute(STATUS_ATTRIBUTE_NAME, STATUSCODE_EMAILALREADYINUSE);
            }
        } else {
            model.addAttribute(STATUS_ATTRIBUTE_NAME, Constants.STATUSCODE_EMAILINVALID);
        }

        return TEMPLATE_PROFILE;
    }

    @GetMapping("/mail/confirm/{id}")
    public String changeMail(Model model, @PathVariable String id, RedirectAttributes redir) {
        String username = changeMailRequestService.confirm(id);

        if (username != null) {
            String newmail = changeMailRequestService.getNewMail(username);

            boolean[] confirmation = changeMailRequestService.isConfirmed(username);
            if (newmail != null && confirmation[0] && confirmation[1]) {
                try {
                    User user = userService.findByName(username);

                    user.setEmail(newmail);

                    model.addAttribute("newmail", newmail);

                    userService.update(user);
                    changeMailRequestService.delete(username);
                } catch (UserNotFoundException e) {
                    log.error(e.getMessage());
                }
            }

            model.addAttribute("confirmed", confirmation[0] && confirmation[1]);
            model.addAttribute("oldmailid", confirmation[0]);

            model.addAttribute("newmailid", confirmation[1]);
        } else {
            redir.addFlashAttribute("emailchange","invalid_request");
            return REDIRECT;
        }

        return "mailchangeprogress";
    }

    @GetMapping("/delete/{requestUUID}")
    public String deleteAccount(@PathVariable String requestUUID, HttpServletRequest servletRequest, RedirectAttributes redir) {
        try {
            DeleteAccountRequest request = deleteAccountRequestService.findByUUID(requestUUID);

            userService.deleteAccount(request);
            deleteAccountRequestService.deleteRequest(request);

            try {
                servletRequest.logout();
            } catch (ServletException e) {
                log.error(e.getMessage());
            }

            redir.addFlashAttribute("profile","delete");
        } catch (DeleteAccountRequestException e) {
            redir.addFlashAttribute("profile","delete_invalid_request");
            log.error(e.getMessage());
        }

        return Constants.REDIRECT;
    }

    private void setModelAttribs(Model model, User user) {
        model.addAttribute(Constants.SESSION_CONTACTREQUEST, new ContactRequest());
        model.addAttribute("emailchangerequest", changeMailRequestService.hasRequest(user.getUsername()));
        model.addAttribute("deleterequest", deleteAccountRequestService.hasDeletionRequest(user.getUsername()));
        model.addAttribute("user", user);
        model.addAttribute("journalCount", journalService.countByUsername(user.getUsername()));
        model.addAttribute("activeGoals", goalService.getActiveGoals(user.getUsername()));
    }

}
