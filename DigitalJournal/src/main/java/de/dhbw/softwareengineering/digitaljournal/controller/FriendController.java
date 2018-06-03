package de.dhbw.softwareengineering.digitaljournal.controller;

import de.dhbw.softwareengineering.digitaljournal.business.FriendService;
import de.dhbw.softwareengineering.digitaljournal.business.UserService;
import de.dhbw.softwareengineering.digitaljournal.domain.User;
import de.dhbw.softwareengineering.digitaljournal.domain.form.CreateFriend;
import de.dhbw.softwareengineering.digitaljournal.util.AjaxResponseBody;
import de.dhbw.softwareengineering.digitaljournal.util.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static de.dhbw.softwareengineering.digitaljournal.util.Constants.STATUS_ATTRIBUTE_NAME;

@Slf4j
@Controller
@RequestMapping("/journal/friends")
public class FriendController {
    private final FriendService friendService;
    private final UserService userService;

    public FriendController(FriendService friendService, UserService userService) {
        this.friendService = friendService;
        this.userService = userService;
    }

    @GetMapping(value = "/add")
    public String openModalNewGoal(RedirectAttributes redir) {
        redir.addFlashAttribute(STATUS_ATTRIBUTE_NAME, "addFriends");
        redir.addFlashAttribute("newFriend", new CreateFriend());
        return Constants.REDIRECT_JOURNAl;
    }

    @ResponseBody
    @PostMapping("/suggest/{username}")
    public List<String> getSearchResultViaAjax(@PathVariable(required = false) String username) {
       return userService.findSuggestionsByName(username);
    }


    @PostMapping(value = "/add")
    public String submit(@Valid @ModelAttribute("friend") final CreateFriend createFriend, final BindingResult result, final Model model, Principal principal) {
        if (result.hasErrors())
            return "error";

        friendService.save(createFriend, principal, userService);
        return Constants.REDIRECT_JOURNAl;
    }
}
