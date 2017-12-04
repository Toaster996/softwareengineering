package de.dhbw.softwareengineering.controller;

import de.dhbw.softwareengineering.model.ContactRequest;
import de.dhbw.softwareengineering.model.dao.ContactRequestDAO;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import static de.dhbw.softwareengineering.utilities.Constants.applicationContext;

@Controller
public class ContactRequestController {

    @RequestMapping(value = "/contactRequest", method = RequestMethod.GET)
    public String showForm() {
        return "redirect:/";
    }

    @RequestMapping(value = "/contactRequest", method = RequestMethod.POST)
    public String submit(@Valid @ModelAttribute("contactRequest") final ContactRequest contactRequest, final HttpServletRequest servletRequest, final BindingResult result) {
        if (result.hasErrors())
            return "error";

        applicationContext.refresh();
            ContactRequestDAO dao = applicationContext.getBean(ContactRequestDAO.class);
            dao.addRequest(contactRequest);
        applicationContext.close();

        return "redirect:"+servletRequest.getHeader("Referer");
    }
}
