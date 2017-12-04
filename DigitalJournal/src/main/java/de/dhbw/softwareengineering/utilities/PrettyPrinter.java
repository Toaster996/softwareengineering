package de.dhbw.softwareengineering.utilities;

import org.hibernate.Hibernate;

import java.lang.reflect.Field;

/**
 * @author straub-florian
 * <p>
 * Pretty prints all kinds of stuff!
 */
public class PrettyPrinter {

    /**
     * the position of the method caller on the callstack
     */
    private static final int CALLER_STACK_POS = 3;

    /**
     * Transforms a given object to a JSON-style-formatted string.
     *
     * @param object the object to pretty print
     * @return a formatted String
     */
    public String formatObject(Object object) {
        // Get the object's classname
        String result = String.format("%s {\n", object.getClass().getSimpleName());
        int longestFieldName = -1;

        // Calculate the longest field name
        for (Field field : object.getClass().getDeclaredFields()) {
            if (field.getName().length() > longestFieldName) {
                longestFieldName = field.getName().length();
            }
        }

        // Use Refelction to get all fields and their current values
        for (Field field : object.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            Object value = null;
            try {
                value = field.get(object);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

            result += (String.format("\t%s%s = %s\n", field.getName(), repeat(" ", longestFieldName - field.getName().length()), value));
        }
        result += "}";
        return result;
    }

    /**
     * Repeats the given text by the given amount.
     *
     * @param text   what to repeat
     * @param amount how often to repeat
     * @return the given text 'amount' times
     */
    private String repeat(String text, int amount) {
        String result = "";
        for (int i = 0; i < amount; i++) result += text;
        return result;
    }

    /**
     * Outputs a given message to the console
     *
     * @param msg the message to output
     */
    public void info(String msg) {
        Class<?> class_ = getCallerClass();
        System.out.println(String.format("[%s] %s", class_ != null ? getCallerClass().getSimpleName() : "Unknown caller", msg));
    }

    /**
     * Outputs a given exception to the console
     *
     * @param exc the exception to output
     */
    public void error(Exception exc) {
        Class<?> class_ = getCallerClass();
        System.err.println(String.format("[%s] %s", class_ != null ? getCallerClass().getSimpleName() : "Unknown caller", exc.getMessage()));
    }

    /**
     * @return the caller of the method
     */
    private static Class<?> getCallerClass() {
        final StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();

        if (stackTrace != null && stackTrace[CALLER_STACK_POS] != null) {
            String clazzName = stackTrace[CALLER_STACK_POS].getClassName();
            try {
                return Class.forName(clazzName);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                return null;
            }
        } else {
            return null;
        }
    }
}
