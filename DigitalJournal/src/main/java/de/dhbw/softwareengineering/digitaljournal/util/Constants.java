package de.dhbw.softwareengineering.digitaljournal.util;

import java.util.regex.Pattern;

public class Constants {

    public static final Pattern emailPattern    = Pattern.compile("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])");
    public static final Pattern usernamePattern = Pattern.compile("[a-zA-Z0-9]+");

    public static final int SECOND  = 1000;
    public static final int MINUTE  = 60 * SECOND;
    public static final int HOUR    = 60 * MINUTE;

    public static final int JOURNAL_CONTENT_SIZE = 2048;

    public static final String REDIRECT         = "redirect:/";
    public static final String REDIRECT_JOURNAL = "redirect:/journal";

    public static final String SESSION_CHANGEPWUSER            = "changePasswordUser";
    public static final String SESSION_CURRENTJOURNAL          = "currentJournal";
    public static final String SESSION_CONTACTREQUEST          = "contactRequest";
    public static final String SESSION_REGISTRATIONUSER        = "registrationUser";
    public static final String SESSION_LOGINUSER               = "loginUser";
    public static final String SESSION_GOAL                    = "goal";
    public static final String SESSION_GOALS                   = "goals";
    public static final String SESSION_JOURNAL                 = "journal";
    public static final String SESSION_JOURNALS                = "journals";


    public static final String FLASHATTRIBUTE_TEMP_MODAL_HEADER = "temp_modal_header";
    public static final String FLASHATTRIBUTE_TEMP_MODAL_BODY   = "temp_modal_body";
    public static final String FLASHATTRIBUTE_MODAL_GEN_ID      = "modal_gen_id";

    public static final String STATUS_ATTRIBUTE_NAME           = "status";
    public static final String STATUS_RESPONSE_ATTRIBUTE_NAME  = "response";
    public static final String STATUS_REQUEST_ATTRIBUTE_NAME   = "contactStatus" ;

    public static final String STATUSCODE_ALPHANUMERIC         = "alphanumeric";
    public static final String STATUSCODE_PWMISSMATCH          = "pwmissmatch";
    public static final String STATUSCODE_USERNAMETOOLONG      = "nametoolong";
    public static final String STATUSCODE_EMAILTOOLONG         = "emailtoolong";
    public static final String STATUSCODE_EMPTYFORM            = "emptyform";
    public static final String STATUSCODE_SUCREG               = "sucreg";
    public static final String STATUSCODE_PWTOOSHORT           = "pwtooshort";
    public static final String STATUSCODE_PWTOOLONG            = "pwtoolong";
    public static final String STATUSCODE_EMAILINVALID         = "emailinvalid";
    public static final String STATUSCODE_EMAILALREADYINUSE    = "usedemail";
    public static final String STATUSCODE_USERNAMEALREADYINUSE = "useduser";
    public static final String STATUSCODE_MODAL_TEMP           = "temp_modal";
    public static final String STATUSCODE_PWCHANGESUCCESS      = "pwchangesuccess";
    public static final String STATUSCODE_MODAL_HEADER         = "temp_modal_header";
    public static final String STATUSCODE_MODAL_BODY           = "temp_modal_body";
    public static final String STATUSCODE_REQUESTSENT          = "requestSent";
    public static final String STATUSCODE_INVALID_CREDENTIALS  = "invalidCredentials";
    public static final String STATUSCODE_SUCCESS              = "success";
    public static final String STATUSCODE_REQUEST_FAILED       = "requestfailed";

    public static final String SHOW_FURTHER_GOALS_BTN = "showNextGoals";

    public static final String BASE_URL = "http://localhost:8080";

    public static final String SUPPORT_RECIPIENT = "straub.flo@web.de";
    public static final String SESSION_IMAGES = "images";
    public static final String URL_UNAUTHORIZED = "/unauthorized";
    public static final String SESSION_SHARE_JOURNAL = "shareJournalID";
    public static final String TEMPLATE_PROFILE = "profile";
    public static final String TEMPLATE_RECOVER = "recover";

    public static int calculateDaysLeft(long current, long date){
        long remaining = date - current;
        if(remaining < 0) return -1;
        return (int) (remaining/1000/60/60/24);
    }
}
