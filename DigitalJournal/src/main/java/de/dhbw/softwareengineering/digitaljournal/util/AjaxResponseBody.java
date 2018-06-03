package de.dhbw.softwareengineering.digitaljournal.util;

import de.dhbw.softwareengineering.digitaljournal.domain.User;
import lombok.Data;

import java.util.List;

@Data
public class AjaxResponseBody {

    String msg;
    List<User> result;

}
