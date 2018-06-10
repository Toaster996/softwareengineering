package de.dhbw.softwareengineering.digitaljournal.business;

import de.dhbw.softwareengineering.digitaljournal.domain.Goal;
import de.dhbw.softwareengineering.digitaljournal.domain.form.CreateGoal;
import de.dhbw.softwareengineering.digitaljournal.persistence.GoalRepository;
import de.dhbw.softwareengineering.digitaljournal.util.UUIDGenerator;
import de.dhbw.softwareengineering.digitaljournal.util.exceptions.GoalNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static de.dhbw.softwareengineering.digitaljournal.util.Constants.calculateDaysLeft;
import static org.unbescape.html.HtmlEscape.escapeHtml5;

@Slf4j
@Service
public class GoalService implements AbstractService {

    private final GoalRepository repository;
    private static final int NUMBER_OF_LATESTS_GOALS = 4;

    public GoalService(GoalRepository repository) {
        this.repository = repository;
    }

    public void save(@Valid CreateGoal goalForm, Principal principal) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date;
        try {
            date = formatter.parse(goalForm.getDate());

            Goal goal = new Goal();
            goal.setId(UUIDGenerator.generateUniqueUUID(repository));
            goal.setUsername(escapeHtml5(principal.getName()));
            goal.setDate(date.getTime());
            goal.setName(escapeHtml5(goalForm.getName()));
            goal.setDescription(escapeHtml5(goalForm.getDescription()));
            goal.setDaysLeft(calculateDaysLeft(System.currentTimeMillis(), date.getTime()));

            repository.save(goal);
        } catch (ParseException e) {
            logException(e);
        }
    }

    public void update(Goal oldGoal, CreateGoal goal) {
        if (!goal.getName().equals(""))
            oldGoal.setName(escapeHtml5(goal.getName()));
        if (!goal.getDescription().equals(""))
            oldGoal.setDescription(escapeHtml5(goal.getDescription()));

        if (!goal.getDate().equals("")) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date date;
            try {
                date = formatter.parse(goal.getDate());
                oldGoal.setDate(date.getTime());
                oldGoal.setDaysLeft(calculateDaysLeft(System.currentTimeMillis(), date.getTime()));

            } catch (ParseException e) {
                logException(e);
            }
        }
        repository.save(oldGoal);
    }

    public Goal getById(String goalID) throws GoalNotFoundException {
        Optional<Goal> goalOptional = repository.findById(goalID);

        if (goalOptional.isPresent()) {
            Goal goal = goalOptional.get();
            goal.setDaysLeft(calculateDaysLeft(System.currentTimeMillis(), goal.getDate()));

            return goal;
        } else {
            throw new GoalNotFoundException(goalID);
        }
    }

    public List<Goal> findAll(String name) {
        List<Goal> goals = repository.findAllByUsernameOrderByDateDesc(name);
        goals = goals.stream().sorted(Comparator.comparingInt(Goal::getDaysLeft)).collect(Collectors.toList());

        for (Goal goal : goals) {
            goal.setDaysLeft(calculateDaysLeft(System.currentTimeMillis(), goal.getDate()));
            goal.setDescription(goal.getDescription());
            goal.setName(goal.getName());
        }
        return goals;
    }

    public List<Goal> findLatestsGoals(String name) {
        List<Goal> goals = repository.findAllByUsernameOrderByDateDesc(name);
        goals = goals.stream().sorted(Comparator.comparingInt(Goal::getDaysLeft)).collect(Collectors.toList());
        while (goals.size() > NUMBER_OF_LATESTS_GOALS) {
            goals.remove(NUMBER_OF_LATESTS_GOALS);
        }

        for (Goal goal : goals) {
            int daysleft = calculateDaysLeft(System.currentTimeMillis(), goal.getDate());
            goal.setDaysLeft(daysleft);
            goal.setDescription(goal.getDescription());
            goal.setName(goal.getName());
        }

        return goals;
    }

    public void deleteById(String goalId) {
        repository.deleteById(goalId);
    }

    public void checkByID(String goalID) {
        try {
            Goal goal = this.getById(goalID);
            goal.setChecked(true);
            repository.save(goal);
        } catch (GoalNotFoundException e) {
            log.error(e.getMessage());
        }
    }

    public int getActiveGoals(String name) {
        List<Goal> goals = repository.findAllByUsernameOrderByDateDesc(name);
        AtomicInteger activeGoals = new AtomicInteger();
        goals.forEach(goal -> {
            if (goal.getDaysLeft() >= 0 && !goal.isChecked())
                activeGoals.getAndIncrement();
        });
        return activeGoals.get();
    }

    public boolean hasNotnotifiedGoals(String name) {
        List<Goal> goals = repository.findAllByUsernameOrderByDateDesc(name);
        boolean notify = false;
        for (Goal goal : goals) {
            if (goal.getDaysLeft() < 0 && !goal.isChecked() && !goal.isHasBeenNotified()) {
                goal.setHasBeenNotified(true);
                repository.save(goal);
                notify = true;
            }
        }
        return notify;
    }

    private void logException(Exception e) {
        log.error(e.getMessage());
        StringWriter stringWriter = new StringWriter();
        e.printStackTrace(new PrintWriter(stringWriter));
        log.error(stringWriter.toString());
    }

    public void deleteAllFromUser(String username) {
        repository.deleteAllByUsername(username);
    }
}
