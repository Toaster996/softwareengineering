package de.dhbw.softwareengineering.controller;

import de.dhbw.softwareengineering.model.ContactRequest;
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
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

import static de.dhbw.softwareengineering.utilities.Constants.applicationContext;

@Controller
public class JournalController {
    @RequestMapping(value = "/journal", method = RequestMethod.GET)
    public String showForm(Model m, HttpSession session) {
        m.addAttribute(new ContactRequest());
        if (session.getAttribute("loggedInUser") == null) return "notloggedin";
        m.addAttribute("journal", new Journal());

        //Load all Journals
        applicationContext.refresh();
        JournalDAO journalDAO = applicationContext.getBean(JournalDAO.class);
        User user = (User) session.getAttribute("loggedInUser");
        List<Journal> journals = journalDAO.getAllJournals(user.getUsername());

        m.addAttribute("journals", journals);
        m.addAttribute("currentTime", System.currentTimeMillis());
        applicationContext.close();
        return "feed";
    }

    @RequestMapping(value = "/newjournal", method = RequestMethod.POST)
    public String submit(@Valid @ModelAttribute("journal") final Journal journal, final BindingResult result, final ModelMap model, HttpSession session) {
        if (result.hasErrors())
            return "error";
        if (journal.getJournalName().equals(""))
            model.addAttribute(Constants.STATUS_ATTRIBUTE_NAME, Constants.STATUSCODE_EMPTYFORM);
        if (journal.getJournalName().length() > 100) {
            model.addAttribute(Constants.STATUS_ATTRIBUTE_NAME, Constants.STATUSCODE_MODAL_TEMP);
            model.addAttribute(Constants.STATUSCODE_MODAL_HEADER, "Journalname to long!");
            model.addAttribute(Constants.STATUSCODE_MODAL_BODY, "Please enter an shorter Journalname.");
        }

        //Get logged int user
        User user = (User) session.getAttribute("loggedInUser");
        journal.setUsername(user.getUsername());
        journal.setDate(System.currentTimeMillis());

        applicationContext.refresh();
        JournalDAO journalDAO = applicationContext.getBean(JournalDAO.class);
        journalDAO.newJournal(journal);
        applicationContext.close();

        return "redirect:/journal";
    }

    //Cick on Edit Button
    @RequestMapping(value = "/editjournal", method = RequestMethod.GET)
    public String show(@RequestParam(name = "journalid") String journalid, Model m, HttpSession session) {
        m.addAttribute("journal", new Journal());
        m.addAttribute(new ContactRequest());
        System.out.println("Journal ID: " + journalid);

        if (session.getAttribute("loggedInUser") == null)
            return "error";

        applicationContext.refresh();
        JournalDAO journalDAO = applicationContext.getBean(JournalDAO.class);
        Journal journal = journalDAO.getJournal(Integer.parseInt(journalid));
        applicationContext.close();

        User user = (User) session.getAttribute("loggedInUser");
        m.addAttribute("user", user);
        if (user.getUsername().equals(journal.getUsername())) {
            if (session.getAttribute("currentJournal") != null) {
                session.removeAttribute("currentJournal");
            }
            session.setAttribute("currentJournal", journal);
            m.addAttribute("journal", journal);
            return "editjournal";
        }

        return "redirect:/journal";
    }

    @RequestMapping(value = "/editjournal", method = RequestMethod.POST)
    public String editJournal(@Valid @ModelAttribute("journal") final Journal newJournal, final BindingResult result, final ModelMap model, HttpSession session) {
        if (result.hasErrors())
            return "error";
        if (newJournal.getJournalName().equals(""))
            model.addAttribute(Constants.STATUS_ATTRIBUTE_NAME, Constants.STATUSCODE_EMPTYFORM);
        if (newJournal.getJournalName().length() > 100) {
            model.addAttribute(Constants.STATUS_ATTRIBUTE_NAME, Constants.STATUSCODE_MODAL_TEMP);
            model.addAttribute(Constants.STATUSCODE_MODAL_HEADER, "Journalname to long!");
            model.addAttribute(Constants.STATUSCODE_MODAL_BODY, "Please enter an shorter Journalname.");
        }

        User loggedInUser = (User) session.getAttribute("loggedInUser");
        Journal oldJournal = (Journal) session.getAttribute("currentJournal");

        if (loggedInUser.getUsername().equals(oldJournal.getUsername())) {
            oldJournal.setJournalName(newJournal.getJournalName());
            oldJournal.setContent(newJournal.getContent());

            applicationContext.refresh();
            JournalDAO journalDAO = applicationContext.getBean(JournalDAO.class);
            journalDAO.updateJournal(oldJournal);
            applicationContext.close();
        }

        return "redirect:/journal";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public String delete(Model m, HttpSession session) {
        Journal journal = (Journal) session.getAttribute("currentJournal");
        m.addAttribute("journal", journal);
        m.addAttribute(new ContactRequest());

        if (session.getAttribute("loggedInUser") == null)
            return "error";

        m.addAttribute("delete", "true");
        return "editjournal";

    }

    @RequestMapping(value = "/deleteconfirm", method = RequestMethod.GET)
    public String deleteConfirm(Model m, HttpSession session) {
        System.out.println("I ran here");
       //m.addAttribute("journal", new Journal());
        m.addAttribute(new ContactRequest());

        if (session.getAttribute("loggedInUser") == null)
            return "error";

        applicationContext.refresh();
        JournalDAO journalDAO = applicationContext.getBean(JournalDAO.class);
        Journal oldJournal = (Journal) session.getAttribute("currentJournal");
        journalDAO.removeJournal(oldJournal);
        applicationContext.close();
        session.removeAttribute("currentJournal");


        return "redirect:/journal";
    }

}
