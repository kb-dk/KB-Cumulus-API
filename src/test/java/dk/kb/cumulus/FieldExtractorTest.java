package dk.kb.cumulus;

import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.UUID;

import org.jaccept.structure.ExtendedTestCase;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.canto.cumulus.FieldDefinition;
import com.canto.cumulus.FieldTypes;
import com.canto.cumulus.GUID;
import com.canto.cumulus.Item;
import com.canto.cumulus.Layout;
import com.canto.cumulus.fieldvalue.AssetReference;
import com.canto.cumulus.fieldvalue.AssetReferencePart;

import dk.kb.cumulus.field.Field;
import dk.kb.cumulus.field.StringField;

public class FieldExtractorTest extends ExtendedTestCase {

    @Test
    public void testGetFieldTypeName() {
        addDescription("Test all the different kinds of field types, including undefined");
        Layout layout = mock(Layout.class);
        CumulusServer server = mock(CumulusServer.class);
        String catalog = UUID.randomUUID().toString();
        
        FieldExtractor fe = new FieldExtractor(layout, server, catalog);
        
        String type;
        
        addStep("FieldTypes.FieldTypeBool", "boolean");
        type = fe.getFieldTypeName(FieldTypes.FieldTypeBool);
        Assert.assertEquals(type, "boolean");
        
        addStep("FieldTypes.FieldTypeDate", "date");
        type = fe.getFieldTypeName(FieldTypes.FieldTypeDate);
        Assert.assertEquals(type, "date");

        addStep("FieldTypes.FieldTypeDouble", "double");
        type = fe.getFieldTypeName(FieldTypes.FieldTypeDouble);
        Assert.assertEquals(type, "double");

        addStep("FieldTypes.FieldTypeEnum", "enum");
        type = fe.getFieldTypeName(FieldTypes.FieldTypeEnum);
        Assert.assertEquals(type, "enumerator");

        addStep("FieldTypes.FieldTypeInteger", "int");
        type = fe.getFieldTypeName(FieldTypes.FieldTypeInteger);
        Assert.assertEquals(type, "integer");

        addStep("FieldTypes.FieldTypeLong", "long");
        type = fe.getFieldTypeName(FieldTypes.FieldTypeLong);
        Assert.assertEquals(type, "long");

        addStep("FieldTypes.FieldTypeString", "string");
        type = fe.getFieldTypeName(FieldTypes.FieldTypeString);
        Assert.assertEquals(type, "string");

        addStep("FieldTypes.FieldTypeBinary", "binary");
        type = fe.getFieldTypeName(FieldTypes.FieldTypeBinary);
        Assert.assertEquals(type, "binary");

        addStep("FieldTypes.FieldTypeAudio", "audio");
        type = fe.getFieldTypeName(FieldTypes.FieldTypeAudio);
        Assert.assertEquals(type, "audio");

        addStep("FieldTypes.FieldTypePicture", "picture");
        type = fe.getFieldTypeName(FieldTypes.FieldTypePicture);
        Assert.assertEquals(type, "picture");

        addStep("FieldTypes.FieldTypeTable", "table");
        type = fe.getFieldTypeName(FieldTypes.FieldTypeTable);
        Assert.assertEquals(type, "table");

        addStep("-1", "UNKNOWN");
        type = fe.getFieldTypeName(-1);
        Assert.assertEquals(type, "NOT DEFINED!!!");
    }
    
    @Test
    public void testGetFieldGUIDSuccess() {
        Layout layout = mock(Layout.class);
        CumulusServer server = mock(CumulusServer.class);
        String catalog = UUID.randomUUID().toString();
        
        FieldExtractor fe = new FieldExtractor(layout, server, catalog);
        
        String fieldName = UUID.randomUUID().toString();
        FieldDefinition fd = mock(FieldDefinition.class);
        GUID guid = mock(GUID.class);
        
        when(fd.getName()).thenReturn(fieldName);
        when(fd.getFieldUID()).thenReturn(guid);
        when(layout.iterator()).thenReturn(Arrays.asList(fd).iterator());
        
        GUID res = fe.getFieldGUID(fieldName);
        
        Assert.assertEquals(res, guid);
        
        verify(layout).iterator();
        verifyNoMoreInteractions(layout);
        
        verifyZeroInteractions(server);
        
        verify(fd).getName();
        verify(fd).getFieldUID();
        verifyNoMoreInteractions(fd);
        
        verifyZeroInteractions(guid);
    }
    
    @Test(expectedExceptions = IllegalStateException.class)
    public void testGetFieldGUIDFailure() {
        Layout layout = mock(Layout.class);
        CumulusServer server = mock(CumulusServer.class);
        String catalog = UUID.randomUUID().toString();
        
        FieldExtractor fe = new FieldExtractor(layout, server, catalog);
        
        String fieldName = UUID.randomUUID().toString();
        FieldDefinition fd = mock(FieldDefinition.class);
        
        when(fd.getName()).thenReturn(fieldName);
        when(layout.iterator()).thenReturn(Arrays.asList(fd).iterator());
        
        fe.getFieldGUID("THIS IS NOT THE NAME OF THE FIELD");
    }
    
    @Test
    public void testExtractBinaryFieldForFile() {
        Layout layout = mock(Layout.class);
        CumulusServer server = mock(CumulusServer.class);
        String catalog = UUID.randomUUID().toString();
        
        FieldExtractor fe = new FieldExtractor(layout, server, catalog);
        
        String displayValue = UUID.randomUUID().toString();
        String fieldName = UUID.randomUUID().toString();
        
        Item item = mock(Item.class);
        FieldDefinition fd = mock(FieldDefinition.class);
        GUID fieldGuid = mock(GUID.class);
        AssetReference assetReference = mock(AssetReference.class);
        AssetReferencePart assetReferencePart = mock(AssetReferencePart.class);
        
        when(fd.getName()).thenReturn(fieldName);
        when(fd.getFieldType()).thenReturn(FieldTypes.FieldTypeBinary);
        when(fd.getFieldUID()).thenReturn(fieldGuid);
        when(item.getAssetReferenceValue(eq(fieldGuid))).thenReturn(assetReference);
        when(assetReference.getPart(eq(0))).thenReturn(assetReferencePart);
        when(assetReferencePart.getDisplayString()).thenReturn(displayValue);
        
        Field f = fe.extractBinaryField(fd, item);
        
        Assert.assertTrue(f instanceof StringField);
        Assert.assertFalse(f.isEmpty());
        Assert.assertEquals(f.getName(), fieldName);
        Assert.assertEquals(((StringField) f).getStringValue(), displayValue);
        
        verify(fd, times(4)).getName();
        verify(fd).getFieldType();
        verify(fd).getFieldUID();
        verifyNoMoreInteractions(fd);
        
        verify(item).getAssetReferenceValue(eq(fieldGuid));
        verifyNoMoreInteractions(item);
        
        verifyZeroInteractions(server);
        verifyZeroInteractions(layout);
    }
}
