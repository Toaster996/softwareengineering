package de.dhbw.softwareengineering.utilities;

import java.io.File;

public class GeneralConfiguration extends FileConfiguration {

    private static GeneralConfiguration instance;

    private GeneralConfiguration() {
        super(new File("." + File.separator + "conf" + File.separator + "general.conf"));

        this.setDefaultValue("domain", "localhost:8080");
    }

    public static GeneralConfiguration getInstance() {
        if(instance == null){
            instance = new GeneralConfiguration();
        }
        return instance;
    }
}
