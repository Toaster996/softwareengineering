package de.dhbw.softwareengineering.digitaljournal.util;

import de.dhbw.softwareengineering.digitaljournal.util.Tasks.Task;

import java.util.ArrayList;
import java.util.List;

public abstract class Scheduler {
    private List<Task> tasks = new ArrayList<>();
    public void addTask(Task task){
        tasks.add(task);
    }
    public void removeTask(Task task){
        tasks.remove(task);
    }

}
