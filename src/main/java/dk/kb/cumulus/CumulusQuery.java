package dk.kb.cumulus;

import java.util.EnumSet;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.canto.cumulus.constants.CombineMode;
import com.canto.cumulus.constants.FindFlag;

import dk.kb.cumulus.utils.ArgumentCheck;
import dk.kb.cumulus.utils.StringUtils;

/**
 * Class for encapsulating the query for locating specific items in Cumulus.
 */
public class CumulusQuery {
    /** The logger.*/
    private static final Logger log = LoggerFactory.getLogger(CumulusQuery.class);

    /** The query.*/
    protected final String query;
    /** The flags.*/
    protected final EnumSet<FindFlag> findFlags;
    /** The combine mode.*/
    protected final CombineMode combineMode;
    /** The locale. Defaults to null.*/
    protected Locale locale;

    /**
     * Constructor. 
     * Automatically sets the locale to null.
     * @param query The query.
     * @param findFlags The flags.
     * @param combineMode The combine mode.
     */
    public CumulusQuery(String query, EnumSet<FindFlag> findFlags, CombineMode combineMode) {
        ArgumentCheck.checkNotNullOrEmpty(query, "String query");
        ArgumentCheck.checkNotNullOrEmpty(findFlags, "EnumSet<FindFlag> findFlags");
        ArgumentCheck.checkNotNull(combineMode, "CombineMode combineMode");
        this.query = query;
        this.findFlags = findFlags;
        this.combineMode = combineMode;
        this.locale = null;
        
        log.debug("Instantiated Cumulus query '" + query + "' with flags, '" + findFlags + "' and combine-mode: '" 
                + combineMode.name() + "'");
    }

    /** @return The query string. */
    public String getQuery() {
        return query;
    }

    /** @return The enum set of flags for the query. */
    public EnumSet<FindFlag> getFindFlags() {
        return findFlags;
    }

    /** @return The combine mode.*/
    public CombineMode getCombineMode() {
        return combineMode;
    }

    /** @return The locale. This can be null.*/
    public Locale getLocale() {
        return locale;
    }

    /**
     * @param locale The new value for the locale.
     */
    public void setLocale(Locale locale) {
        this.locale = locale;
    }
    
    /**
     * The query for extracting all the records in a Cumulus catalog.
     * 
     * The records which have the registration state 'registration finished', and it must belong to the given catalog.
     * 
     * @param catalogName The name of the catalog.
     * @return The query for all the records in a Cumulus catalog.
     */
    public static CumulusQuery getQueryForAllInCatalog(String catalogName) {
        ArgumentCheck.checkNotNullOrEmpty(catalogName, "String catalogName");
        String query = String.format(StringUtils.replaceSpacesToTabs("%s is %s"),
                Constants.FieldNames.CATALOG_NAME,
                catalogName);        
        EnumSet<FindFlag> findFlags = EnumSet.of(
                FindFlag.FIND_MISSING_FIELDS_ARE_ERROR, 
                FindFlag.FIND_MISSING_STRING_LIST_VALUES_ARE_ERROR);    

        return new CumulusQuery(query, findFlags, CombineMode.FIND_NEW);
    }
    

    /**
     * The query for extracting records containing a specific record name.
     * 
     * @param catalogName The name of the catalog.
     * @param name The record name for the Cumulus record to find.
     * @return The query for finding the Cumulus record with the given record name.
     */
    public static CumulusQuery getQueryForSpecificRecordName(String catalogName, String name) {
        ArgumentCheck.checkNotNullOrEmpty(catalogName, "String catalogName");
        ArgumentCheck.checkNotNullOrEmpty(name, "String name");
        String query = String.format(
                StringUtils.replaceSpacesToTabs("%s is %s\nand %s is %s"),
                Constants.FieldNames.RECORD_NAME,
                name,
                Constants.FieldNames.CATALOG_NAME,
                catalogName);
        EnumSet<FindFlag> findFlags = EnumSet.of(
                FindFlag.FIND_MISSING_FIELDS_ARE_ERROR, 
                FindFlag.FIND_MISSING_STRING_LIST_VALUES_ARE_ERROR);    

        return new CumulusQuery(query, findFlags, CombineMode.FIND_NEW);
    }
    
    /**
     * The query for extracting records containing a specific UUID.
     * If the full uuid is given, then Cumulus should only give a single Cumulus record.
     * 
     * The record must have the 'GUID' field contain the uuid (the Cumulus GUID has a silly prefix, which we ignore),
     * It must have the registration state 'registration finished', and it must belong to the given catalog.
     * 
     * @param catalogName The name of the catalog.
     * @param uuid The UUID for the Cumulus record to find.
     * @return The query for finding the Cumulus record with the given UUID.
     */
    public static CumulusQuery getQueryForSpecificGUID(String catalogName, String uuid) {
        ArgumentCheck.checkNotNullOrEmpty(catalogName, "String catalogName");
        ArgumentCheck.checkNotNullOrEmpty(uuid, "String uuid");
        String query = String.format(
                StringUtils.replaceSpacesToTabs("%s contains %s\nand %s is %s\nand %s is %s"),
                Constants.FieldNames.GUID,
                uuid,
                Constants.FieldNames.REGISTRATIONSTATE,
                Constants.FieldValues.REGISTRATIONSTATE_FINISHED,
                Constants.FieldNames.CATALOG_NAME,
                catalogName);
        EnumSet<FindFlag> findFlags = EnumSet.of(
                FindFlag.FIND_MISSING_FIELDS_ARE_ERROR, 
                FindFlag.FIND_MISSING_STRING_LIST_VALUES_ARE_ERROR);    

        return new CumulusQuery(query, findFlags, CombineMode.FIND_NEW);
    }
}
