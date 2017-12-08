package de.dhbw.softwareengineering.controller;

import de.dhbw.softwareengineering.model.ContactRequest;
import de.dhbw.softwareengineering.model.dao.ContactRequestDAO;
import de.dhbw.softwareengineering.utilities.Constants;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
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
        // contactrequest is only used for post requests
        // users that navigate to /contactRequest should bei redirected to the homepage
        return "redirect:/";
    }

    @RequestMapping(value = "/contactRequest", method = RequestMethod.POST)
    public String submit(@Valid @ModelAttribute("contactRequest") final ContactRequest contactRequest, final HttpServletRequest servletRequest, final BindingResult result, final ModelMap model) {
        // show errorpage if an error occurs
        if (result.hasErrors()) {
            return "error";
        }

        // check if one field is empty
        if (contactRequest.getEmail().isEmpty() || contactRequest.getName().isEmpty() || contactRequest.getMessage().isEmpty()) {
            model.addAttribute(Constants.STATUS_REQUEST_ATTRIBUTE_NAME, Constants.STATUSCODE_EMPTYFORM);
            return "redirect:/";
        }
        // check if email is valid
        if (!Constants.emailPattern.matcher(contactRequest.getEmail()).matches()) {
            model.addAttribute(Constants.STATUS_REQUEST_ATTRIBUTE_NAME, Constants.STATUSCODE_EMAILINVALID);
            return "redirect:/";
        }

        // add request to database
        applicationContext.refresh();
        ContactRequestDAO dao = applicationContext.getBean(ContactRequestDAO.class);
        dao.addRequest(contactRequest);
        applicationContext.close();

        // set status attribute to show a modal
        model.addAttribute(Constants.STATUS_REQUEST_ATTRIBUTE_NAME, Constants.STATUSCODE_REQUESTSENT);

        return "redirect:/";
    }
}
