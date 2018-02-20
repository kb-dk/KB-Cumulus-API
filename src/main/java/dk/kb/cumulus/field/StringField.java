package dk.kb.cumulus.field;

import com.canto.cumulus.FieldDefinition;

/**
 * Container for Cumulus field.
 */
public class StringField extends Field {
    /** The string value. This is only used, if the value of the field can be converted into a String. 
     * Otherwise the byteValue is used.*/
    protected String stringValue = null;
    
    /**
     * Constructor, for a string value.
     * @param fieldDefinition The definition of the field.
     * @param fieldType The type of field.
     * @param value The string value of the field.
     */
    public StringField(FieldDefinition fieldDefinition, String fieldType, String value) {
        super(fieldDefinition, fieldType);
        this.stringValue = value;
    }
    
    /**
     * @return Whether or not the field has a string value.
     */
    public boolean hasStringValue() {
        return stringValue != null;
    }
    
    /**
     * @return The string value.
     */
    public String getStringValue() {
        return stringValue;
    }
    
    /**
     * @return Whether or not it contains any actual value (spaces are ignored).
     */
    @Override
    public boolean isEmpty() {
        if(stringValue != null && stringValue.trim().length() > 0) {
            return false;
        }
        return true;
    }
}
