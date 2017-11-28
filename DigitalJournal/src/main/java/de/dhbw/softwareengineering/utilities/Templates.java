package de.dhbw.softwareengineering.utilities;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Templates {

    private static Templates instance;

    public String getTemplate(String templateName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(Constants.TEMPLATE_DIRECTORY + File.separator + templateName))) {
            StringBuilder builder = new StringBuilder();
            while (reader.ready()) {
                builder.append(reader.readLine());
            }
            return builder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "error";
    }

    public static Templates getInstance(){
        if(instance == null){
            instance = new Templates();
        }
        return instance;
    }
}
