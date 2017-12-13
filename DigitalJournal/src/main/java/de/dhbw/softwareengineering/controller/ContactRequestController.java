package de.dhbw.softwareengineering.controller;

import de.dhbw.softwareengineering.model.ContactRequest;
import de.dhbw.softwareengineering.model.dao.ContactRequestDAO;
import de.dhbw.softwareengineering.utilities.Constants;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String submit(@Valid @ModelAttribute("contactRequest") final ContactRequest contactRequest, final HttpServletRequest servletRequest, final BindingResult result, final ModelMap model, RedirectAttributes redir) {
        // show errorpage if an error occurs
        if (result.hasErrors()) {
            return "error";
        }

        // check if one field is empty
        if (contactRequest.getEmail().isEmpty() || contactRequest.getName().isEmpty() || contactRequest.getMessage().isEmpty()) {
            redir.addFlashAttribute(Constants.STATUS_REQUEST_ATTRIBUTE_NAME, Constants.STATUSCODE_EMPTYFORM);
            return "redirect:/";
        }
        // check if email is valid
        if (!Constants.emailPattern.matcher(contactRequest.getEmail()).matches()) {
            redir.addFlashAttribute(Constants.STATUS_REQUEST_ATTRIBUTE_NAME, Constants.STATUSCODE_EMAILINVALID);
            return "redirect:/";
        }

        if(contactRequest.getName().length() > 50){
            redir.addFlashAttribute(Constants.STATUS_REQUEST_ATTRIBUTE_NAME, "temp_modal");
            redir.addFlashAttribute("temp_modal_header", "Name to long!");
            redir.addFlashAttribute("temp_modal_body", "Please use a shorter name to contact us.");
            redir.addFlashAttribute("modal_gen_id", "mdl_name_too_long");
            return "redirect:/";
        }
        if(contactRequest.getEmail().length() > 100){
            redir.addFlashAttribute(Constants.STATUS_REQUEST_ATTRIBUTE_NAME, "temp_modal");
            redir.addFlashAttribute("temp_modal_header", "Email to long!");
            redir.addFlashAttribute("temp_modal_body", "Please use a shorter email to contact us.");
            redir.addFlashAttribute("modal_gen_id", "mdl_email_too_long");
            return "redirect:/";
        }
        if(contactRequest.getMessage().length() > 1000){
            redir.addFlashAttribute(Constants.STATUS_REQUEST_ATTRIBUTE_NAME, "temp_modal");
            redir.addFlashAttribute("temp_modal_header", "Message to long!");
            redir.addFlashAttribute("temp_modal_body", "Please use a shorter Message to contact us.");
            redir.addFlashAttribute("modal_gen_id", "mdl_message_too_long");
            return "redirect:/";
        }

        // add request to database
        applicationContext.refresh();
        ContactRequestDAO dao = applicationContext.getBean(ContactRequestDAO.class);
        dao.addRequest(contactRequest);
        applicationContext.close();

        // set status attribute to show a modal
        redir.addFlashAttribute(Constants.STATUS_REQUEST_ATTRIBUTE_NAME, Constants.STATUSCODE_REQUESTSENT);

        return "redirect:/";
    }
}
