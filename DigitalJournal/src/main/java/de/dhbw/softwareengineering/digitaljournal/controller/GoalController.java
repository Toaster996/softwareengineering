package de.dhbw.softwareengineering.digitaljournal.controller;

import de.dhbw.softwareengineering.digitaljournal.business.GoalService;
import de.dhbw.softwareengineering.digitaljournal.domain.ContactRequest;
import de.dhbw.softwareengineering.digitaljournal.domain.Goal;
import de.dhbw.softwareengineering.digitaljournal.domain.form.CreateGoal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import java.security.Principal;

import static de.dhbw.softwareengineering.digitaljournal.util.Constants.STATUSCODE_EMPTYFORM;
import static de.dhbw.softwareengineering.digitaljournal.util.Constants.STATUSCODE_MODAL_BODY;
import static de.dhbw.softwareengineering.digitaljournal.util.Constants.STATUSCODE_MODAL_HEADER;
import static de.dhbw.softwareengineering.digitaljournal.util.Constants.STATUSCODE_MODAL_TEMP;
import static de.dhbw.softwareengineering.digitaljournal.util.Constants.STATUS_ATTRIBUTE_NAME;

@Controller
@RequestMapping("/journal/goal")
public class GoalController {

    private final GoalService goalService;

    public GoalController(GoalService goalService) {
        this.goalService = goalService;
    }

    @GetMapping(value = "/create")
    public String openModalNewGoal(Model model, RedirectAttributes redir) {
        model.addAttribute("contactRequest",new ContactRequest());

        redir.addFlashAttribute(STATUS_ATTRIBUTE_NAME, "createGoal");
        return "redirect:/journal";
    }

    @PostMapping(value = "/create")
    public String submit(@Valid @ModelAttribute("goal") final CreateGoal goal, final BindingResult result, final Model model, Principal principal) {
        model.addAttribute("contactRequest",new ContactRequest());

        if (result.hasErrors())
            return "error";
        else if (goal.getName().equals("") || goal.getDescription().equals(""))
            model.addAttribute(STATUS_ATTRIBUTE_NAME, STATUSCODE_EMPTYFORM);
        else if (goal.getName().length() > 100) {
            model.addAttribute(STATUS_ATTRIBUTE_NAME, STATUSCODE_MODAL_TEMP);
            model.addAttribute(STATUSCODE_MODAL_HEADER, "Goal name to long!");
            model.addAttribute(STATUSCODE_MODAL_BODY, "Please enter an shorter goal name.");
        } else {
            goalService.save(goal, principal);
        }

        return "redirect:/journal";
    }

    @PostMapping("/delete/{goalId}")
    public String delete(@PathVariable String goalId, Model model, Principal principal) {
        model.addAttribute("contactRequest",new ContactRequest());

        Goal goal = goalService.getById(goalId);

        if (goal.getUsername().equals(principal.getName())) {
            goalService.deleteById(goalId);
        }

        return "redirect:/journal";
    }

    @GetMapping("/{goalId}")
    public String showGoal(@PathVariable String goalId, Model model, RedirectAttributes redir, Principal principal) {
        model.addAttribute("contactRequest",new ContactRequest());

        Goal goal = goalService.getById(goalId);

        if (goal.getUsername().equals(principal.getName())) {
            redir.addFlashAttribute(STATUS_ATTRIBUTE_NAME, "showGoal");
            redir.addFlashAttribute("showGoal", goal);
        }

        return "redirect:/journal";
    }
}
