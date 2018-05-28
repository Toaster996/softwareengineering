package de.dhbw.softwareengineering.digitaljournal.controller;

import de.dhbw.softwareengineering.digitaljournal.business.GoalService;
import de.dhbw.softwareengineering.digitaljournal.domain.ContactRequest;
import de.dhbw.softwareengineering.digitaljournal.domain.Goal;
import de.dhbw.softwareengineering.digitaljournal.domain.form.CreateGoal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;

import static de.dhbw.softwareengineering.digitaljournal.util.Constants.*;

@Slf4j
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
        Goal goal = goalService.getById(goalId);

        if (goal.getUsername().equals(principal.getName())) {
            goalService.deleteById(goalId);
        }

        return "redirect:/journal";
    }

    @GetMapping("/edit/{goalId}")
    public String editGoal(@PathVariable String goalId, Model model, RedirectAttributes redir, Principal principal) {
        Goal goal = goalService.getById(goalId);

        if (goal.getUsername().equals(principal.getName())) {
            redir.addFlashAttribute(STATUS_ATTRIBUTE_NAME, "editGoal");
            redir.addFlashAttribute("editGoal", goal);
        }
        return "redirect:/journal";
    }

    @PostMapping("/edit")
    public String editGoal(@Valid @ModelAttribute("goal") final CreateGoal goal, Model model, Principal principal) {
        System.out.println(goal.getName());
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

    @GetMapping("/check/{goalId}")
    public String checkGoal(@PathVariable String goalId, Model model, RedirectAttributes redir, Principal principal) {
        Goal goal = goalService.getById(goalId);

        if (goal.getUsername().equals(principal.getName())) {
           //TODO goalService.checkByID(goalId);
        }
        return "redirect:/journal";
    }
}
