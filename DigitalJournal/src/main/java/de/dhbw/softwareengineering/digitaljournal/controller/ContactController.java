package de.dhbw.softwareengineering.digitaljournal.controller;

import de.dhbw.softwareengineering.digitaljournal.business.ContactRequestService;
import de.dhbw.softwareengineering.digitaljournal.domain.ContactRequest;
import de.dhbw.softwareengineering.digitaljournal.util.Constants;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

import static de.dhbw.softwareengineering.digitaljournal.util.Constants.STATUSCODE_EMAILINVALID;
import static de.dhbw.softwareengineering.digitaljournal.util.Constants.STATUSCODE_EMPTYFORM;
import static de.dhbw.softwareengineering.digitaljournal.util.Constants.STATUS_REQUEST_ATTRIBUTE_NAME;
import static de.dhbw.softwareengineering.digitaljournal.util.Constants.emailPattern;

@Controller
public class ContactController {

    private final ContactRequestService contactRequestService;

    public ContactController(ContactRequestService contactRequestService) {
        this.contactRequestService = contactRequestService;
    }

    @PostMapping(value = "/contact")
    public String submit(@Valid @ModelAttribute(Constants.SESSION_CONTACTREQUEST) ContactRequest contactRequest, BindingResult result, RedirectAttributes redir, Model model) {
        model.addAttribute(Constants.SESSION_CONTACTREQUEST, new ContactRequest());

        // show errorpage if an error occurs
        if (result.hasErrors()) {
            return "error";
        }

        // check if one field is empty
        if (contactRequest.getEmail().isEmpty() || contactRequest.getName().isEmpty() || contactRequest.getMessage().isEmpty()) {
            redir.addFlashAttribute(STATUS_REQUEST_ATTRIBUTE_NAME, STATUSCODE_EMPTYFORM);
            return Constants.REDIRECT;
        }
        // check if email is valid
        if (!emailPattern.matcher(contactRequest.getEmail()).matches()) {
            redir.addFlashAttribute(STATUS_REQUEST_ATTRIBUTE_NAME, STATUSCODE_EMAILINVALID);
            return Constants.REDIRECT;
        }

        if (contactRequest.getName().length() > 50) {
            redir.addFlashAttribute(STATUS_REQUEST_ATTRIBUTE_NAME, "temp_modal");
            redir.addFlashAttribute(Constants.FLASHATTRIBUTE_TEMP_MODAL_HEADER, "Name to long!");
            redir.addFlashAttribute(Constants.FLASHATTRIBUTE_TEMP_MODAL_BODY, "Please use a shorter name to contact us.");
            redir.addFlashAttribute(Constants.FLASHATTRIBUTE_MODAL_GEN_ID, "mdl_name_too_long");
            return Constants.REDIRECT;
        }
        if (contactRequest.getEmail().length() > 100) {
            redir.addFlashAttribute(STATUS_REQUEST_ATTRIBUTE_NAME, "temp_modal");
            redir.addFlashAttribute(Constants.FLASHATTRIBUTE_TEMP_MODAL_HEADER, "Email to long!");
            redir.addFlashAttribute(Constants.FLASHATTRIBUTE_TEMP_MODAL_BODY, "Please use a shorter email to contact us.");
            redir.addFlashAttribute(Constants.FLASHATTRIBUTE_MODAL_GEN_ID, "mdl_email_too_long");
            return Constants.REDIRECT;
        }
        if (contactRequest.getMessage().length() > 1000) {
            redir.addFlashAttribute(STATUS_REQUEST_ATTRIBUTE_NAME, "temp_modal");
            redir.addFlashAttribute(Constants.FLASHATTRIBUTE_TEMP_MODAL_HEADER, "Message to long!");
            redir.addFlashAttribute(Constants.FLASHATTRIBUTE_TEMP_MODAL_BODY, "Please use a shorter Message to contact us.");
            redir.addFlashAttribute(Constants.FLASHATTRIBUTE_MODAL_GEN_ID, "mdl_message_too_long");
            return Constants.REDIRECT;
        }

        // add request to database
        contactRequestService.create(contactRequest);

        // set status attribute to show a modal
        redir.addFlashAttribute(Constants.STATUS_REQUEST_ATTRIBUTE_NAME, Constants.STATUSCODE_REQUESTSENT);
        return Constants.REDIRECT;
    }

}
