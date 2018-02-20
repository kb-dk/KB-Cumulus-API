package dk.kb.cumulus.utils;

import java.util.List;

/**
 * Utility class for dealings with Strings.
 */
public class StringUtils {
    /** The result of an empty string.*/
    protected static final String EMPTY_LIST = "[]";
    
    /**
     * Replaces all spaces with tabs.
     * @param s The string to have its spaces replaced by tabs.
     * @return The string where all the spaces have been replaced by tabs.
     */
    public static String replaceSpacesToTabs(String s) {
        ArgumentCheck.checkNotNullOrEmpty(s, "String s");
        return s.replaceAll(" ", "\t");
    }
    
    /**
     * Method for changing a list of strings, into a single string.
     * @param list The list of string.
     * @param separator The separator between each string in the list.
     * @return The strings in the list combined to a single string, but separated by the separator.
     */
    public static String listToString(List<String> list, String separator) {
        if(list == null || list.isEmpty()) {
            return EMPTY_LIST;
        }
        StringBuilder res = new StringBuilder();
        for(String s : list) {
            res.append(s);
            res.append(separator);
        }
        return res.toString();
    }

    /**
     * XML encoding a string.
     * Replaces all the illegal characters with encoded ones.
     * 
     * FIXME:
     * Tried to use apache commons-lang utility class,
     * but it seems to replace danish characters with odd HTML tags.
     * 
     * @param s The string to encode.
     * @return The encoded string.
     */
    public static String xmlEncode(String s) {
        s = s.replace("<", "&lt;");
        s = s.replace(">", "&gt;");
        return s;
    }
    
    /**
     * Checks whether or not a given string is either null or empty.
     * @param s The string to check.
     * @return True if the string is null or empty, false otherwise.
     */
    public static boolean isNullOrEmpty(String s) {
        return s == null || s.isEmpty();
    }
}
