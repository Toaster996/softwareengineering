package de.dhbw.softwareengineering.digitaljournal.controller;

import de.dhbw.softwareengineering.digitaljournal.business.GoalService;
import de.dhbw.softwareengineering.digitaljournal.business.JournalService;
import de.dhbw.softwareengineering.digitaljournal.domain.ContactRequest;
import de.dhbw.softwareengineering.digitaljournal.domain.Goal;
import de.dhbw.softwareengineering.digitaljournal.domain.Journal;
import de.dhbw.softwareengineering.digitaljournal.util.Constants;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

import static de.dhbw.softwareengineering.digitaljournal.util.Constants.*;

@Controller
@RequestMapping("/journal")
public class JournalController {

    private final JournalService journalService;
    private final GoalService goalService;


    public JournalController(JournalService journalService, GoalService goalService) {
        this.journalService = journalService;
        this.goalService = goalService;
    }

    @GetMapping
    public String showFeed(Model model, Principal principal) {
        List<Journal> journals = journalService.findAll(principal.getName());
        List<Goal> goals = goalService.findLatestsGoals(principal.getName());
        if (!model.containsAttribute(Constants.SHOW_FURTHER_GOALS_BTN))
            model.addAttribute(Constants.SHOW_FURTHER_GOALS_BTN, true);
        model.addAttribute("contactRequest", new ContactRequest());
        if (!model.containsAttribute("goals"))
            model.addAttribute("goals", goals);
        model.addAttribute("journal", new Journal());
        if (!model.containsAttribute("journals"))
            model.addAttribute("journals", journals);
        model.addAttribute("goal", new Goal());

        return "feed";
    }

    @PostMapping("/create")
    public String createJournal(@Valid @ModelAttribute("journal") Journal journal, BindingResult result, Model model, Principal principal) {
        model.addAttribute("contactRequest", new ContactRequest());

        if (result.hasErrors())
            return "error";

        if (journal.getJournalName().equals("")) {
            model.addAttribute(STATUS_ATTRIBUTE_NAME, STATUSCODE_EMPTYFORM);
        } else if (journal.getJournalName().length() > 100) {
            model.addAttribute(STATUS_ATTRIBUTE_NAME, STATUSCODE_MODAL_TEMP);
            model.addAttribute(STATUSCODE_MODAL_HEADER, "Journalname to long!");
            model.addAttribute(STATUSCODE_MODAL_BODY, "Please enter an shorter Journalname.");
        } else if (journal.getContent().length() > JOURNAL_CONTENT_SIZE) {
            model.addAttribute(STATUS_ATTRIBUTE_NAME, STATUSCODE_MODAL_TEMP);
            model.addAttribute(STATUSCODE_MODAL_HEADER, "Content to long!");
            model.addAttribute(STATUSCODE_MODAL_BODY, "Please make your content shorter.");
        } else {
            journal.setUsername(principal.getName());
            journal.setDate(System.currentTimeMillis());
            journalService.save(journal);
        }

        return "redirect:/journal";
    }

    @GetMapping("/edit/{journalId}")
    public String showEditJournal(@PathVariable String journalId, Model model, Principal principal, HttpSession session) {
        model.addAttribute("contactRequest", new ContactRequest());

        Journal journal = journalService.findById(journalId);

        if (journal.getUsername().equals(principal.getName())) {
            model.addAttribute("journal", journal);
            session.setAttribute("currentJournal", journal);
            return "editjournal";
        }

        return "redirect:/journal";
    }

    @PostMapping("/edit")
    public String editJournal(@Valid @ModelAttribute("journal") final Journal editedJournal, final BindingResult result, Model model, Principal principal, HttpSession session) {
        model.addAttribute("contactRequest", new ContactRequest());

        if (result.hasErrors())
            return "error";

        if (editedJournal.getJournalName().equals("")) {
            model.addAttribute(STATUS_ATTRIBUTE_NAME, STATUSCODE_EMPTYFORM);
        } else if (editedJournal.getJournalName().length() > 100) {
            model.addAttribute(STATUS_ATTRIBUTE_NAME, STATUSCODE_MODAL_TEMP);
            model.addAttribute(STATUSCODE_MODAL_HEADER, "Journalname too long!");
            model.addAttribute(STATUSCODE_MODAL_BODY, "Please enter an shorter Journalname.");
        } else if (editedJournal.getContent().length() > JOURNAL_CONTENT_SIZE) {
            model.addAttribute(STATUS_ATTRIBUTE_NAME, STATUSCODE_MODAL_TEMP);
            model.addAttribute(STATUSCODE_MODAL_HEADER, "Content too long!");
            model.addAttribute(STATUSCODE_MODAL_BODY, "Please make your content shorter.");
        } else if (session.getAttribute("currentJournal") != null) {
            Journal oldJournal = (Journal) session.getAttribute("currentJournal");

            if (oldJournal.getUsername().equals(principal.getName())) {
                editedJournal.setJournalid(oldJournal.getJournalid());
                editedJournal.setUsername(oldJournal.getUsername());
                editedJournal.setDate(System.currentTimeMillis());
                journalService.update(editedJournal);
            }
        }

        return "redirect:/journal";
    }

    @GetMapping("/delete")
    public String delete(Model model, HttpSession session) {
        Journal journal = (Journal) session.getAttribute("currentJournal");
        model.addAttribute("delete", "true");
        model.addAttribute("journal", journal);
        model.addAttribute("contactRequest", new ContactRequest());

        return "editjournal";
    }

    @PostMapping("/delete")
    public String deleteConfirm(Model model, HttpSession session) {
        model.addAttribute("contactRequest", new ContactRequest());

        Journal oldJournal = (Journal) session.getAttribute("currentJournal");
        journalService.deleteById(oldJournal.getJournalid());
        session.removeAttribute("currentJournal");

        return "redirect:/journal";
    }

}
