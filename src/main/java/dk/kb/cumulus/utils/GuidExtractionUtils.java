package dk.kb.cumulus.utils;

/**
 * Handles the extraction of GUIDs from the potentially old and invalid format.
 */
public final class GuidExtractionUtils {
    /** Constructor for this Utility class.*/
    protected GuidExtractionUtils() {}

    /**
     * Method for extracting the part of the KB-GUID which is valid as a 'xs:ID' standardized guid.
     * 
     * @param guid The GUID for the system.
     * @return The extracted GUID.
     */
    public static String extractGuid(String guid) {
        if(guid == null || guid.isEmpty()) {
            throw new IllegalArgumentException("A GUID must be defined.");
        }

        String res;
        if(guid.contains("/")) {
            String[] guidParts = guid.split("[/]");
            res = guidParts[guidParts.length-1];
        } else {
            res = guid;
        }
        
        if(res.contains("#")) {
            String[] guidParts = res.split("[#]");
            res = guidParts[0];            
        }
        
        return res;
    }
}
