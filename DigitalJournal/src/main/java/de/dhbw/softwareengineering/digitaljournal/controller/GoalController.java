package de.dhbw.softwareengineering.digitaljournal.controller;

import de.dhbw.softwareengineering.digitaljournal.business.GoalService;
import de.dhbw.softwareengineering.digitaljournal.domain.ContactRequest;
import de.dhbw.softwareengineering.digitaljournal.domain.Goal;
import de.dhbw.softwareengineering.digitaljournal.domain.form.CreateGoal;
import de.dhbw.softwareengineering.digitaljournal.util.Constants;
import de.dhbw.softwareengineering.digitaljournal.util.exceptions.GoalNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

import static de.dhbw.softwareengineering.digitaljournal.util.Constants.STATUSCODE_EMPTYFORM;
import static de.dhbw.softwareengineering.digitaljournal.util.Constants.STATUSCODE_MODAL_BODY;
import static de.dhbw.softwareengineering.digitaljournal.util.Constants.STATUSCODE_MODAL_HEADER;
import static de.dhbw.softwareengineering.digitaljournal.util.Constants.STATUSCODE_MODAL_TEMP;
import static de.dhbw.softwareengineering.digitaljournal.util.Constants.STATUS_ATTRIBUTE_NAME;

@Slf4j
@Controller
@RequestMapping("/journal/goal")
public class GoalController {

    private final GoalService goalService;
    private static final int NUMBER_OF_LATESTS_GOALS = 4;
    private int loadedGoals = NUMBER_OF_LATESTS_GOALS;

    public GoalController(GoalService goalService) {
        this.goalService = goalService;
    }

    @GetMapping(value = "/create")
    public String openModalNewGoal(RedirectAttributes redir) {
        redir.addFlashAttribute(STATUS_ATTRIBUTE_NAME, "createGoal");
        return Constants.REDIRECT_JOURNAL;
    }

    @PostMapping(value = "/create")
    public String submit(@Valid @ModelAttribute("goal") final CreateGoal goal, final BindingResult result, final Model model, Principal principal) {
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

        return Constants.REDIRECT_JOURNAL;
    }

    @PostMapping("/delete/{goalId}")
    public String delete(@PathVariable String goalId, Principal principal) {
        try {
            Goal goal = goalService.getById(goalId);
            if (goal.getUsername().equals(principal.getName())) {
                goalService.deleteById(goalId);
            }
        } catch (GoalNotFoundException e) {
            log.error(e.getMessage());
        }

        return Constants.REDIRECT_JOURNAL;
    }

    @GetMapping("/edit/{goalId}")
    public String editGoal(@PathVariable String goalId, RedirectAttributes redir, Principal principal, HttpSession session) {
        try {
            Goal goal = goalService.getById(goalId);

            if (goal.getUsername().equals(principal.getName())) {
                redir.addFlashAttribute(STATUS_ATTRIBUTE_NAME, "editGoal");
                redir.addFlashAttribute("editGoal", goal);
                session.setAttribute("currentGoal", goal);
            }
        } catch (GoalNotFoundException e) {
            log.error(e.getMessage());
        }

        return Constants.REDIRECT_JOURNAL;
    }

    @PostMapping("/edit")
    public String editGoal(@Valid @ModelAttribute("goal") final CreateGoal goal, HttpSession session) {
        Goal oldGoal = (Goal) session.getAttribute("currentGoal");
        goalService.update(oldGoal, goal);
        return Constants.REDIRECT_JOURNAL;
    }

    @GetMapping("/{goalId}")
    public String showGoal(@PathVariable String goalId, Model model, RedirectAttributes redir, Principal principal) {
        model.addAttribute("contactRequest", new ContactRequest());

        try {
            Goal goal = goalService.getById(goalId);

            if (goal.getUsername().equals(principal.getName())) {
                redir.addFlashAttribute(STATUS_ATTRIBUTE_NAME, "showGoal");
                redir.addFlashAttribute("showGoal", goal);
            }
        } catch (GoalNotFoundException e) {
            log.error(e.getMessage());
        }

        return Constants.REDIRECT_JOURNAL;
    }

    @GetMapping("/check/{goalId}")
    public String checkGoal(@PathVariable String goalId, Principal principal) {
        try {
            Goal goal = goalService.getById(goalId);

            if (goal.getUsername().equals(principal.getName())) {
                goalService.checkByID(goalId);
            }
        } catch (GoalNotFoundException e) {
            log.error(e.getMessage());
        }

        return Constants.REDIRECT_JOURNAL;
    }

    @GetMapping("/allgoals")
    public String showAllGoals(Principal principal, RedirectAttributes redir) {
        List<Goal> goals = goalService.findAll(principal.getName());
        goals = removeNotShownGoals(goals);
        redir.addFlashAttribute("goals", goals);
        if (goals.size() <= loadedGoals)
            redir.addFlashAttribute(Constants.SHOW_FURTHER_GOALS_BTN, false);
        return Constants.REDIRECT_JOURNAL;
    }

    private List<Goal> removeNotShownGoals(List<Goal> goals) {
        loadedGoals += NUMBER_OF_LATESTS_GOALS;
        if (goals.size() > loadedGoals) {
            while (goals.size() > loadedGoals) {
                goals.remove(loadedGoals);
            }
        }
        return goals;
    }
}
