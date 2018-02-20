package dk.kb.cumulus.field;

import java.util.UUID;

import org.jaccept.structure.ExtendedTestCase;
import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.canto.cumulus.FieldDefinition;

public class StringFieldTest extends ExtendedTestCase {

    @Test
    public void defaultFieldTest() {
        addDescription("The default field test for the string field.");
        FieldDefinition fieldDefinition = Mockito.mock(FieldDefinition.class);
        String fieldType = UUID.randomUUID().toString();
        String name = UUID.randomUUID().toString();
        String value = UUID.randomUUID().toString();
        
        Mockito.when(fieldDefinition.getName()).thenReturn(name);
        Mockito.when(fieldDefinition.isEditable()).thenReturn(false);
        StringField f = new StringField(fieldDefinition, fieldType, value);
        
        Assert.assertEquals(f.getName(), name);
        Assert.assertEquals(f.getType(), fieldType);
        Assert.assertFalse(f.isFieldEditable());
    }
    
    @Test
    public void testStringFieldWithProperStringValue() {
        addDescription("Test the string field when it is given a proper string value.");
        FieldDefinition fieldDefinition = Mockito.mock(FieldDefinition.class);
        String fieldType = UUID.randomUUID().toString();
        String value = UUID.randomUUID().toString();
        
        StringField f = new StringField(fieldDefinition, fieldType, value);
        
        Assert.assertFalse(f.isEmpty());
        Assert.assertTrue(f.hasStringValue());
        Assert.assertEquals(f.getStringValue(), value);
    }
    
    @Test
    public void testStringFieldWithEmptyStringValue() {
        addDescription("Test the string field when it is given an empty string value.");
        FieldDefinition fieldDefinition = Mockito.mock(FieldDefinition.class);
        String fieldType = UUID.randomUUID().toString();
        String value = "";
        
        StringField f = new StringField(fieldDefinition, fieldType, value);
        
        Assert.assertTrue(f.isEmpty());
        Assert.assertTrue(f.hasStringValue());
        Assert.assertEquals(f.getStringValue(), value);
    }
    
    @Test
    public void testStringFieldWithNullValue() {
        addDescription("Test the string field when it is given a null value.");
        FieldDefinition fieldDefinition = Mockito.mock(FieldDefinition.class);
        String fieldType = UUID.randomUUID().toString();
        String value = null;
        
        StringField f = new StringField(fieldDefinition, fieldType, value);
        
        Assert.assertTrue(f.isEmpty());
        Assert.assertFalse(f.hasStringValue());
        Assert.assertEquals(f.getStringValue(), value);
    }
}
