//package dk.kb.cumulus;
//
//import static org.mockito.Matchers.anyString;
//import static org.mockito.Matchers.eq;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.times;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.verifyNoMoreInteractions;
//import static org.mockito.Mockito.verifyZeroInteractions;
//import static org.mockito.Mockito.when;
//import static org.testng.Assert.assertEquals;
//import static org.testng.Assert.assertFalse;
//import static org.testng.Assert.assertNotNull;
//import static org.testng.Assert.assertNull;
//import static org.testng.Assert.assertTrue;
//
//import java.io.File;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.UUID;
//
//import org.jaccept.structure.ExtendedTestCase;
//import org.testng.SkipException;
//import org.testng.annotations.AfterClass;
//import org.testng.annotations.BeforeClass;
//import org.testng.annotations.Test;
//
//import com.canto.cumulus.Asset;
//import com.canto.cumulus.CumulusException;
//import com.canto.cumulus.GUID;
//import com.canto.cumulus.Item;
//import com.canto.cumulus.exceptions.UnresolvableAssetReferenceException;
//import com.canto.cumulus.fieldvalue.AssetReference;
//import com.canto.cumulus.fieldvalue.StringEnumFieldValue;
//
//import dk.kb.cumulus.field.EmptyField;
//import dk.kb.cumulus.field.Field;
//import dk.kb.cumulus.field.StringField;
//import dk.kb.ginnungagap.config.RequiredFields;
//import dk.kb.ginnungagap.testutils.TestFileUtils;
//
//public class CumulusRecordTest extends ExtendedTestCase {
//
//    File testFile = new File("src/test/resources/test-resource.txt");
//
//    @BeforeClass
//    public void setupClass() {
//        TestFileUtils.setup();
//    }
//    
//    @AfterClass
//    public void tearDownClass() {
//        TestFileUtils.tearDown();
//    }
//    
//    @Test
//    public void testConstructor() {
//        addDescription("Test the constructor.");
//        FieldExtractor fe = mock(FieldExtractor.class);
//        Item item = mock(Item.class);
//        CumulusRecord record = new CumulusRecord(fe, item);
//        assertNotNull(record);
//        
//        verifyZeroInteractions(fe);
//        verifyZeroInteractions(item);
//    }
//    
//    @Test
//    public void testInitFields() {
//        // FIXME
//        if(true) throw new SkipException("Fix this test");
////        addDescription("Test the initFields method.");
////        FieldExtractor fe = mock(FieldExtractor.class);
////        Item item = mock(Item.class);
////        CumulusRecord record = new CumulusRecord(fe, item);
////        
////        record.initChecksumField();
//    }
//    
//    @Test
//    public void testGetUUID() {
//        addDescription("Test the getUUID method.");
//        FieldExtractor fe = mock(FieldExtractor.class);
//        Item item = mock(Item.class);
//        
//        GUID fieldGuid = mock(GUID.class);
//        when(fe.getFieldGUID(eq(Constants.FieldNames.GUID))).thenReturn(fieldGuid);
//        
//        CumulusRecord record = new CumulusRecord(fe, item);
//
//        addStep("Test with a UUID", "Returning the whole UUID");
//        String uuid1 = UUID.randomUUID().toString();
//        when(item.getStringValue(eq(fieldGuid))).thenReturn(uuid1);
//        assertEquals(record.getUUID(), uuid1);
//
//        verify(fe).getFieldGUID(eq(Constants.FieldNames.GUID));
//        verifyNoMoreInteractions(fe);
//        
//        verify(item).getStringValue(eq(fieldGuid));
//        verifyNoMoreInteractions(item);
//    }
//    
//    @Test
//    public void testGetFieldValue() {
//        addDescription("Test the getFieldValue method.");
//        FieldExtractor fe = mock(FieldExtractor.class);
//        Item item = mock(Item.class);
//        
//        String fieldName = UUID.randomUUID().toString();
//        String fieldValue = UUID.randomUUID().toString();
//        
//        GUID fieldGuid = mock(GUID.class);
//        when(fe.getFieldGUID(eq(fieldName))).thenReturn(fieldGuid);
//        when(item.getStringValue(eq(fieldGuid))).thenReturn(fieldValue);
//        
//        CumulusRecord record = new CumulusRecord(fe, item);
//        
//        assertEquals(record.getFieldValue(fieldName), fieldValue);
//        
//        verify(fe).getFieldGUID(eq(fieldName));
//        verifyNoMoreInteractions(fe);
//        
//        verify(item).getStringValue(eq(fieldGuid));
//        verifyNoMoreInteractions(item);
//    }
//    
//    @Test
//    public void testGetFieldValueOrNull() {
//        addDescription("Test the getFieldValueOrNull method.");
//        FieldExtractor fe = mock(FieldExtractor.class);
//        Item item = mock(Item.class);
//        
//        String fieldName = UUID.randomUUID().toString();
//        String fieldValue = UUID.randomUUID().toString();
//        
//        GUID fieldGuid = mock(GUID.class);
//        when(fe.getFieldGUID(eq(fieldName))).thenReturn(fieldGuid);
//        when(item.getStringValue(eq(fieldGuid))).thenReturn(fieldValue);
//        
//        CumulusRecord record = new CumulusRecord(fe, item);
//        
//        addStep("Test when the item has a value", "Returns the value");
//        when(item.hasValue(eq(fieldGuid))).thenReturn(true);
//        assertEquals(record.getFieldValueOrNull(fieldName), fieldValue);
//        
//        addStep("Test when the item does not have a value", "Returns a null");
//        when(item.hasValue(eq(fieldGuid))).thenReturn(false);
//        assertNull(record.getFieldValueOrNull(fieldName));
//        
//        verify(fe, times(2)).getFieldGUID(eq(fieldName));
//        verifyNoMoreInteractions(fe);
//        
//        verify(item).getStringValue(eq(fieldGuid));
//        verify(item, times(2)).hasValue(fieldGuid);
//        verifyNoMoreInteractions(item);
//    }
//
//    @Test
//    public void testGetFieldLongValue() {
//        addDescription("Test the getFieldLongValue method.");
//        FieldExtractor fe = mock(FieldExtractor.class);
//        Item item = mock(Item.class);
//        
//        String fieldName = UUID.randomUUID().toString();
//        Long fieldValue = Long.MAX_VALUE;
//        
//        GUID fieldGuid = mock(GUID.class);
//        when(fe.getFieldGUID(eq(fieldName))).thenReturn(fieldGuid);
//        when(item.getLongValue(eq(fieldGuid))).thenReturn(fieldValue);
//        
//        CumulusRecord record = new CumulusRecord(fe, item);
//        
//        assertEquals(record.getFieldLongValue(fieldName), fieldValue);
//        
//        verify(fe).getFieldGUID(eq(fieldName));
//        verifyNoMoreInteractions(fe);
//        
//        verify(item).getLongValue(eq(fieldGuid));
//        verifyNoMoreInteractions(item);
//    }
//    
//    @Test
//    public void testGetFieldValueForNonStringField() {
//        addDescription("Test the getFieldValueForNonStringField method.");
//        FieldExtractor fe = mock(FieldExtractor.class);
//        Item item = mock(Item.class);
//
//        String fieldName = UUID.randomUUID().toString();
//        String fieldValue = UUID.randomUUID().toString();
//
//        CumulusRecord record = new CumulusRecord(fe, item);
//        
//        when(fe.getStringValueForField(eq(fieldName), eq(item))).thenReturn(fieldValue);
//        
//        assertEquals(record.getFieldValueForNonStringField(fieldName), fieldValue);
//
//        verify(fe).getStringValueForField(eq(fieldName), eq(item));
//        verifyNoMoreInteractions(fe);
//        
//        verifyZeroInteractions(item);
//    }
//    
//    @Test(expectedExceptions = IllegalStateException.class)
//    public void testGetMetadataFailure() {
//        addDescription("Test the getMetadata when it fails to write to the output file.");
//        FieldExtractor fe = mock(FieldExtractor.class);
//        Item item = mock(Item.class);
//        
//        File outputFile = new File(TestFileUtils.getTempDir(), UUID.randomUUID().toString());
//        
//        when(fe.getAllFields(eq(item))).thenReturn(new HashMap<String, Field>());
//        
//        CumulusRecord record = new CumulusRecord(fe, item);
//        
//        try {
//            outputFile.getParentFile().setWritable(false);
//            record.getMetadata(outputFile);
//        } finally {
//            outputFile.getParentFile().setWritable(true);
//        }
//    }
//    
//    @Test
//    public void testGetFileSuccess() throws Exception {
//        addDescription("Test the getFile method for the succes scenario.");
//        FieldExtractor fe = mock(FieldExtractor.class);
//        Item item = mock(Item.class);
//        
//        AssetReference reference = mock(AssetReference.class);
//        Asset asset = mock(Asset.class);
//        
//        CumulusRecord record = new CumulusRecord(fe, item);
//        
//        when(item.getAssetReferenceValue(eq(GUID.UID_REC_ASSET_REFERENCE))).thenReturn(reference);
//        when(reference.getAsset(eq(CumulusRecord.ASSET_NOT_ALLOW_PROXY))).thenReturn(asset);
//        when(asset.getAsFile()).thenReturn(testFile);
//        
//        File f = record.getFile();
//        assertNotNull(f);
//        assertEquals(f.getAbsolutePath(), testFile.getAbsolutePath());
//        
//        verifyZeroInteractions(fe);
//        
//        verify(item).getAssetReferenceValue(eq(GUID.UID_REC_ASSET_REFERENCE));
//        verifyNoMoreInteractions(item);
//        
//        verify(reference).getAsset(eq(CumulusRecord.ASSET_NOT_ALLOW_PROXY));
//        verifyNoMoreInteractions(reference);
//        
//        verify(asset).getAsFile();
//        verifyNoMoreInteractions(asset);
//    }
//    
//    @Test(expectedExceptions = IllegalStateException.class)
//    public void testGetFileFailure() throws Exception {
//        addDescription("Test the getFile method when it cannot get the file.");
//        FieldExtractor fe = mock(FieldExtractor.class);
//        Item item = mock(Item.class);
//        
//        CumulusRecord record = new CumulusRecord(fe, item);
//        
//        when(item.getAssetReferenceValue(eq(GUID.UID_REC_ASSET_REFERENCE))).thenThrow(new RuntimeException("Test the failure."));
//        
//        record.getFile();
//    }
//    
//    @Test
//    public void testSetNewAssetReferenceSuccess() throws Exception {
//        // FIXME
//        if(true) throw new SkipException("Fix this test - cannot use mock CumulusSession");
////        addDescription("Test the setNewAssetReference method for the succes scenario.");
////        FieldExtractor fe = mock(FieldExtractor.class);
////        Item item = mock(Item.class);
////        CumulusSession session = mock(CumulusSession.class);
////        
////        when(item.getCumulusSession()).thenReturn(session);
////        
////        CumulusRecord record = new CumulusRecord(fe, item);
////        
////        record.setNewAssetReference(testFile);
////        
////        verifyZeroInteractions(fe);
////        
////        verify(item).setAssetReferenceValue(eq(GUID.UID_REC_ASSET_REFERENCE), any(AssetReference.class));
////        verify(item).save();
////        verifyNoMoreInteractions(item);
//    }
//    
//    @Test(expectedExceptions = IllegalStateException.class)
//    public void testSetNewAssetReferenceFailure() throws Exception {
//        addDescription("Test the setNewAssetReference method when it fails.");
//        FieldExtractor fe = mock(FieldExtractor.class);
//        Item item = mock(Item.class);
//        
//        CumulusRecord record = new CumulusRecord(fe, item);
//        
//        when(item.getCumulusSession()).thenThrow(new RuntimeException("Test the failure."));
//        
//        record.setNewAssetReference(testFile);
//    }
//    
//    @Test
//    public void testUpdateAssetReference() {
//        addDescription("Test the updateAssetReference method.");
//        FieldExtractor fe = mock(FieldExtractor.class);
//        Item item = mock(Item.class);
//        
//        CumulusRecord record = new CumulusRecord(fe, item);
//        
//        record.updateAssetReference();
//        
//        verify(item).updateAssetReference();
//        verify(item).save();
//        verifyNoMoreInteractions(item);
//        
//        verifyZeroInteractions(fe);
//    }
//    
//    @Test
//    public void testWriteMetadataFile() {
//        // TODO
//        if(true) throw new SkipException("Implement this test");
//    }
//    
//    @Test
//    public void testAddCumulusFieldToMetadataOutput() {
//        // TODO
//        if(true) throw new SkipException("Implement this test");
//    }
//    
//    @Test
//    public void testGetValues() {
//        addDescription("Test the getValues.");
//        FieldExtractor fe = mock(FieldExtractor.class);
//        Item item = mock(Item.class);
//        String value = UUID.randomUUID().toString();
//        
//        CumulusRecord record = new CumulusRecord(fe, item);
//        assertEquals(record.getValues(value)[0], value);
//    }
//    
//    @Test
//    public void testValidateRequiredFieldsSuccesValidBaseField() {
//        addDescription("Test the validateRequiredFields when it has a single valid base-field.");
//        FieldExtractor fe = mock(FieldExtractor.class);
//        Item item = mock(Item.class);
//        
//        String fieldName = UUID.randomUUID().toString();
//        Map<String, Field> fieldMap = new HashMap<String, Field>();
//        fieldMap.put(fieldName, new StringField(null, null, UUID.randomUUID().toString()));
//        
//        RequiredFields requiredFields = new RequiredFields(Arrays.asList(fieldName), new ArrayList<String>());
//        when(fe.getAllFields(eq(item))).thenReturn(fieldMap);
//        
//        CumulusRecord record = new CumulusRecord(fe, item);
//        record.validateRequiredFields(requiredFields);
//        
//        verify(fe).getAllFields(eq(item));
//        verifyNoMoreInteractions(fe);
//        
//        verifyZeroInteractions(item);
//    }
//    
//    @Test(expectedExceptions = IllegalStateException.class)
//    public void testValidateRequiredFieldsFailureValidBaseField() {
//        addDescription("Test the validateRequiredFields when it is missing a base-field.");
//        FieldExtractor fe = mock(FieldExtractor.class);
//        Item item = mock(Item.class);
//        
//        String fieldName = UUID.randomUUID().toString();
//        Map<String, Field> fieldMap = new HashMap<String, Field>();
//        
//        RequiredFields requiredFields = new RequiredFields(Arrays.asList(fieldName), new ArrayList<String>());
//        when(fe.getAllFields(eq(item))).thenReturn(fieldMap);
//        
//        CumulusRecord record = new CumulusRecord(fe, item);
//        record.validateRequiredFields(requiredFields);
//    }
//
//    @Test(expectedExceptions = IllegalStateException.class)
//    public void testValidateRequiredFieldsFailureEmptyBaseField() {
//        addDescription("Test the validateRequiredFields when it has an empty base-field.");
//        FieldExtractor fe = mock(FieldExtractor.class);
//        Item item = mock(Item.class);
//        
//        String fieldName = UUID.randomUUID().toString();
//        Map<String, Field> fieldMap = new HashMap<String, Field>();
//        fieldMap.put(fieldName, new EmptyField(null, null));
//        
//        RequiredFields requiredFields = new RequiredFields(Arrays.asList(fieldName), new ArrayList<String>());
//        when(fe.getAllFields(eq(item))).thenReturn(fieldMap);
//        
//        CumulusRecord record = new CumulusRecord(fe, item);
//        record.validateRequiredFields(requiredFields);
//    }
//    
//    @Test(expectedExceptions = IllegalStateException.class)
//    public void testValidateRequiredFieldsFailureValidWritableField() {
//        addDescription("Test the validateRequiredFields when it is missing a writable-field.");
//        FieldExtractor fe = mock(FieldExtractor.class);
//        Item item = mock(Item.class);
//        
//        String fieldName = UUID.randomUUID().toString();
//        Map<String, Field> fieldMap = new HashMap<String, Field>();
//        
//        RequiredFields requiredFields = new RequiredFields(new ArrayList<String>(), Arrays.asList(fieldName));
//        when(fe.getAllFields(eq(item))).thenReturn(fieldMap);
//        
//        CumulusRecord record = new CumulusRecord(fe, item);
//        record.validateRequiredFields(requiredFields);
//    }
//    
//    @Test
//    public void testInitChecksumFieldSuccessAlreadyHasValue() {
//        addDescription("Test the initChecksumField method for the success scenario when it already have a value.");
//        FieldExtractor fe = mock(FieldExtractor.class);
//        Item item = mock(Item.class);
//
//        GUID fieldGuid = mock(GUID.class);
//        String fieldValue = UUID.randomUUID().toString();
//        
//        when(fe.getFieldGUID(eq(Constants.FieldNames.CHECKSUM_ORIGINAL_MASTER))).thenReturn(fieldGuid);
//        when(item.hasValue(eq(fieldGuid))).thenReturn(true);
//        when(item.getStringValue(eq(fieldGuid))).thenReturn(fieldValue);
//        
//        CumulusRecord record = new CumulusRecord(fe, item);
//        record.initChecksumField();
//        
//        verify(fe).getFieldGUID(eq(Constants.FieldNames.CHECKSUM_ORIGINAL_MASTER));
//        verifyNoMoreInteractions(fe);
//        
//        verify(item).hasValue(eq(fieldGuid));
//        verify(item).getStringValue(eq(fieldGuid));
//        verifyNoMoreInteractions(item);
//    }
//    
//    @Test
//    public void testInitChecksumFieldSuccessNewValue() throws CumulusException, UnresolvableAssetReferenceException {
//        addDescription("Test the initChecksumField method for the success scenario when it must calculate a value.");
//        FieldExtractor fe = mock(FieldExtractor.class);
//        Item item = mock(Item.class);
//
//        GUID fieldGuid = mock(GUID.class);
//        AssetReference reference = mock(AssetReference.class);
//        Asset asset = mock(Asset.class);
//                
//        when(item.getAssetReferenceValue(eq(GUID.UID_REC_ASSET_REFERENCE))).thenReturn(reference);
//        when(reference.getAsset(eq(CumulusRecord.ASSET_NOT_ALLOW_PROXY))).thenReturn(asset);
//        when(asset.getAsFile()).thenReturn(testFile);
//        
//        when(fe.getFieldGUID(eq(Constants.FieldNames.CHECKSUM_ORIGINAL_MASTER))).thenReturn(fieldGuid);
//        when(item.hasValue(eq(fieldGuid))).thenReturn(false);
//        
//        CumulusRecord record = new CumulusRecord(fe, item);
//        record.initChecksumField();
//        
//        verify(fe, times(2)).getFieldGUID(eq(Constants.FieldNames.CHECKSUM_ORIGINAL_MASTER));
//        verifyNoMoreInteractions(fe);
//        
//        verify(item).hasValue(eq(fieldGuid));
//        verify(item).setStringValue(eq(fieldGuid), anyString());
//        verify(item).getAssetReferenceValue(GUID.UID_REC_ASSET_REFERENCE);
//        verify(item).save();
//        verifyNoMoreInteractions(item); 
//    }
//    
//    @Test(expectedExceptions = IllegalStateException.class)
//    public void testInitChecksumFieldFailure() throws Exception {
//        addDescription("Test the initChecksumField method for the scenario when it fails.");
//        FieldExtractor fe = mock(FieldExtractor.class);
//        Item item = mock(Item.class);
//
//        GUID fieldGuid = mock(GUID.class);
//                
//        when(item.getAssetReferenceValue(eq(GUID.UID_REC_ASSET_REFERENCE))).thenThrow(new RuntimeException("This must fail."));
//        
//        when(fe.getFieldGUID(eq(Constants.FieldNames.CHECKSUM_ORIGINAL_MASTER))).thenReturn(fieldGuid);
//        when(item.hasValue(eq(fieldGuid))).thenReturn(false);
//        
//        CumulusRecord record = new CumulusRecord(fe, item);
//        record.initChecksumField();
//    }
//    
//    @Test
//    public void testInitRelatedIntellectualEntityObjectIdentifierWhenItHaveValue() {
//        addDescription("Test the initRelatedIntellectualEntityObjectIdentifier method for the success scenario when it has a value.");
//        FieldExtractor fe = mock(FieldExtractor.class);
//        Item item = mock(Item.class);
//
//        GUID fieldGuid = mock(GUID.class);
//        
//        when(fe.getFieldGUID(eq(Constants.FieldNames.RELATED_OBJECT_IDENTIFIER_VALUE_INTELLECTUEL_ENTITY))).thenReturn(fieldGuid);
//        when(item.hasValue(eq(fieldGuid))).thenReturn(true);
//        
//        CumulusRecord record = new CumulusRecord(fe, item);
//        record.initIntellectualEntityUUID();
//        
//        verify(fe).getFieldGUID(eq(Constants.FieldNames.RELATED_OBJECT_IDENTIFIER_VALUE_INTELLECTUEL_ENTITY));
//        verifyNoMoreInteractions(fe);
//        
//        verify(item).hasValue(eq(fieldGuid));
//        verifyNoMoreInteractions(item);    
//    }
//    
//    @Test
//    public void testInitRelatedIntellectualEntityObjectIdentifierWhenItDoesNotHaveValue() {
//        addDescription("Test the initRelatedIntellectualEntityObjectIdentifier method for the success scenario when it does not have a value.");
//        FieldExtractor fe = mock(FieldExtractor.class);
//        Item item = mock(Item.class);
//
//        GUID fieldGuid = mock(GUID.class);
//        
//        when(fe.getFieldGUID(eq(Constants.FieldNames.RELATED_OBJECT_IDENTIFIER_VALUE_INTELLECTUEL_ENTITY))).thenReturn(fieldGuid);
//        when(item.hasValue(eq(fieldGuid))).thenReturn(false);
//        
//        CumulusRecord record = new CumulusRecord(fe, item);
//        record.initIntellectualEntityUUID();
//        
//        verify(fe).getFieldGUID(eq(Constants.FieldNames.RELATED_OBJECT_IDENTIFIER_VALUE_INTELLECTUEL_ENTITY));
//        verifyNoMoreInteractions(fe);
//        
//        verify(item).hasValue(eq(fieldGuid));
//        verify(item).setStringValue(eq(fieldGuid), anyString());
//        verify(item).save();
//        verifyNoMoreInteractions(item);    
//    }
//    
//    @Test
//    public void testSetPreservationFailedSuccess() {
//        addDescription("Test the setPreservationFailed method for the success scenario.");
//        FieldExtractor fe = mock(FieldExtractor.class);
//        Item item = mock(Item.class);
//        
//        String failureMessage = UUID.randomUUID().toString();
//        
//        GUID preservationStatusFieldGuid = mock(GUID.class);
//        GUID qaErrorFieldGuid = mock(GUID.class);
//        StringEnumFieldValue stringEnumValue = mock(StringEnumFieldValue.class);
//        
//        when(fe.getFieldGUID(eq(Constants.FieldNames.PRESERVATION_STATUS))).thenReturn(preservationStatusFieldGuid);
//        when(fe.getFieldGUID(eq(Constants.FieldNames.QA_ERROR))).thenReturn(qaErrorFieldGuid);
//        when(item.getStringEnumValue(eq(preservationStatusFieldGuid))).thenReturn(stringEnumValue);
//        
//        CumulusRecord record = new CumulusRecord(fe, item);
//        record.setPreservationFailed(failureMessage);
//        
//        verify(stringEnumValue).setFromDisplayString(Constants.FieldValues.PRESERVATIONSTATE_ARCHIVAL_FAILED);
//        verifyNoMoreInteractions(stringEnumValue);
//        
//        verify(fe).getFieldGUID(eq(Constants.FieldNames.PRESERVATION_STATUS));
//        verify(fe).getFieldGUID(eq(Constants.FieldNames.QA_ERROR));
//        verifyNoMoreInteractions(fe);
//        
//        verify(item).getStringEnumValue(eq(preservationStatusFieldGuid));
//        verify(item).setStringEnumValue(eq(preservationStatusFieldGuid), eq(stringEnumValue));
//        verify(item).setStringValue(eq(qaErrorFieldGuid), eq(failureMessage));
//        verify(item).save();
//        verifyNoMoreInteractions(item);    
//    }
//    
//    @Test(expectedExceptions = IllegalStateException.class)
//    public void testSetPreservationFailedFailure() {
//        addDescription("Test the setPreservationFailed method for the failure scenario.");
//        FieldExtractor fe = mock(FieldExtractor.class);
//        Item item = mock(Item.class);
//        
//        String failureMessage = UUID.randomUUID().toString();
//        
//        when(fe.getFieldGUID(eq(Constants.FieldNames.PRESERVATION_STATUS))).thenThrow(new RuntimeException("This must fail"));
//        
//        CumulusRecord record = new CumulusRecord(fe, item);
//        record.setPreservationFailed(failureMessage);
//    }
//    
//    @Test
//    public void testSetPreservationFinishedSuccess() {
//        addDescription("Test the setPreservationFinished method for the success scenario.");
//        FieldExtractor fe = mock(FieldExtractor.class);
//        Item item = mock(Item.class);
//        
//        GUID preservationStatusFieldGuid = mock(GUID.class);
//        GUID qaErrorFieldGuid = mock(GUID.class);
//        StringEnumFieldValue stringEnumValue = mock(StringEnumFieldValue.class);
//        
//        when(fe.getFieldGUID(eq(Constants.FieldNames.PRESERVATION_STATUS))).thenReturn(preservationStatusFieldGuid);
//        when(fe.getFieldGUID(eq(Constants.FieldNames.QA_ERROR))).thenReturn(qaErrorFieldGuid);
//        when(item.getStringEnumValue(eq(preservationStatusFieldGuid))).thenReturn(stringEnumValue);
//        
//        CumulusRecord record = new CumulusRecord(fe, item);
//        record.setPreservationFinished();
//        
//        verify(stringEnumValue).setFromDisplayString(Constants.FieldValues.PRESERVATIONSTATE_ARCHIVAL_COMPLETED);
//        verifyNoMoreInteractions(stringEnumValue);
//        
//        verify(fe).getFieldGUID(eq(Constants.FieldNames.PRESERVATION_STATUS));
//        verify(fe).getFieldGUID(eq(Constants.FieldNames.QA_ERROR));
//        verifyNoMoreInteractions(fe);
//        
//        verify(item).getStringEnumValue(eq(preservationStatusFieldGuid));
//        verify(item).setStringEnumValue(eq(preservationStatusFieldGuid), eq(stringEnumValue));
//        verify(item).setStringValue(eq(qaErrorFieldGuid), eq(""));
//        verify(item).save();
//        verifyNoMoreInteractions(item);
//    }
//    
//    @Test(expectedExceptions = IllegalStateException.class)
//    public void testSetPreservationFinishedFailure() {
//        addDescription("Test the setPreservationFinished method for the failure scenario.");
//        FieldExtractor fe = mock(FieldExtractor.class);
//        Item item = mock(Item.class);
//        
//        when(fe.getFieldGUID(eq(Constants.FieldNames.PRESERVATION_STATUS))).thenThrow(new RuntimeException("This must fail."));
//        
//        CumulusRecord record = new CumulusRecord(fe, item);
//        record.setPreservationFinished();
//    }
//
//    @Test
//    public void testSetStringValueInFieldSuccess() {
//        addDescription("Test the setStringValueInField method for the success scenario.");
//        FieldExtractor fe = mock(FieldExtractor.class);
//        Item item = mock(Item.class);
//        
//        String fieldName = UUID.randomUUID().toString();
//        String newFieldValue = UUID.randomUUID().toString();
//        
//        GUID fieldGuid = mock(GUID.class);
//        when(fe.getFieldGUID(eq(fieldName))).thenReturn(fieldGuid);
//        
//        CumulusRecord record = new CumulusRecord(fe, item);
//        record.setStringValueInField(fieldName, newFieldValue);
//        
//        verify(fe).getFieldGUID(eq(fieldName));
//        verifyNoMoreInteractions(fe);
//        
//        verify(item).setStringValue(eq(fieldGuid), eq(newFieldValue));
//        verify(item).save();
//        verifyNoMoreInteractions(item);
//    }
//    
//    @Test(expectedExceptions = IllegalStateException.class, enabled = false)
//    public void testSetStringValueInFieldFailure() {
//        addDescription("Test the setStringValueInField method for the failure scenario.");
//        FieldExtractor fe = mock(FieldExtractor.class);
//        Item item = mock(Item.class);
//        
//        String fieldName = UUID.randomUUID().toString();
//        String newFieldValue = UUID.randomUUID().toString();
//        
//        when(fe.getFieldGUID(eq(fieldName))).thenThrow(new RuntimeException("This must fail"));
//        
//        CumulusRecord record = new CumulusRecord(fe, item);
//        record.setStringValueInField(fieldName, newFieldValue);
//    }
//    
//    @Test
//    public void testGetMetadataGUIDWhenVariableIsSet() {
//        addDescription("Test the getMetadataGUID method when the metadata-guid variable already is set.");
//        FieldExtractor fe = mock(FieldExtractor.class);
//        Item item = mock(Item.class);
//        
//        String metadataGuid = UUID.randomUUID().toString();
//        
//        CumulusRecord record = new CumulusRecord(fe, item);
//        record.metadataGuid = metadataGuid;
//        
//        assertEquals(record.getMetadataGUID(), metadataGuid);
//        
//        verifyZeroInteractions(fe);
//        verifyZeroInteractions(item);
//    }
//    
//    @Test
//    public void testGetMetadataGUIDWhenValueExistsInItem() {
//        addDescription("Test the getMetadataGUID method when the Cumulus field have a value.");
//        FieldExtractor fe = mock(FieldExtractor.class);
//        Item item = mock(Item.class);
//        
//        String metadataGuid = UUID.randomUUID().toString();
//        GUID fieldGuid = mock(GUID.class);
//
//        when(fe.getFieldGUID(eq(Constants.FieldNames.METADATA_GUID))).thenReturn(fieldGuid);
//        when(item.getStringValue(eq(fieldGuid))).thenReturn(metadataGuid);
//        when(item.hasValue(eq(fieldGuid))).thenReturn(true);
//        
//        CumulusRecord record = new CumulusRecord(fe, item);
//        
//        assertEquals(record.getMetadataGUID(), metadataGuid);
//        
//        verify(fe).getFieldGUID(eq(Constants.FieldNames.METADATA_GUID));
//        verifyNoMoreInteractions(fe);
//        
//        verify(item).getStringValue(eq(fieldGuid));
//        verify(item).hasValue(eq(fieldGuid));
//        verifyNoMoreInteractions(item);
//    }
//    
//    @Test
//    public void testGetMetadataGUIDWhenNewValueMustBeMade() {
//        addDescription("Test the getMetadataGUID method when the Cumulus field does not have a value.");
//        FieldExtractor fe = mock(FieldExtractor.class);
//        Item item = mock(Item.class);
//        
//        GUID fieldGuid = mock(GUID.class);
//
//        when(fe.getFieldGUID(eq(Constants.FieldNames.METADATA_GUID))).thenReturn(fieldGuid);
//        when(item.hasValue(eq(fieldGuid))).thenReturn(false);
//        
//        CumulusRecord record = new CumulusRecord(fe, item);
//        
//        assertNull(record.metadataGuid);
//        String metadataGuid = record.getMetadataGUID();
//        assertEquals(record.metadataGuid, metadataGuid);
//        
//        verify(fe, times(2)).getFieldGUID(eq(Constants.FieldNames.METADATA_GUID));
//        verifyNoMoreInteractions(fe);
//        
//        verify(item).setStringValue(eq(fieldGuid), anyString());
//        verify(item).hasValue(eq(fieldGuid));
//        verify(item).save();
//        verifyNoMoreInteractions(item);
//    }
//    
//    @Test(expectedExceptions = IllegalStateException.class)
//    public void testGetMetadataGUIDWhenItFailsToMakeNewMetadataUUID() {
//        addDescription("Test the getMetadataGUID method when the Cumulus field does not have a value.");
//        FieldExtractor fe = mock(FieldExtractor.class);
//        Item item = mock(Item.class);
//        
//        GUID fieldGuid = mock(GUID.class);
//
//        when(fe.getFieldGUID(eq(Constants.FieldNames.METADATA_GUID))).thenReturn(fieldGuid).thenThrow(new RuntimeException("This must fail."));
//        when(item.hasValue(eq(fieldGuid))).thenReturn(false);
//        
//        CumulusRecord record = new CumulusRecord(fe, item);
//        record.getMetadataGUID();
//    }
//    
//    @Test
//    public void testIsMasterAsset() {
//        addDescription("Test the isMasterAsset method.");
//        FieldExtractor fe = mock(FieldExtractor.class);
//        Item item = mock(Item.class);
//        
//        GUID fieldGuid = mock(GUID.class);
//        when(fe.getFieldGUID(eq(Constants.FieldNames.RELATED_SUB_ASSETS))).thenReturn(fieldGuid);
//        
//        CumulusRecord record = new CumulusRecord(fe, item);
//        
//        when(item.hasValue(fieldGuid)).thenReturn(true);
//        assertTrue(record.isMasterAsset());
//        
//        when(item.hasValue(fieldGuid)).thenReturn(false);
//        assertFalse(record.isMasterAsset());
//        
//        verify(fe, times(2)).getFieldGUID(eq(Constants.FieldNames.RELATED_SUB_ASSETS));
//        verifyNoMoreInteractions(fe);
//        
//        verify(item, times(2)).hasValue(eq(fieldGuid));
//        verifyNoMoreInteractions(item);
//    }
//    
//    @Test
//    public void testToString() {
//        // TODO
//        if(true) throw new SkipException("Implement this test");
//    }
//}
