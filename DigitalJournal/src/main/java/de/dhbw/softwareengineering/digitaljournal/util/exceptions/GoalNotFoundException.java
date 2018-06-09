package de.dhbw.softwareengineering.digitaljournal.util.exceptions;

public class GoalNotFoundException extends RuntimeException{

    public GoalNotFoundException(String id){
        super("No goal found with id: " + id);
    }

}
