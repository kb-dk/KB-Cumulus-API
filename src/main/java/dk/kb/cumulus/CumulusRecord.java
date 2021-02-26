package dk.kb.cumulus;

import java.io.File;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import com.canto.cumulus.CumulusException;
import com.canto.cumulus.exceptions.FieldNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.canto.cumulus.ItemCollection;
import com.canto.cumulus.Asset;
import com.canto.cumulus.GUID;
import com.canto.cumulus.RecordItem;
import com.canto.cumulus.fieldvalue.AssetReference;
import com.canto.cumulus.fieldvalue.AssetXRefFieldValue;
import com.canto.cumulus.fieldvalue.StringEnumFieldValue;

import dk.kb.cumulus.field.AssetsField;
import dk.kb.cumulus.field.Field;
import dk.kb.cumulus.field.StringField;
import dk.kb.cumulus.field.TableField;
import dk.kb.cumulus.field.TableField.Row;
import dk.kb.cumulus.utils.ArgumentCheck;
import dk.kb.cumulus.utils.GuidExtractionUtils;
import dk.kb.cumulus.utils.StringUtils;

/**
 * Record from Cumulus.
 * The Cumulus records are extracted from the Cumulus Server using a Cumulus Query.
 * 
 * The Cumulus server extracts a RecordItemCollection, which both is a collection of RecordItems and
 * contains the field layout (used for creating the FieldExtractor).
 * Each RecordItem is used with the FieldExtractor for the RecordItemCollection to create one 
 * CumulusRecord (this class).
 * 
 * Basically this class contains helper methods and extractor for the Item delivered by Cumulus.
 */
public class CumulusRecord {
    /** The logger.*/
    private static final Logger log = LoggerFactory.getLogger(CumulusRecord.class);

    /** Constant for not allowing assert to be extracted from proxy.*/
    protected static final boolean ASSET_NOT_ALLOW_PROXY = false;

    /** The field extractor.*/
    protected final FieldExtractor fe;
    /** The Cumulus record item.*/
    protected final RecordItem item;

    /** The GUID for the file and the Cumulus record. It is created and stored the first time it is needed.*/
    protected String guid = null;
    
    /** A map between the value and the name of the fields already extracted.*/
    protected Map<String, String> fieldValues = new HashMap<String, String>();
    
    /**
     * Constructor.
     * @param fe The field extractor.
     * @param item The Cumulus record item.
     */
    public CumulusRecord(FieldExtractor fe, RecordItem item) {
        this.fe = fe;
        this.item = item;
    }
    
    /**
     * @return The identifier for this record.
     */
    public String getUUID() {
        if(guid == null) {
            try {
                guid = GuidExtractionUtils.extractGuid(getFieldValue(Constants.FieldNames.GUID));
            } catch (RuntimeException e) {
                // Needed to avoid further exceptions during error handling.
                guid = "GUID COULD NOT BE EXTRACTED!";
                throw e;
            }
        }
        return guid;
    }

    /**
     * Extracts the value of the field with the given name.
     * If multiple fields have the given field name, then only the value of one of the fields are returned.
     * The result is in String format.
     * It will throw an exception, if the field does not exist or does not contain a value.
     * @param fieldname The name for the field. 
     * @return The string value of the field. 
     */
    public String getFieldValue(String fieldname) {
        if(!fieldValues.containsKey(fieldname)) {
            GUID fieldGuid = fe.getFieldGUID(fieldname);
            try {
                fieldValues.put(fieldname, item.getStringValue(fieldGuid));
            } catch (CumulusException e) {
                log.error("Cumulus failed to extract the following field value:" +
                                " \n Fieldname: {}\n fieldGuid: {}\n item: {}\n fieldValues: {}\n",
                        fieldname, fieldGuid, item, fieldValues);
                e.printStackTrace();
            }
        }
        return fieldValues.get(fieldname);
    }
    
    /**
     * Extracts the string value of a given field.
     * It will return a null, if the field does not have a value.
     * @param fieldname The name of the field to extract.
     * @return The value of the field, or null if field is empty.
     */
    public String getFieldValueOrNull(String fieldname) {
        if(!fieldValues.containsKey(fieldname)) {
            GUID fieldGuid = fe.getFieldGUID(fieldname);
            if(item.hasValue(fieldGuid)) {
                fieldValues.put(fieldname, item.getStringValue(fieldGuid));    
            } else {
                return null;
            }
        }
        return fieldValues.get(fieldname);
    }
    
    /**
     * Set the string value of a given Cumulus field.
     * @param fieldName The name of the field.
     * @param value The new value of the field.
     */
    public void setStringValueInField(String fieldName, String value) {
        GUID fieldGuid = fe.getFieldGUID(fieldName);
        setStringValueInField(fieldGuid, value);
        
        fieldValues.put(fieldName, value);
    }
    
    /**
     * Extracts the long value of the field with the given name.
     * If multiple fields have the given field name, then only the value of one of the fields are returned.
     * The result has type Long.
     * It will throw an exception, if the field does not have an value.
     * @param fieldname The name for the field. 
     * @return The long value of the field. 
     */
    public Long getFieldLongValue(String fieldname) {
        GUID fieldGuid = fe.getFieldGUID(fieldname);
        return item.getLongValue(fieldGuid);
    }
    
    /**
     * Extracts the long value of the field with the given name.
     * If multiple fields have the given field name, then only the value of one of the fields are returned.
     * The result has type Long.
     * It will throw an exception, if the field does not have an value.
     * @param fieldname The name for the field. 
     * @return The long value of the field. 
     */
    public Integer getFieldIntValue(String fieldname) {
        GUID fieldGuid = fe.getFieldGUID(fieldname);
        return item.getIntValue(fieldGuid);
    }

    /**
     * Extracts the asset reference of a given field.
     * It will return a null, if the field does not have a value.
     * @param fieldname The name of the field to extract.
     * @return The asset reference value of the field.
     */
    public AssetReference getAssetReference(String fieldname) {
        GUID fieldGuid = fe.getFieldGUID(fieldname);
        return item.getAssetReferenceValue(fieldGuid);
    }

    /**
     * Extracts the guid for a given field.
     * It will return null if not found
     * @param fieldname The name of the field to extract guid from.
     * @return The guid of the first found fieldname or
     * null if not found
     */
    public GUID getGUID(String fieldname){
        return fe.getFieldGUID(fieldname);
    }

    /**
     * Extracts the table value of the given guid
     * @param guid The guid to get contents from
     * @return Table value (contents of the item collection)
     * for the specified guid
     */
    public ItemCollection getTableValue(GUID guid){
        return item.getTableValue(guid);
    }

    /**
     * Retrieves the IDs of the categories for this record.
     * @return The collection of IDs for the categories for this record.
     */
    public Collection<Integer> getCategories() {
        return item.getCategoriesValue().getIDs();
    }
    
    /**
     * Retrieves the string value of a field (also non-string fields, except tables, pictures and audio).
     * @param fieldname The name of the field.
     * @return The string value of the field. Or null, if the field is missing, empty, or unhandled data-type.
     */
    public String getFieldValueForNonStringField(String fieldname) {
        return fe.getStringValueForField(fieldname, item);
    }

    /**
     * Retrieves the content file.
     * @return The content file.
     */
    public File getFile() {
        try {
            AssetReference reference = item.getAssetReferenceValue(GUID.UID_REC_ASSET_REFERENCE);

            Asset asset = reference.getAsset(ASSET_NOT_ALLOW_PROXY);
            return asset.getAsFile();
        } catch (Exception e) {
            throw new IllegalStateException("Cannot retrieve the file.", e);
        }        
    }
    
    /**
     * Set the given file as new asset reference for this cumulus record.
     * NOTE: Be careful with this method, since it overrides the reference to the current asset.
     * @param f The new file for the asset reference.
     */
    public void setNewAssetReference(File f) {
        try {
            AssetReference newAssetRef = new AssetReference(item.getCumulusSession(), f.getAbsolutePath(), null);
            item.setAssetReferenceValue(GUID.UID_REC_ASSET_REFERENCE, newAssetRef);
            item.save();
        } catch (Exception e) {
            throw new IllegalStateException("Cannot update the asset reference with file '" + f.getAbsolutePath() 
                    + "'.", e);
        }
    }
    
    /**
     * Updates the asset reference.
     */
    public void updateAssetReference() {
        item.updateAssetReference();
        item.save();
    }
    
    /**
     * Extracts all the metadata fields for this record and converts them into an XML file. 
     * @param out The output stream where the XML for this record is placed.
     * @throws ParserConfigurationException If the XML parse has an issue with the configuration.
     * @throws TransformerException If the transformer has an issue.
     */
    public void writeFieldMetadata(OutputStream out) throws ParserConfigurationException, TransformerException {
        ArgumentCheck.checkNotNull(out, "OutputStream out");
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        Document doc = docBuilder.newDocument();
        Element rootElement = doc.createElement("record");
        doc.appendChild(rootElement);
        
        Map<String, Field> fields = fe.getFields(item);

        for(Field f : fields.values()) {
            if(!f.isEmpty()) {
                addCumulusFieldToMetadataOutput(f, doc, rootElement);
            }
        }
        
        // write the content into xml file
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(out);

        transformer.transform(source, result);
    }
    
    /**
     * Adds the given Cumulus field to the given metadata output document.
     * @param f The Cumulus Field.
     * @param doc The metadata output document.
     * @param rootElement The root element for the field.
     */
    protected void addCumulusFieldToMetadataOutput(Field f, Document doc, Element rootElement) {
        Element field = doc.createElement("field");
        rootElement.appendChild(field);
        field.setAttribute("data-type", f.getType());
        field.setAttribute("name", f.getName());
        
        if(f instanceof StringField) {
            StringField sf = (StringField) f;
            for(String v : getValues(sf.getStringValue())) {
                Element value = doc.createElement("value");
                field.appendChild(value);
                value.appendChild(doc.createTextNode(v));
            }
        } else if(f instanceof TableField) {
            Element table = doc.createElement("table");
            field.appendChild(table);
            
            TableField tf = (TableField) f;
            for(Row r : tf.getRows()) {
                Element row = doc.createElement("row");
                table.appendChild(row);
                
                for(Map.Entry<String, String> element : r.getElements().entrySet()) {
                    Element coloumn = doc.createElement("field");
                    row.appendChild(coloumn);
                    coloumn.setAttribute("name", element.getKey());
                    
                    for(String v : getValues(element.getValue())) {
                        Element value = doc.createElement("value");
                        coloumn.appendChild(value);
                        value.appendChild(doc.createTextNode(v));
                    }
                }
            }
        } else if(f instanceof AssetsField) {
            AssetsField af = (AssetsField) f;
            for(String n : af.getNames()) {
                Element value = doc.createElement("value");
                field.appendChild(value);
                
                Element name = doc.createElement("name");
                name.appendChild(doc.createTextNode(n));
                value.appendChild(name);
                
                Element uuid = doc.createElement("uuid");
                uuid.appendChild(doc.createTextNode(af.getGuid(n)));
                value.appendChild(uuid);
                
                Element index = doc.createElement("order");
                index.appendChild(doc.createTextNode(af.getIndex(n).toString()));
                value.appendChild(index);
            }
        } else {
            log.warn("Could not handle field: " + f);
        }
    }
    
    /**
     * The given value is formatted and split into lines.
     * @param value The multi-lined value, which should be split into individual values.
     * @return Get values as an array.
     */
    protected String[] getValues(String value) {
        String encodedValue = StringUtils.xmlEncode(value);
        return encodedValue.split("\n");
    }

    /**
     * Validates that the record has the given required fields, and that the field has a value.
     * @param requiredFields The required fields validate against.
     * @throws IllegalStateException If any required field does not exist, or does not contain a value.
     */
    public void validateFieldsHasValue(Collection<String> requiredFields) {
        List<String> fieldErrors = new ArrayList<String>();

        Map<String, Field> fields = fe.getAllFields(item);
        for(String field : requiredFields) {
            if(!fields.containsKey(field)) {
                fieldErrors.add("The field '" + field + "' does not exist.");                
            } else if(fields.get(field).isEmpty()) {
                fieldErrors.add("The field '" + field + "' does not contain any data.");
            }
        }
        
        if(!fieldErrors.isEmpty()) {
            String errMsg = "The following field(s) did not live up to the requirements: \n" 
                    + StringUtils.listToString(fieldErrors, "\n");
            log.warn(errMsg);
            throw new IllegalStateException("Required fields failure, " + fieldErrors.size() 
                    + " field(s) did not live up to their requirements.\n" + errMsg);
        }
    }
    
    /**
     * Validates that the record has the given required fields.
     * @param requiredFields The required fields validate against.
     * @throws IllegalStateException If any required field does not exist.
     */
    public void validateFieldsExists(Collection<String> requiredFields) {
        List<String> fieldErrors = new ArrayList<String>();

        Map<String, Field> fields = fe.getAllFields(item);
        for(String field : requiredFields) {
            if(!fields.containsKey(field)) {
                fieldErrors.add("The field '" + field + "' does not exist.");                                
            }
        }

        if(!fieldErrors.isEmpty()) {
            String errMsg = "The following field(s) did not live up to the requirements: \n" 
                    + StringUtils.listToString(fieldErrors, "\n");
            log.warn(errMsg);
            throw new IllegalStateException("Required fields failure, " + fieldErrors.size() 
                    + " field(s) did not live up to their requirements.\n" + errMsg);
        }
    }

    /**
     * Sets a given String Enum value for a field.
     * @param fieldName The name of the field.
     * @param value The new enum value for the field.
     */
    public void setStringEnumValueForField(String fieldName, String value) {
        try {
            GUID fieldGuid = fe.getFieldGUID(fieldName);
            StringEnumFieldValue enumValue = item.getStringEnumValue(fieldGuid);
            enumValue.setFromDisplayString(value);
            item.setStringEnumValue(fieldGuid, enumValue);

            item.save();
        } catch(Exception e) {
            String errMsg = "Could not set the value '" + value + "' for field '" + fieldName + "'.";
            log.error(errMsg, e);
            throw new IllegalStateException(errMsg, e);
        }
    }
    
    /**
     * Sets the value of the field with the given GUID.
     * @param fieldGuid The GUID of the field.
     * @param value The new value for the field.
     */
    protected void setStringValueInField(GUID fieldGuid, String value) {
        try {
            item.setStringValue(fieldGuid, value);
            item.save();
        } catch (Exception e) {
            String errMsg = "Could not set the value '" + value + "' for the field '" + fieldGuid + "'";
            log.error(errMsg, e);
            throw new IllegalStateException(errMsg, e);
        }
    }
    
    /**
     * Sets the date value for the given date field.
     * @param fieldName The name of the field.
     * @param dateValue The new date value for the field.
     */
    public void setDateValueInField(String fieldName, Date dateValue) {
        GUID fieldGuid = fe.getFieldGUID(fieldName);
        try {
            item.setDateValue(fieldGuid, dateValue);
            item.save();
        } catch (Exception e) {
            String errMsg = "Could not set the date value '" + dateValue + "' for the field '" + fieldName + "'";
            log.error(errMsg, e);
            throw new IllegalStateException(errMsg, e);
        }        
    }
    
    /**
     * Sets the date value for the given boolean field.
     * @param fieldName The name of the field.
     * @param value The new boolean value for the field.
     */
    public void setBooleanValueInField(String fieldName, Boolean value) {
        GUID fieldGuid = fe.getFieldGUID(fieldName);
        try {
            item.setBooleanValue(fieldGuid, value);
            item.save();
        } catch (Exception e) {
            String errMsg = "Could not set the boolean value '" + value + "' for the field '" + fieldName + "'";
            log.error(errMsg, e);
            throw new IllegalStateException(errMsg, e);
        }        
    }

    /**
     * Checks whether the record has any sub-assets, and thus whether it is a master-asset.
     * @return Whether or not this record is a master-asset.
     */
    public boolean isMasterAsset() {
        GUID fieldGuid = fe.getFieldGUID(Constants.FieldNames.RELATED_SUB_ASSETS);
        return item.hasValue(fieldGuid);
    }
    
    /**
     * Checks whether the record has any master-assets attached, and thus whether it is a sub-asset.
     * @return Whether or not this record is a sub-asset.
     */
    public boolean isSubAsset() {
        GUID fieldGuid = fe.getFieldGUID(Constants.FieldNames.RELATED_MASTER_ASSETS);
        return item.hasValue(fieldGuid);
    }
    
    /**
     * Adds the given Cumulus record as Master Asset to this Cumulus record.
     * @param record The record to add as Master Asset.
     */
    public void addMasterAsset(CumulusRecord record) {
        createRelationToRecord(record, Constants.FieldNames.RELATED_MASTER_ASSETS,
                GUID.UID_ASSET_RELATION_IS_ALTERNATE);
    }
    
    /**
     * Adds the given Cumulus record as Sub Asset to this Cumulus record.
     * @param record The record to add as Sub Asset.
     */
    public void addSubAsset(CumulusRecord record) {
        createRelationToRecord(record, Constants.FieldNames.RELATED_SUB_ASSETS, GUID.UID_ASSET_RELATION_IS_ALTERNATE);
    }

    /**
     * Create a relation to another record.
     * @param record The record to create a relationship to.
     * @param fieldName The name of the field for the relation.
     * @param relation The type of relation.
     */
    public void createRelationToRecord(CumulusRecord record, String fieldName, GUID relation) {
        GUID fieldGuid = fe.getFieldGUID(fieldName);
        AssetXRefFieldValue assetXRef = item.getAssetXRefValue(fieldGuid);
        assetXRef.addReference(relation, record.item.getID(), record.item.getDisplayString());
        item.setAssetXRefValue(fieldGuid, assetXRef);
        item.save();
    }

    @Override
    public String toString() {
        return "[CumulusRecord : " + getClass().getCanonicalName() + " -> " + getUUID() + "]";
    }
}
