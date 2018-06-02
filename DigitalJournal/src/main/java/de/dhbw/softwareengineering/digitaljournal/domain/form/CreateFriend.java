package de.dhbw.softwareengineering.digitaljournal.domain.form;

import lombok.Data;

@Data
public class CreateFriend {
    String username;

    public CreateFriend(){}

    public CreateFriend(String username) {
        this.username = username;
    }
}
