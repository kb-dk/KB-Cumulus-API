package dk.kb.cumulus.field;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.canto.cumulus.FieldDefinition;

/**
 * Container for Cumulus asset field.
 * This is either a field for sub-assets or a master-asset.
 */
public class AssetsField extends Field {
    /** The list of names for the assets.*/
    protected final List<String> assetNames = new ArrayList<String>();
    /** The map between the names and the guids for the assets.*/
    protected final Map<String, String> assetGuids = new HashMap<String, String>();
    
    /**
     * Constructor, for a string value.
     * @param fieldDefinition The definition of the field.
     * @param fieldType The type of field.
     */
    public AssetsField(FieldDefinition fieldDefinition, String fieldType) {
        super(fieldDefinition, fieldType);
    }
    
    /**
     * Adds an asset to this field.
     * @param name The name of the asset.
     * @param uuid The uuid of the asset.
     */
    public void addAsset(String name, String uuid) {
        assetNames.add(name);
        assetGuids.put(name, uuid);
    }
    
    /**
     * @return The names the all the assets.
     */
    public Collection<String> getNames() {
        return assetNames;
    }
    
    /**
     * @param name The name of the asset, whose guid should be extracted.
     * @return The guid for the asset.
     */
    public String getGuid(String name) {
        return assetGuids.get(name);
    }
    
    /**
     * @param name The name of the asset, whose index should be found.
     * @return The index of the asset.
     */
    public Integer getIndex(String name) {
        return assetNames.indexOf(name) + 2;
    }
    
    /**
     * @return Whether or not it contains any actual value.
     */
    @Override
    public boolean isEmpty() {
        if(!assetNames.isEmpty()) {
            return false;
        }
        return true;
    }
}
