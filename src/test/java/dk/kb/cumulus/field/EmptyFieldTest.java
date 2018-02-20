package dk.kb.cumulus.field;

import java.util.UUID;

import org.jaccept.structure.ExtendedTestCase;
import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.canto.cumulus.FieldDefinition;

public class EmptyFieldTest extends ExtendedTestCase {

    @Test
    public void defaultFieldTest() {
        addDescription("The default field test for the empty field.");
        FieldDefinition fieldDefinition = Mockito.mock(FieldDefinition.class);
        String fieldType = UUID.randomUUID().toString();
        String name = UUID.randomUUID().toString();
        
        Mockito.when(fieldDefinition.getName()).thenReturn(name);
        Mockito.when(fieldDefinition.isEditable()).thenReturn(false);
        Field f = new EmptyField(fieldDefinition, fieldType);
        
        Assert.assertEquals(f.getName(), name);
        Assert.assertEquals(f.getType(), fieldType);
        Assert.assertFalse(f.isFieldEditable());
    }

    @Test
    public void emptyFieldSpecificsTest() {
        addDescription("Test the specifics for the empty field.");
        FieldDefinition fieldDefinition = Mockito.mock(FieldDefinition.class);
        String fieldType = UUID.randomUUID().toString();
        
        Field f = new EmptyField(fieldDefinition, fieldType);
        
        Assert.assertTrue(f.isEmpty());
    }
}
