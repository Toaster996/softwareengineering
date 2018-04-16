package de.dhbw.softwareengineering.digitaljournal.domain.form;

import lombok.Data;

@Data
public class CreateGoal {

    private String name;
    private String description;
    private String date;

}
