package dk.kb.cumulus;

import java.io.Closeable;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.canto.cumulus.Catalog;
import com.canto.cumulus.Cumulus;
import com.canto.cumulus.RecordItemCollection;
import com.canto.cumulus.Server;

import dk.kb.cumulus.config.CumulusConfiguration;
import dk.kb.cumulus.utils.ArgumentCheck;

/**
 * Wrapper for accessing the Cumulus server.
 */
public class CumulusServer implements Closeable {
    /** The logger.*/
    private static final Logger log = LoggerFactory.getLogger(CumulusServer.class);

    /** The configuraiton for the Cumulus server. */
    protected final CumulusConfiguration configuration;
    /** Map between the catalog name and the catalog object.*/
    protected final Map<String, Catalog> catalogs = new HashMap<String, Catalog>();

    /** The cumulus server access point.*/
    protected Server server;

    /** 
     * Constructor.
     * @param configuration The configuration for Cumulus.
     */
    public CumulusServer(CumulusConfiguration configuration) {
        ArgumentCheck.checkNotNull(configuration, "CumulusConfiguration configuration");
        Cumulus.CumulusStart();

        this.configuration = configuration;
        try {
            this.server = Server.openConnection(configuration.getWriteAccess(), configuration.getServerUrl(), 
                    configuration.getUserName(), configuration.getUserPassword());
        } catch (Exception e) {
            throw new IllegalStateException("Could not connect to server '" + configuration.getServerUrl() + "'", e);
        }
    }

    /** 
     * Constructor.
     * @param serverUrl The URL for the Cumulus Server.
     * @param userName The name of the user for the Cumulus server.
     * @param userPassword The password for the user for the Cumulus server.
     * @param catalogs The catalogs which may be accessed on the Cumulus server.
     * @param writeAccess Whether or not the connection to the Cumulus server allows write access.
     */
    public CumulusServer(String serverUrl, String userName, String userPassword, Collection<String> catalogs, 
            boolean writeAccess) {
        ArgumentCheck.checkNotNullOrEmpty(serverUrl, "String serverUrl");
        ArgumentCheck.checkNotNull(userName, "String userName");
        ArgumentCheck.checkNotNull(userPassword, "String userPassword,");
        ArgumentCheck.checkNotNullOrEmpty(catalogs, "Collection<String> catalogs,");
        this.configuration = new CumulusConfiguration(writeAccess, serverUrl, userName, userPassword, catalogs);
        Cumulus.CumulusStart();

        try {
            this.server = Server.openConnection(configuration.getWriteAccess(), configuration.getServerUrl(), 
                    configuration.getUserName(), configuration.getUserPassword());
        } catch (Exception e) {
            throw new IllegalStateException("Could not connect to server '" + configuration.getServerUrl() + "'", e);
        }
    }
    
    /**
     * @return The catalogs for the server.
     */
    public List<String> getCatalogNames() {
        return configuration.getCatalogs();
    }

    /**
     * @return The Cumulus server.
     */
    public Server getServer() {
        if(!server.isAlive()) {
            try {
                server = Server.openConnection(configuration.getWriteAccess(), configuration.getServerUrl(), 
                        configuration.getUserName(), configuration.getUserPassword());
            } catch (Exception e) {
                throw new IllegalStateException("Connection to Cumulus server '" + configuration.getServerUrl() 
                        + "' is no longer alive, and we cannot create a new one.", e);
            }
        }
        return server;
    }

    /**
     * Retrieve the catalog for a given catalog name.
     * @param catalogName The name of the catalog.
     * @return The catalog.
     */
    protected Catalog getCatalog(String catalogName) {
        if(!catalogs.containsKey(catalogName)) {
            int catalogId = getServer().findCatalogID(catalogName);
            catalogs.put(catalogName, getServer().openCatalog(catalogId));            
        }
        return catalogs.get(catalogName);
    }

    /**
     * Extracts the collection of record items from a given catalog limiting by the given query.
     * @param catalogName The name of the catalog.
     * @param query The query for finding the desired items.
     * @return The collection of record items.
     */
    public CumulusRecordCollection getItems(String catalogName, CumulusQuery query) {
        ArgumentCheck.checkNotNullOrEmpty(catalogName, "String catalogName");
        ArgumentCheck.checkNotNull(query, "CumulusQuery query");
        Catalog catalog = getCatalog(catalogName);
        RecordItemCollection recordCollection = catalog.newRecordItemCollection(true);
        recordCollection.find(query.getQuery(), query.getFindFlags(), query.getCombineMode(),
                query.getLocale());
        return new CumulusRecordCollection(recordCollection, this, catalogName);
    }
    
    /**
     * Find the Cumulus record containing a given UUID and belonging to a given catalog.
     * Will only return the first found result. And it will return a null if no results were found. 
     * @param catalogName The name of the catalog, where the Cumulus record is.
     * @param uuid The UUID of the Cumulus record to find.
     * @return The Cumulus record, or null if no record was found.
     */
    public CumulusRecord findCumulusRecord(String catalogName, String uuid) {
        ArgumentCheck.checkNotNullOrEmpty(catalogName, "String catalogName");
        ArgumentCheck.checkNotNullOrEmpty(uuid, "String uuid");
        CumulusQuery query = CumulusQuery.getQueryForSpecificGUID(catalogName, uuid);
        
        return getSpecificRecord(query, catalogName);
    }
    
    /**
     * Find the Cumulus record containing a given record name and belonging to a given catalog.
     * Will only return the first found result. And it will return a null if no results were found. 
     * @param catalogName The name of the catalog, where the Cumulus record is.
     * @param name The record name of the Cumulus record to find.
     * @return The Cumulus record, or null if no record was found.
     */
    public CumulusRecord findCumulusRecordByName(String catalogName, String name) {
        ArgumentCheck.checkNotNullOrEmpty(catalogName, "String catalogName");
        ArgumentCheck.checkNotNullOrEmpty(name, "String uuid");
        CumulusQuery query = CumulusQuery.getQueryForSpecificRecordName(catalogName, name);
        
        return getSpecificRecord(query, catalogName);
    }
    
    /**
     * Extracts one CumulusRecord with the given query.
     * If none are found, then null is returned. 
     * If multiple are found, then only the first is returned.
     * @param query The query for the finding the CumulusRecord.
     * @param catalogName The name of the catalog.
     * @return The CumulusRecord, or null if none found.
     */
    protected CumulusRecord getSpecificRecord(CumulusQuery query, String catalogName) {
        CumulusRecordCollection items = getItems(catalogName, query);
        if(items == null || !items.iterator().hasNext()) {
            log.warn("Could not find any records with query: '" + query + "'. Returning a null");            
            return null;
        }

        Iterator<CumulusRecord> iterator = items.iterator();
        CumulusRecord res = iterator.next();
        if(iterator.hasNext()) {
            log.warn("More than one record found for query: '" + query + "'. Only using the first found.");
        }
        return res;
    }

    @Override
    public void close() throws IOException {
        Cumulus.CumulusStop();
    }    
}
