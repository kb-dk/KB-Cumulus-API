package dk.kb.cumulus.field;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

import org.jaccept.structure.ExtendedTestCase;
import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.canto.cumulus.FieldDefinition;
import com.canto.cumulus.FieldTypes;
import com.canto.cumulus.GUID;
import com.canto.cumulus.Item;
import com.canto.cumulus.ItemCollection;
import com.canto.cumulus.Layout;

import dk.kb.cumulus.FieldExtractor;

public class TableFieldTest extends ExtendedTestCase {

    @Test
    public void defaultFieldTest() {
        addDescription("The default field test for the Table field.");
        FieldDefinition fieldDefinition = Mockito.mock(FieldDefinition.class);
        String fieldType = UUID.randomUUID().toString();
        String name = UUID.randomUUID().toString();
        
        ItemCollection itemCollection = Mockito.mock(ItemCollection.class);
        FieldExtractor fe = Mockito.mock(FieldExtractor.class);
        
        Mockito.when(fieldDefinition.getName()).thenReturn(name);
        Mockito.when(fieldDefinition.isEditable()).thenReturn(false);
        
        Mockito.when(itemCollection.iterator()).thenReturn((new ArrayList<Item>()).iterator());
        
        TableField f = new TableField(fieldDefinition, fieldType, itemCollection, fe);
        
        Assert.assertEquals(f.getName(), name);
        Assert.assertEquals(f.getType(), fieldType);
        Assert.assertFalse(f.isFieldEditable());
    }

    @Test
    public void tableFieldEmptyTest() {
        addDescription("Test table field when the table is empty.");
        FieldDefinition fieldDefinition = Mockito.mock(FieldDefinition.class);
        String fieldType = UUID.randomUUID().toString();
        
        ItemCollection itemCollection = Mockito.mock(ItemCollection.class);
        FieldExtractor fe = Mockito.mock(FieldExtractor.class);
        
        Mockito.when(itemCollection.iterator()).thenReturn((new ArrayList<Item>()).iterator());
        
        TableField f = new TableField(fieldDefinition, fieldType, itemCollection, fe);
        
        Assert.assertTrue(f.isEmpty());
        Assert.assertNotNull(f.getRows());
        Assert.assertTrue(f.getRows().isEmpty());
        Assert.assertEquals(f.getRows().size(), 0);
    }

    @Test
    public void tableFieldNonEmptyTest() {
        addDescription("Test table field when the table is not empty.");
        FieldDefinition fieldDefinition = Mockito.mock(FieldDefinition.class);
        String fieldType = UUID.randomUUID().toString();
        
        String rowName = UUID.randomUUID().toString();
        String rowValue = UUID.randomUUID().toString();
        
        Item item = Mockito.mock(Item.class);
        ItemCollection itemCollection = Mockito.mock(ItemCollection.class);
        
        FieldExtractor fe = Mockito.mock(FieldExtractor.class);

        Layout layout = Mockito.mock(Layout.class);
        FieldDefinition rowFieldDefinition = Mockito.mock(FieldDefinition.class);
        
        Mockito.when(rowFieldDefinition.getFieldType()).thenReturn(FieldTypes.FieldTypeString);
        Mockito.when(rowFieldDefinition.getName()).thenReturn(rowName);
        Mockito.when(item.getStringValue(Mockito.any(GUID.class))).thenReturn(rowValue);
        Mockito.when(item.hasValue(Mockito.any(GUID.class))).thenReturn(true);
        Mockito.when(layout.iterator()).thenReturn(Arrays.asList(rowFieldDefinition).iterator());
        Mockito.when(itemCollection.getLayout()).thenReturn(layout);
        Mockito.when(itemCollection.iterator()).thenReturn(Arrays.asList(item).iterator());
        
        TableField f = new TableField(fieldDefinition, fieldType, itemCollection, fe);
        
        Assert.assertFalse(f.isEmpty());
        Assert.assertNotNull(f.getRows());
        Assert.assertFalse(f.getRows().isEmpty());
        Assert.assertEquals(f.getRows().size(), 1);
        
        Assert.assertEquals(f.getRows().get(0).getElements().size(), 1);
        Assert.assertTrue(f.getRows().get(0).getElements().containsKey(rowName));
        Assert.assertEquals(f.getRows().get(0).getElements().get(rowName), rowValue);
    }
}
