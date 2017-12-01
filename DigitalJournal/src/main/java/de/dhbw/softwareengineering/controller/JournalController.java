package de.dhbw.softwareengineering.controller;

import de.dhbw.softwareengineering.model.Journal;
import de.dhbw.softwareengineering.model.User;
import de.dhbw.softwareengineering.model.dao.JournalDAO;
import de.dhbw.softwareengineering.utilities.Constants;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import static de.dhbw.softwareengineering.utilities.Constants.applicationContext;

@Controller
public class JournalController {
    @RequestMapping(value = "/journal", method = RequestMethod.GET)
    public String showForm(Model m) {
        m.addAttribute("journal", new Journal());
        return "feed";
    }

    @RequestMapping(value = "/newjournal", method = RequestMethod.POST)
    public String submit(@Valid @ModelAttribute("journal") final Journal journal, final BindingResult result, final ModelMap model, HttpSession session) {
        if (result.hasErrors())
            return "error";
        if(session.getAttribute("loggedInUser") == null)
            return "notloggedin";
        System.out.println("[JournalController] " + journal);
        if(journal.getName().equals(""))
            model.addAttribute(Constants.STATUS_ATTRIBUTE_NAME, Constants.STATUSCODE_EMPTYFORM);
        if(journal.getName().length() > 100) {
            model.addAttribute(Constants.STATUS_ATTRIBUTE_NAME, Constants.STATUSCODE_MODAL_TEMP);
            model.addAttribute(Constants.STATUSCODE_MODAL_HEADER, "Journalname to long!");
            model.addAttribute(Constants.STATUSCODE_MODAL_BODY, "Please enter an shorter Journalname.");
        }

        System.out.println("[JournalController] " + model.get("status"));
        //Get logged int user

        User user = (User) session.getAttribute("loggedInUser");
        journal.setUser(user);

        applicationContext.refresh();
        JournalDAO journalDAO = applicationContext.getBean(JournalDAO.class);
        journalDAO.newJournal(journal);

        applicationContext.close();
        return "feed";
    }

    @RequestMapping(value = "/editjournal", method = RequestMethod.GET)
    public String show(Model m, HttpSession session) {
        m.addAttribute("journal", new Journal());
        if(session.getAttribute("loggedInUser") == null)
            return "notloggedin";
        return "editjournal";
    }

}
