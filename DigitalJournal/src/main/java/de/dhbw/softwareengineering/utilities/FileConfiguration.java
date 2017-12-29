package de.dhbw.softwareengineering.utilities;

import java.io.*;
import java.util.*;

public class FileConfiguration {

    private File file;

    private HashMap<String, String> bufferedValues = new HashMap<>();

    public FileConfiguration(File file) {

        this.file = file;
        loadFile();

    }


    public int getInt(String identifier) throws NumberFormatException {
        return Integer.parseInt(bufferedValues.get(identifier));
    }

    public long getLong(String identifier) throws NumberFormatException {
        return Long.parseLong(bufferedValues.get(identifier));
    }

    public double getDouble(String identifier) throws NumberFormatException {
        return Double.parseDouble(bufferedValues.get(identifier));
    }

    public float getFloat(String identifier) throws NumberFormatException {
        return Float.parseFloat(bufferedValues.get(identifier));
    }

    public String getString(String identifier) {
        return bufferedValues.get(identifier);
    }

    public boolean exists(String identifier) {
        return bufferedValues.containsKey(identifier);
    }

    private void loadFile() {
        if (file.exists() && file.isFile()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                for (int i = 0; reader.ready(); i++) {
                    String line = reader.readLine();
                    String[] split = line.split("=");
                    if (split.length == 2) {
                        String identifier = split[0];
                        String value = split[1];

                        if (identifier.trim().equals("") || value.trim().equals("")) {
                            Constants.prettyPrinter.error(new Exception("Invalid line " + i + " in FileConfiguration \"" + file.getName() + "\": " + line));
                        } else {
                            if (bufferedValues.containsKey(identifier)) {
                                Constants.prettyPrinter.error(new Exception("Duplicate of identifier \"" + identifier + "\"" + " in FileConfiguration \"" + file.getName() + "\": " + line));
                            } else {
                                bufferedValues.put(identifier, value);
                            }
                        }
                    } else {
                        Constants.prettyPrinter.error(new Exception("Invalid line " + i + " in FileConfiguration \"" + file.getName() + "\": " + line));
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void reload() {
        loadFile();
    }

    public void save() throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(file, false));
        Iterator<Map.Entry<String, String>> iterator = bufferedValues.entrySet().iterator();

        while (iterator.hasNext()) {
            Map.Entry<String, String> entry = iterator.next();
            writer.write(entry.getKey() + "=" + entry.getValue());
            writer.newLine();
        }
        writer.flush();
        writer.close();

    }

    public void setValue(String identifier, String value){
        bufferedValues.put(identifier, value);
    }

    public void setDefaultValue(String identifier, String value) {
        if (!bufferedValues.containsKey(identifier)) {
            bufferedValues.put(identifier, value);
        }
    }


}
