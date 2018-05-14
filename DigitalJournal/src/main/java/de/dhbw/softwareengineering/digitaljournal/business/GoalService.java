package de.dhbw.softwareengineering.digitaljournal.business;

import de.dhbw.softwareengineering.digitaljournal.domain.Goal;
import de.dhbw.softwareengineering.digitaljournal.domain.Journal;
import de.dhbw.softwareengineering.digitaljournal.domain.form.CreateGoal;
import de.dhbw.softwareengineering.digitaljournal.persistence.GoalRepository;
import de.dhbw.softwareengineering.digitaljournal.util.UUIDGenerator;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static de.dhbw.softwareengineering.digitaljournal.util.Constants.calculateDaysLeft;
import static org.unbescape.html.HtmlEscape.escapeHtml5;

@Service
public class GoalService extends AbstractService{

    private final GoalRepository repository;

    public GoalService(GoalRepository repository) {
        this.repository = repository;
    }

    public void save(@Valid CreateGoal goalForm, Principal principal) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = formatter.parse(goalForm.getDate());

            Goal goal = new Goal();
                 goal.setId(UUIDGenerator.generateUniqueUUID(repository));
                 goal.setUsername(principal.getName());
                 goal.setDate(date.getTime());
                 goal.setName(goalForm.getName());
                 goal.setDescription(goalForm.getDescription());
                 goal.setDaysLeft(calculateDaysLeft(System.currentTimeMillis(), date.getTime()));

            repository.save(goal);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public Goal getById(String goalID) {
        Optional<Goal> goalOptional = repository.findById(goalID);

        if (goalOptional.isPresent()) {
            Goal goal = goalOptional.get();
                 goal.setDaysLeft(calculateDaysLeft(System.currentTimeMillis(), goal.getDate()));

            return goal;
        } else {
            throw new RuntimeException("No goal found with Id: " + goalID);
        }
    }

    public List<Goal> findAll(String name) {
        List<Goal> goals = repository.findAllByUsernameOrderByDateDesc(name);

        for(Goal goal : goals){
            goal.setDaysLeft(calculateDaysLeft(System.currentTimeMillis(), goal.getDate()));
            goal.setDescription(escapeHtml5(goal.getDescription()).replaceAll("\n", "<br/>"));
            goal.setName(escapeHtml5(goal.getName()));
        }

        return goals;
    }

    public void deleteById(String goalId) {
        repository.deleteById(goalId);
    }
}
