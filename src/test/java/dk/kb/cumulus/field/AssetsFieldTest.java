package dk.kb.cumulus.field;

import java.util.UUID;

import org.jaccept.structure.ExtendedTestCase;
import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.canto.cumulus.FieldDefinition;

public class AssetsFieldTest extends ExtendedTestCase {

    @Test
    public void defaultFieldTest() {
        addDescription("The default field test for the assets field.");
        FieldDefinition fieldDefinition = Mockito.mock(FieldDefinition.class);
        String fieldType = UUID.randomUUID().toString();
        String name = UUID.randomUUID().toString();
        
        Mockito.when(fieldDefinition.getName()).thenReturn(name);
        Mockito.when(fieldDefinition.isEditable()).thenReturn(false);
        AssetsField f = new AssetsField(fieldDefinition, fieldType);
        
        Assert.assertEquals(f.getName(), name);
        Assert.assertEquals(f.getType(), fieldType);
        Assert.assertFalse(f.isFieldEditable());
    }

    @Test
    public void assetsFieldSpecificTest() {
        addDescription("Test the specifics for the asset field.");
        FieldDefinition fieldDefinition = Mockito.mock(FieldDefinition.class);
        String fieldType = UUID.randomUUID().toString();
        
        AssetsField f = new AssetsField(fieldDefinition, fieldType);
        
        addStep("Test with no assets inserted into the field", "It is empty");
        Assert.assertTrue(f.isEmpty());
        Assert.assertTrue(f.getNames().isEmpty());
        
        addStep("Insert an asset", "Have the asset, and give it index 2");
        String a1Name = UUID.randomUUID().toString();
        String a1Uuid = UUID.randomUUID().toString();
        
        f.addAsset(a1Name, a1Uuid);
        
        Assert.assertFalse(f.isEmpty());
        Assert.assertFalse(f.getNames().isEmpty());
        Assert.assertEquals(f.getNames().size(), 1);
        Assert.assertTrue(f.getNames().contains(a1Name));
        Assert.assertEquals(f.getGuid(a1Name), a1Uuid);
        Assert.assertEquals(f.getIndex(a1Name).intValue(), 2);

        addStep("Insert another asset", "Have the asset, and give it index 3");
        String a2Name = UUID.randomUUID().toString();
        String a2Uuid = UUID.randomUUID().toString();
        
        f.addAsset(a2Name, a2Uuid);
        
        Assert.assertFalse(f.isEmpty());
        Assert.assertFalse(f.getNames().isEmpty());
        Assert.assertEquals(f.getNames().size(), 2);
        Assert.assertTrue(f.getNames().contains(a2Name));
        Assert.assertEquals(f.getGuid(a2Name), a2Uuid);
        Assert.assertEquals(f.getIndex(a2Name).intValue(), 3);        
    }
}
