package dk.kb.cumulus;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.canto.cumulus.Asset;
import com.canto.cumulus.GUID;
import com.canto.cumulus.Item;
import com.canto.cumulus.fieldvalue.AssetReference;
import com.canto.cumulus.fieldvalue.StringEnumFieldValue;

import dk.kb.cumulus.field.AssetsField;
import dk.kb.cumulus.field.Field;
import dk.kb.cumulus.field.StringField;
import dk.kb.cumulus.field.TableField;
import dk.kb.cumulus.field.TableField.Row;
import dk.kb.cumulus.utils.StringUtils;
import dk.kb.cumulus.utils.GuidExtractionUtils;

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
    protected final Item item;

    /** The GUID for the metadata record. It is created and stored the first time it is needed. */
    protected String metadataGuid;
    /** The GUID for the file and the Cumulus record. It is created and stored the first time it is needed.*/
    protected String guid = null;
    
    /** The ID of the collection where the record should be preserved.*/
    protected String preservationCollectionID;
    
    /**
     * Constructor.
     * @param fe The field extractor.
     * @param item The Cumulus record item.
     */
    public CumulusRecord(FieldExtractor fe, Item item) {
        this.fe = fe;
        this.item = item;
    }
    
    /**
     * Initializes the fields required for the preservation. 
     */
    public void initFieldsForPreservation() {
//        initChecksumField();
        initIntellectualEntityUUID();
    }
    
    /**
     * Initializes the fields required for the preservation of the representation.
     */
    public void initRepresentationFields() {
        initRepresentationIntellectualEntityUUID();
    }
    
    /**
     * Sets a new Metadata GUID for the record.
     */
    public void resetMetadataGuid() {
        metadataGuid = UUID.randomUUID().toString();
        setStringValueInField(Constants.FieldNames.METADATA_GUID, metadataGuid);
    }
    
    /**
     * Sets a new metadata GUID for the representation of this record.
     */
    public void resetRepresentationMetadataGuid() {
        String representationMetadataGuid = UUID.randomUUID().toString();
        setStringValueInField(Constants.FieldNames.REPRESENTATION_METADATA_GUID, 
                representationMetadataGuid);
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
     * It will throw an exception, if the field does not contain a value.
     * @param fieldname The name for the field. 
     * @return The string value of the field. 
     */
    public String getFieldValue(String fieldname) {
        GUID fieldGuid = fe.getFieldGUID(fieldname);
        return item.getStringValue(fieldGuid);
    }
    
    /**
     * Extracts the string value of a given field.
     * It will return a null, if the field does not have a value.
     * @param fieldname The name of the field to extract.
     * @return The value of the field, or null if field is empty.
     */
    public String getFieldValueOrNull(String fieldname) {
        GUID fieldGuid = fe.getFieldGUID(fieldname);
        if(item.hasValue(fieldGuid)) {
            return item.getStringValue(fieldGuid);
        } else {
            return null;
        }
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
     * Retrieves the string value of a field (also non-string fields, except tables, pictures and audio).
     * @param fieldname The name of the field.
     * @return The string value of the field. Or null, if the field is missing, empty, or unhandled data-type.
     */
    public String getFieldValueForNonStringField(String fieldname) {
        return fe.getStringValueForField(fieldname, item);
    }

    /**
     * Retrieves the metadata as an input stream.
     * @param cumulusFieldMetadataFile The file which the metadata is written to.
     * @return The input stream with the metadata.
     */
    public InputStream getMetadata(File cumulusFieldMetadataFile) {
        try {
            writeMetadataFile(cumulusFieldMetadataFile);
            return new FileInputStream(cumulusFieldMetadataFile);
        } catch (Exception e) {
            throw new IllegalStateException("Could not extract metadata file.", e);
        }
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
     * @param cumulusFieldFile The file where the XML for this record is placed.
     * @throws ParserConfigurationException If the XML parse has an issue with the configuration.
     * @throws TransformerException If the transformer has an issue.
     */
    protected void writeMetadataFile(File cumulusFieldFile) throws ParserConfigurationException, TransformerException {
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
        StreamResult result = new StreamResult(cumulusFieldFile);

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
     * Initializes the value in the related object identifier value for the intellectual entity.
     */
    protected void initIntellectualEntityUUID() {
        GUID relatedIntellectualEntityGuid = fe.getFieldGUID(
                Constants.FieldNames.RELATED_OBJECT_IDENTIFIER_VALUE_INTELLECTUEL_ENTITY);
        if(item.hasValue(relatedIntellectualEntityGuid)) {
            log.trace("Already has a value for the related intellectual object");
        } else {
            setStringValueInField(relatedIntellectualEntityGuid, UUID.randomUUID().toString());
        }
    }
    
    /**
     * Initializes the value for the intellectual entity of the representation.
     */
    protected void initRepresentationIntellectualEntityUUID() {
        GUID relatedIntellectualEntityGuid = fe.getFieldGUID(
                Constants.FieldNames.REPRESENTATION_INTELLECTUAL_ENTITY_UUID);
        if(item.hasValue(relatedIntellectualEntityGuid)) {
            log.trace("Already has a value for the related intellectual object of the representation");
        } else {
            setStringValueInField(relatedIntellectualEntityGuid, UUID.randomUUID().toString());
        }
    }
    
    /**
     * Sets the preservation status to failure.
     * @param status The error message for the failure state.
     */
    public void setPreservationFailed(String status) {
        try {
            GUID preservationStatusGuid = fe.getFieldGUID(Constants.FieldNames.PRESERVATION_STATUS);
            StringEnumFieldValue enumValue = item.getStringEnumValue(preservationStatusGuid);
            enumValue.setFromDisplayString(Constants.FieldValues.PRESERVATIONSTATE_ARCHIVAL_FAILED);
            item.setStringEnumValue(preservationStatusGuid, enumValue);
            
            GUID qaErrorGuid = fe.getFieldGUID(Constants.FieldNames.QA_ERROR);
            item.setStringValue(qaErrorGuid, status);
            item.save();
        } catch (Exception e) {
            String errMsg = "Could not set preservation failure state for status '" + status + "'";
            log.error(errMsg, e);
            throw new IllegalStateException(errMsg, e);
        }
    }

    /**
     * Sets the preservation status to successfully finished.
     * Also removes any existing error messages.
     */
    public void setPreservationFinished() {
        try {
            GUID preservationStatusGuid = fe.getFieldGUID(Constants.FieldNames.PRESERVATION_STATUS);
            StringEnumFieldValue enumValue = item.getStringEnumValue(preservationStatusGuid);
            enumValue.setFromDisplayString(Constants.FieldValues.PRESERVATIONSTATE_ARCHIVAL_COMPLETED);
            item.setStringEnumValue(preservationStatusGuid, enumValue);

            GUID qaErrorGuid = fe.getFieldGUID(Constants.FieldNames.QA_ERROR);
            item.setStringValue(qaErrorGuid, "");
            item.save();
        } catch(Exception e) {
            String errMsg = "Could not set preservation complete";
            log.error(errMsg, e);
            throw new IllegalStateException(errMsg, e);
        }
    }
    
    /**
     * Set the string value of a given Cumulus field.
     * @param fieldName The name of the field.
     * @param value The new value of the field.
     */
    public void setStringValueInField(String fieldName, String value) {
        GUID fieldGuid = fe.getFieldGUID(fieldName);
        setStringValueInField(fieldGuid, value);
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
     * Retrieves the value for the metadata guid.
     * The first time this method is called, then the guid is created and written back to Cumulus.
     * All following calls of the method returns the guid created in the initial call.
     * @return The metadata guid for this cumulus record.
     */
    public String getMetadataGUID() {
        if(metadataGuid == null) {
            metadataGuid = getFieldValueOrNull(Constants.FieldNames.METADATA_GUID);
            if(metadataGuid == null) {
                try {
                    metadataGuid = UUID.randomUUID().toString();
                    GUID metadataIdGuid = fe.getFieldGUID(Constants.FieldNames.METADATA_GUID);
                    item.setStringValue(metadataIdGuid, metadataGuid);
                    item.save();
                } catch (Exception e) {
                    String errMsg = "Could not set the package id for the metadata.";
                    log.error(errMsg, e);
                    throw new IllegalStateException(errMsg, e);
                }
            }
        }

        return metadataGuid;
    }
    
    /**
     * Retrieves the preservation collection ID.
     * @return The preservation collection ID.
     */
    public String getPreservationCollectionID() {
        if(preservationCollectionID == null) {
            preservationCollectionID = getFieldValue(Constants.FieldNames.COLLECTION_ID);
        }
        return preservationCollectionID;
    }

    /**
     * Checks whether the record has any sub-assets, and thus whether it is a master-asset.
     * @return Whether or not this is record is a master-asset.
     */
    public boolean isMasterAsset() {
        GUID fieldGuid = fe.getFieldGUID(Constants.FieldNames.RELATED_SUB_ASSETS);
        return item.hasValue(fieldGuid);
    }
    
    @Override
    public String toString() {
        return "[CumulusRecord : " + getClass().getCanonicalName() + " -> " + guid + "]";
    }
}
