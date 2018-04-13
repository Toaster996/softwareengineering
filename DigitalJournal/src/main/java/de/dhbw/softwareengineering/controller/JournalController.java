package de.dhbw.softwareengineering.controller;

import de.dhbw.softwareengineering.model.ContactRequest;
import de.dhbw.softwareengineering.model.Goal;
import de.dhbw.softwareengineering.model.Journal;
import de.dhbw.softwareengineering.model.User;
import de.dhbw.softwareengineering.model.dao.JournalDAO;
import de.dhbw.softwareengineering.utilities.Constants;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

import static de.dhbw.softwareengineering.utilities.Constants.applicationContext;
import static org.apache.commons.lang.StringEscapeUtils.escapeHtml;

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

        for (Journal j : journals) {
            j.setContent(escapeHtml(j.getContent()).replaceAll("\n", "<br/>"));
        }
        //TODO This List has to be filled with data from the DB
        List<Goal> goals = getDummyGoals();
        m.addAttribute("goals", goals);
        m.addAttribute("journals", journals);
        m.addAttribute("goal", new Goal());
        m.addAttribute("currentTime", System.currentTimeMillis());
        applicationContext.close();
        return "feed";
    }

    private List<Goal> getDummyGoals() {
        List<Goal> goals = new ArrayList<>();
        Goal goal = new Goal("Buy beer", "2018/04/12", "I need some beer!");
        goal.setId("3242342");
        goals.add(goal);
        return goals;
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

    @RequestMapping(value = "/editjournal/{journalId}", method = RequestMethod.GET)
    public String editJournal(@PathVariable String journalId, Model m, HttpSession session) {
        m.addAttribute("journal", new Journal());
        m.addAttribute(new ContactRequest());
        System.out.println("Journal ID: " + journalId);

        if (session.getAttribute("loggedInUser") == null)
            return "error";

        applicationContext.refresh();
        JournalDAO journalDAO = applicationContext.getBean(JournalDAO.class);
        Journal journal = journalDAO.getJournal(Integer.parseInt(journalId));
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


    @RequestMapping(value = "journal/newgoal", method = RequestMethod.GET)
    public String openModalNewGoal(Model m, HttpSession session, RedirectAttributes redir) {
        redir.addFlashAttribute(Constants.STATUS_ATTRIBUTE_NAME, "createGoal");
        return "redirect:/journal";
    }

    @RequestMapping(value = "journal/newgoal", method = RequestMethod.POST)
    public String submit(@Valid @ModelAttribute("goal") final Goal goal, final BindingResult result, final ModelMap model, HttpSession session) {
        //TODO save goal to DB
        return "redirect:/journal";
    }

    @RequestMapping(value = "/journal/goal/{goalID}", method = RequestMethod.GET)
    public String showGoal(@PathVariable String goalID, Model m, HttpSession session, RedirectAttributes redir) {
        //TODO get goal by id, if logged in user owns it
        Goal goalByID = new Goal("win against Magnus", "2022/02/33", "I want to win a chessgame against Magnus Carlsen.");
        goalByID.setDaysLeft("210");
        redir.addFlashAttribute(Constants.STATUS_ATTRIBUTE_NAME, "showGoal");
        redir.addFlashAttribute("showGoal", goalByID);
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
