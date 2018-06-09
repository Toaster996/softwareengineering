package de.dhbw.softwareengineering.digitaljournal.controller;

import de.dhbw.softwareengineering.digitaljournal.business.EmailService;
import de.dhbw.softwareengineering.digitaljournal.business.PasswordRecoveryRequestService;
import de.dhbw.softwareengineering.digitaljournal.business.UserService;
import de.dhbw.softwareengineering.digitaljournal.domain.ContactRequest;
import de.dhbw.softwareengineering.digitaljournal.domain.PasswordRecoveryRequest;
import de.dhbw.softwareengineering.digitaljournal.domain.User;
import de.dhbw.softwareengineering.digitaljournal.domain.form.LoginUser;
import de.dhbw.softwareengineering.digitaljournal.domain.form.RegistrationUser;
import de.dhbw.softwareengineering.digitaljournal.util.Constants;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

import static de.dhbw.softwareengineering.digitaljournal.util.Constants.SESSION_CHANGEPWUSER;
import static de.dhbw.softwareengineering.digitaljournal.util.Constants.STATUSCODE_EMPTYFORM;
import static de.dhbw.softwareengineering.digitaljournal.util.Constants.STATUSCODE_PWCHANGESUCCESS;
import static de.dhbw.softwareengineering.digitaljournal.util.Constants.STATUSCODE_PWMISSMATCH;
import static de.dhbw.softwareengineering.digitaljournal.util.Constants.STATUSCODE_PWTOOLONG;
import static de.dhbw.softwareengineering.digitaljournal.util.Constants.STATUSCODE_PWTOOSHORT;
import static de.dhbw.softwareengineering.digitaljournal.util.Constants.STATUS_ATTRIBUTE_NAME;
import static de.dhbw.softwareengineering.digitaljournal.util.Constants.TEMPLATE_RECOVER;
import static de.dhbw.softwareengineering.digitaljournal.util.Constants.emailPattern;

@Controller
@RequestMapping("/recover")
public class RecoverPasswordController {

    private final UserService userService;
    private final EmailService emailService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final PasswordRecoveryRequestService passwordRecoveryRequestService;

    public RecoverPasswordController(UserService userService, EmailService emailService, BCryptPasswordEncoder bCryptPasswordEncoder, PasswordRecoveryRequestService passwordRecoveryRequestService) {
        this.userService = userService;
        this.emailService = emailService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.passwordRecoveryRequestService = passwordRecoveryRequestService;
    }

    @PostMapping
    public String index(@RequestParam("email") String email, Model model) {
        model.addAttribute(STATUS_ATTRIBUTE_NAME, "new");
        model.addAttribute("registrationUser", new RegistrationUser());
        model.addAttribute(Constants.SESSION_CONTACTREQUEST, new ContactRequest());
        model.addAttribute(Constants.SESSION_LOGINUSER, new LoginUser());

        if (!emailPattern.matcher(email).matches()) {
            model.addAttribute(TEMPLATE_RECOVER, "invalidEmail");
            return "home";
        }

        User user = userService.findByEmail(email);

        if (user != null) {
            // Delete all old requests from user
            passwordRecoveryRequestService.deleteAllByUsername(user.getUsername());

            model.addAttribute(TEMPLATE_RECOVER, "true");
            model.addAttribute("email", email);

            PasswordRecoveryRequest request = passwordRecoveryRequestService.create(user);

            emailService.sendPasswordRecoveryMail(user, request);
        } else {
            model.addAttribute(TEMPLATE_RECOVER, "false");
        }

        return "home";
    }

    @GetMapping(value = "/{uuid}")
    public String index(@PathVariable String uuid, Model model, HttpSession session) {
        model.addAttribute("contactRequest", new ContactRequest());

        PasswordRecoveryRequest passwordRecoveryRequest = passwordRecoveryRequestService.findByUUID(uuid);

        if (passwordRecoveryRequest == null) {
            model.addAttribute("status", "notFound");
            return "error";
        } else {

            User user = userService.findByName(passwordRecoveryRequest.getUsername());

            if(user != null){
                session.setAttribute("uuid", uuid);
                session.setAttribute(SESSION_CHANGEPWUSER, user.getUsername());
                model.addAttribute("username", user.getUsername());
                model.addAttribute("email", user.getEmail());
            }
        }
        return "changepassword";
    }

    @PostMapping("/change")
    public String changePassword(@RequestParam("password") String password, @RequestParam("password_confirm") String passwordConfirm, ModelMap model, HttpSession session, RedirectAttributes redir) {
        model.addAttribute("contactRequest", new ContactRequest());

        // security checks
        if (password.isEmpty() || passwordConfirm.isEmpty()) {
            redir.addFlashAttribute(STATUS_ATTRIBUTE_NAME, STATUSCODE_EMPTYFORM);
        } else if (!password.matches(passwordConfirm)) {
            redir.addFlashAttribute(STATUS_ATTRIBUTE_NAME, STATUSCODE_PWMISSMATCH);
        } else if (password.length() < 6) {
            redir.addFlashAttribute(STATUS_ATTRIBUTE_NAME, STATUSCODE_PWTOOSHORT);
        } else if (password.length() > 42) {
            redir.addFlashAttribute(STATUS_ATTRIBUTE_NAME, STATUSCODE_PWTOOLONG);
        }
        if (redir.containsAttribute(STATUS_ATTRIBUTE_NAME)) {
            String uuid = (String) session.getAttribute("uuid");
            return "redirect:/recover/" + uuid;
        }

        // get user
        String username = (String) session.getAttribute(SESSION_CHANGEPWUSER);

        // change password of user and write to database
        if (username != null) {
            User user = userService.findByName(username);
            if(user != null){
                user.setPassword(bCryptPasswordEncoder.encode(password));
                redir.addFlashAttribute(TEMPLATE_RECOVER, STATUSCODE_PWCHANGESUCCESS);
                session.removeAttribute(SESSION_CHANGEPWUSER);
                userService.update(user);
                passwordRecoveryRequestService.deleteAllByUsername(user.getUsername());
            }
        } else {
            return "error";
        }

        return Constants.REDIRECT;
    }
}
