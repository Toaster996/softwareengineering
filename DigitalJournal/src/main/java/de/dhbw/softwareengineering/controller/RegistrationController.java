package de.dhbw.softwareengineering.controller;

import de.dhbw.softwareengineering.model.Employee;
import de.dhbw.softwareengineering.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@Controller
public class RegistrationController {
    Map<String, User> UserMap = new HashMap<>();

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView showForm() {
        return new ModelAndView("home", "user", new User());
    }

    @RequestMapping(value = "/addUser", method = RequestMethod.POST)
    public String submit(@Valid @ModelAttribute("employee") final User user, final BindingResult result, final ModelMap model) {
        if (result.hasErrors())
            return "error";

        //TODO check the content of user, check if password equals passwordConfirm
        model.addAttribute("name", user.getName());
        model.addAttribute("email", user.getEmail());
        model.addAttribute("password", user.getPassword());
        UserMap.put(user.getName(), user);
        System.out.println(user);
        return "info";

    }
}
