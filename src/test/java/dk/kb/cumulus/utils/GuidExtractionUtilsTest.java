package dk.kb.cumulus.utils;

import java.util.UUID;

import org.jaccept.structure.ExtendedTestCase;
import org.testng.Assert;
import org.testng.annotations.Test;

public class GuidExtractionUtilsTest extends ExtendedTestCase {

    @Test
    public void testConstructor() {
        addDescription("Test the constructor.");
        GuidExtractionUtils geu = new GuidExtractionUtils();
        Assert.assertNotNull(geu);
    }
    
    @Test
    public void testExtractUUIDWithoutEndHash() {
        addDescription("Test extracting an UUID from a ARC-record URL, whith no suffix");
        String expectedUuid = UUID.randomUUID().toString();
        String url = "prefix://" + expectedUuid;

        String uuid = GuidExtractionUtils.extractGuid(url);
        Assert.assertEquals(expectedUuid, uuid);
    }
    
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testExtractGuidFailureNullArgument() {
        addDescription("Test the way the extractGuid method fails, when given a null argument");
        GuidExtractionUtils.extractGuid(null);
    }
    
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testExtractGuidFailureEmptyStringArgument() {
        addDescription("Test the way the extractGuid method fails, when given the empty string as argument");
        GuidExtractionUtils.extractGuid("");
    }
    
    @Test
    public void testExtractGuidContainingHash() {
        addDescription("Test the extractGuid method, when the GUID contains a hash (#)");
        String prefix = UUID.randomUUID().toString();
        String suffix = UUID.randomUUID().toString();
        String guid = GuidExtractionUtils.extractGuid(prefix + "#" + suffix);
        Assert.assertEquals(guid, prefix);
    }
}
