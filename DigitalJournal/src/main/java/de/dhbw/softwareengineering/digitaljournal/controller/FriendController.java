package de.dhbw.softwareengineering.digitaljournal.controller;

import de.dhbw.softwareengineering.digitaljournal.business.GoalService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/journal/friends")
public class FriendController {
    private final GoalService goalService;

    public FriendController(GoalService goalService) {
        this.goalService = goalService;
    }


}
