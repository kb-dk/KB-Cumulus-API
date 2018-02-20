package dk.kb.cumulus.utils;

import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

import static org.testng.Assert.assertFalse;

import org.jaccept.structure.ExtendedTestCase;
import org.testng.Assert;
import org.testng.annotations.Test;

public class StringUtilsTest extends ExtendedTestCase {

    @Test
    public void testConstructor() {
        addDescription("Test the String Utils constructor.");
        StringUtils su = new StringUtils();
        assertNotNull(su);
    }
    
    @Test
    public void testReplacingSpacesWithTabs() {
        addDescription("Test replace spaces with tabs");
        String s = "asdf fdsa 1 2 3 4 5 6 7 8 9 0";
        assertTrue(s.contains(" "));
        assertFalse(s.contains("\t"));
        String res = StringUtils.replaceSpacesToTabs(s);
        assertFalse(res.contains(" "));
        assertTrue(res.contains("\t"));
    }
    
    @Test
    public void testEmptyListToString() {
        addDescription("Test changing an empty list to a string.");
        String res = StringUtils.listToString(new ArrayList<String>(), "\n");
        assertEquals(res, StringUtils.EMPTY_LIST);
    }

    @Test
    public void testNullListToString() {
        addDescription("Test changing an empty list to a string.");
        String res = StringUtils.listToString(null, "\n");
        assertEquals(res, StringUtils.EMPTY_LIST);
    }
    
    @Test
    public void testListToString() {
        addDescription("Test changing an empty list to a string.");
        String s1 = "foo";
        String s2 = "bar";
        String separator1 = "\n";
        String separator2 = "This is a very peculiar separator";
        
        String res1 = StringUtils.listToString(Arrays.asList(s1, s2), separator1);
        assertTrue(res1.contains(s1));
        assertTrue(res1.contains(s2));
        assertTrue(res1.contains(separator1));
        assertFalse(res1.contains(separator2));

        String res2 = StringUtils.listToString(Arrays.asList(s1, s2), separator2);
        assertTrue(res2.contains(s1));
        assertTrue(res2.contains(s2));
        assertFalse(res2.contains(separator1));
        assertTrue(res2.contains(separator2));
    }
    
    @Test
    public void testXmlEncode() {
        addDescription("Testing the encoding of text into XML values.");
        String s = "<test this>";
        assertTrue(s.contains("<"));
        assertTrue(s.contains(">"));
        
        String res = StringUtils.xmlEncode(s);
        assertFalse(res.contains("<"));
        assertFalse(res.contains(">"));
    }
    
    @Test
    public void testIsNullOrEmptyWhenNull() {
        addDescription("Test the isNullOrEmpty when the string is null");
        Assert.assertTrue(StringUtils.isNullOrEmpty(null));
    }
    
    @Test
    public void testIsNullOrEmptyWhenEmpty() {
        addDescription("Test the isNullOrEmpty when the string is empty");
        Assert.assertTrue(StringUtils.isNullOrEmpty(""));
    }
    
    @Test
    public void testIsNullOrEmptyWhenNeither() {
        addDescription("Test the isNullOrEmpty when the string has content");
        Assert.assertFalse(StringUtils.isNullOrEmpty(UUID.randomUUID().toString()));
    }
}
