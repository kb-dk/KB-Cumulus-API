package dk.kb.cumulus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.canto.cumulus.FieldDefinition;
import com.canto.cumulus.FieldTypes;
import com.canto.cumulus.GUID;
import com.canto.cumulus.Item;
import com.canto.cumulus.Layout;
import com.canto.cumulus.fieldvalue.AssetXRefFieldValue;

import dk.kb.cumulus.field.AssetsField;
import dk.kb.cumulus.field.EmptyField;
import dk.kb.cumulus.field.Field;
import dk.kb.cumulus.field.StringField;
import dk.kb.cumulus.field.TableField;

/**
 * Class for extracting the values of all the fields of an item according to the layout.
 */
public class FieldExtractor {
    /** The logger.*/
    private static final Logger log = LoggerFactory.getLogger(FieldExtractor.class);

    /** The layout for this extractor.*/
    protected final Layout layout;

    /** The cumulus server.*/
    protected final CumulusServer server;

    /** The catalog for this extraction.*/
    protected final String catalog;

    /** The mapping between the field names and their GUIDs.
     * It is used for optimization, so we don't have to locate a field GUID every time we use the field.*/
    protected final Map<String, GUID> fieldGuids;

    /**
     * Constructor.
     * @param layout The field-layout for the extractor.
     * @param server The cumulus server.
     * @param catalog The catalog for this extraction.
     */
    public FieldExtractor(Layout layout, CumulusServer server, String catalog) {
        this.layout = layout;
        this.server = server;
        this.catalog = catalog;
        this.fieldGuids = new HashMap<String, GUID>();
    }

    /**
     * Extracts the fields of the item according to the layout, and returns them as a mapping between
     * the name of the field and the field.
     * It might not be all the fields, which are extracted. Only the ones containing a value.
     * @param item The item to extract the fields for.
     * @return The map of fields for the item. Fields with no value are ignored.
     */
    public Map<String, Field> getFields(Item item) {
        return getFields(item, true);
    }

    /**
     * Extracts all the fields of the item according to the layout, and returns them as a mapping between
     * the name of the field and the field.
     * This extracts all the fields, also the ones which are empty (thus returned as an EmptyField).
     * @param item The item to extracts all the fields for.
     * @return The map of all the fields for the item, including the empty-valued fields.
     */
    public Map<String, Field> getAllFields(Item item) {
        return getFields(item, false);
    }

    /**
     * @return The cumulus server.
     */
    public CumulusServer getServer() {
        return server;
    }

    /**
     * @return The catalog.
     */
    public String getCatalog() {
        return catalog;
    }

    /**
     * Extracts the fields for the item.
     * @param item The item to extract the fields from.
     * @param ignoreEmptyFields Whether or not to ignore empty fields.
     * @return Map between field-name and the field.
     */
    protected Map<String, Field> getFields(Item item, boolean ignoreEmptyFields) {
        Map<String, Field> res = new HashMap<String, Field>();
        for(FieldDefinition fd : layout) {
            Field f = getFieldValue(fd, item, ignoreEmptyFields);
            if(f != null) {
                res.put(f.getName(), f);
            }
        }
        return res;
    }

    /**
     * Extracts all the fields of the item according to the layout, and returns them as a mapping between
     * the name of the field and the value (in string format).
     * @param item The item to extract all fields for.
     * @return The collection of fields for the item. Fields with no value are ignored.
     */
    public Map<String, String> getMap(Item item) {
        Map<String, String> res = new HashMap<String, String>();
        for(FieldDefinition fd : layout) {
            StringField f = (StringField) getFieldValue(fd, item, true);
            if(f != null) {
                res.put(f.getName(), f.getStringValue());
            }
        }
        return res;
    }

    /**
     * Extracts the value of a specific field from the given item.
     * @param fd The definition of the field.
     * @param item The item to have its field value extracted.
     * @param ignoreEmptyFields Whether or not to ignore empty fields.
     * @return The string value of the field. If the field is not natively string, then it is
     * converted into a string.
     */
    protected Field getFieldValue(FieldDefinition fd, Item item, boolean ignoreEmptyFields) {
        if(!item.hasValue(fd.getFieldUID())) {
            log.trace("No element at uid " + fd.getFieldUID());
            if(ignoreEmptyFields) {
                return null;
            } else {
                return new EmptyField(fd, getFieldTypeName(fd.getFieldType()));
            }
        }

        switch(fd.getFieldType()) {
        case FieldTypes.FieldTypeBool:
            return new StringField(fd, getFieldTypeName(fd.getFieldType()), 
                    String.valueOf(item.getBooleanValue(fd.getFieldUID())));
        case FieldTypes.FieldTypeDate:
            // TOOD: figure out about how to format the date.
            return new StringField(fd, getFieldTypeName(fd.getFieldType()), 
                    item.getDateValue(fd.getFieldUID()).toString());
        case FieldTypes.FieldTypeDouble:
            return new StringField(fd, getFieldTypeName(fd.getFieldType()), 
                    String.valueOf(item.getDoubleValue(fd.getFieldUID())));
        case FieldTypes.FieldTypeEnum:
            return new StringField(fd, getFieldTypeName(fd.getFieldType()), 
                    item.getStringEnumValue(fd.getFieldUID()).getDisplayString());
        case FieldTypes.FieldTypeInteger:
            return new StringField(fd, getFieldTypeName(fd.getFieldType()), 
                    String.valueOf(item.getIntValue(fd.getFieldUID())));
        case FieldTypes.FieldTypeLong:
            return new StringField(fd, getFieldTypeName(fd.getFieldType()), 
                    String.valueOf(item.getLongValue(fd.getFieldUID())));
        case FieldTypes.FieldTypeString:
            return new StringField(fd, getFieldTypeName(fd.getFieldType()), 
                    item.getStringValue(fd.getFieldUID()));
        case FieldTypes.FieldTypeBinary:
            log.trace("Issue handling the field '" + fd.getName() + "' of type " + getFieldTypeName(fd.getFieldType()) 
            + ", tries to extracts it as the path of the Asset Reference");
            return extractBinaryField(fd, item);
        case FieldTypes.FieldTypeTable:
            return new TableField(fd, getFieldTypeName(fd.getFieldType()), item.getTableValue(fd.getFieldUID()), this);
        default:
            log.trace("Currently does not handle field value for type " + getFieldTypeName(fd.getFieldType())
            + ", returning an empty field for " + fd.getName());
            return new EmptyField(fd, getFieldTypeName(fd.getFieldType()));
        }
    }

    /**
     * Retrieves the name of a given field type.
     * @param fieldType The field type ordinal.
     * @return the name of the field type.
     */
    protected String getFieldTypeName(int fieldType) {
        switch(fieldType) {
        case FieldTypes.FieldTypeBool:
            return "boolean";
        case FieldTypes.FieldTypeDate:
            return "date";
        case FieldTypes.FieldTypeDouble:
            return "double";
        case FieldTypes.FieldTypeEnum:
            return "enumerator";
        case FieldTypes.FieldTypeInteger:
            return "integer";
        case FieldTypes.FieldTypeLong:
            return "long";
        case FieldTypes.FieldTypeString:
            return "string";
        case FieldTypes.FieldTypeBinary:
            return "binary";
        case FieldTypes.FieldTypeAudio:
            return "audio";
        case FieldTypes.FieldTypePicture:
            return "picture";
        case FieldTypes.FieldTypeTable:
            return "table";
        }

        // Should we throw an error/exception here?
        log.warn("Cannot understand the field type '" + fieldType + "'. It does not seem to be defined!");
        return "NOT DEFINED!!!";
    }

    /**
     * Extracts the GUID for the field with the given name.
     * NOTE: If there is multiple fields with the name (ignore case), only the first found is returned.
     * 
     * @param fieldName The name of the field, whose GUID should be extracted.
     * @return The GUID, or null if not found.
     */
    public GUID getFieldGUID(String fieldName) {
        if(fieldGuids.containsKey(fieldName)) {
            return fieldGuids.get(fieldName);
        }
        for(FieldDefinition fd : layout) {
            if(fd.getName().equalsIgnoreCase(fieldName)) {
                GUID guid = fd.getFieldUID();
                fieldGuids.put(fieldName, guid);
                return guid;
            }
        }

        throw new IllegalStateException("Could not find field: " + fieldName); 
    }

    /**
     * Retrieves the string value of a field.
     * Though it does not handle fields of type Table.
     * @param fieldName The name of the field.
     * @param item The item to extract it from.
     * @return The string value.
     */
    public String getStringValueForField(String fieldName, Item item) {
        for(FieldDefinition fd : layout) {
            if(fd.getName().equalsIgnoreCase(fieldName)) {
                Field f = getFieldValue(fd, item, false);
                if(f instanceof StringField) {
                    return ((StringField) f).getStringValue();
                } else {
                    return null;
                }
            }
        }
        return null;
    }

    /**
     * Extracts the field for a binary field.
     * @param fd The definition of the field.
     * @param item The binary field item to extract. 
     * @return The field.
     */
    protected Field extractBinaryField(FieldDefinition fd, Item item) {
        log.debug("Extracting the binary value for field: " + fd.getName());
        if(fd.getName().equals("Related Sub Assets") || fd.getName().equals("Related Master Assets")) {
            AssetXRefFieldValue subAssets = item.getAssetXRefValue(fd.getFieldUID());

            List<String> names = new ArrayList<String>();
            for(GUID g : subAssets.getRelations()) {
                names.addAll(subAssets.getReferencedItemNames(subAssets.getReferences(g)).values());
            }
            Collections.sort(names);

            AssetsField res = new AssetsField(fd, getFieldTypeName(fd.getFieldType()));
            for(String name : names) {
                CumulusRecord cr = server.findCumulusRecordByName(catalog, name);
                // initialize the RelatedObjectIdentifierValue for the intellectual entity
                // This is needed for the preservation. Perhaps move?
                if(cr.getFieldValueOrNull(
                        Constants.FieldNames.RELATED_OBJECT_IDENTIFIER_VALUE_INTELLECTUEL_ENTITY) == null) {
                    cr.setStringValueInField(Constants.FieldNames.REPRESENTATION_INTELLECTUAL_ENTITY_UUID, 
                            UUID.randomUUID().toString());
                }
                res.addAsset(name, cr.getFieldValue(
                        Constants.FieldNames.RELATED_OBJECT_IDENTIFIER_VALUE_INTELLECTUEL_ENTITY));
            }

            return res;
        } else {
            // Extract the AssetReference display string (usually the path)
            return new StringField(fd, getFieldTypeName(fd.getFieldType()), 
                    item.getAssetReferenceValue(fd.getFieldUID()).getPart(0).getDisplayString());
        }
    }
}
