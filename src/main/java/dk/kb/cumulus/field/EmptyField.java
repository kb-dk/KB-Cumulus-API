package dk.kb.cumulus.field;

import com.canto.cumulus.FieldDefinition;

/**
 * An empty Cumulus field.
 */
public class EmptyField extends Field {
    /**
     * Constructor.
     * @param fieldDefinition The definition.
     * @param fieldType The type.
     */
    public EmptyField(FieldDefinition fieldDefinition, String fieldType) {
        super(fieldDefinition, fieldType);
    }

    @Override
    public boolean isEmpty() {
        return true;
    }
}
