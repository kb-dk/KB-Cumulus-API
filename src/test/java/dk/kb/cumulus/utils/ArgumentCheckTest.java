package dk.kb.cumulus.utils;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;

import java.io.File;
import java.util.Arrays;
import java.util.HashSet;
import java.util.UUID;

import org.jaccept.structure.ExtendedTestCase;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

public class ArgumentCheckTest extends ExtendedTestCase {

    File tempDir = new File("tempDir");
    
    @AfterClass
    public void tearDown() {
        if(tempDir.exists()) {
            for(File f : tempDir.listFiles()) {
                f.delete();
            }
            tempDir.delete();
        }
    }
    
    @Test
    public void testConstructorWithoutCause() {
        addDescription("Test the constructor without the cause");
        String reason = "REASON: " + UUID.randomUUID().toString();
        Exception e = new ArgumentCheck(reason);
        assertEquals(e.getMessage(), reason);
        assertNull(e.getCause());
    }
    
    @Test
    public void testConstructorWithCause() {
        addDescription("Test the constructor with the cause");
        String reason = "REASON: " + UUID.randomUUID().toString();
        Exception cause = new Exception("cause");
        Exception e = new ArgumentCheck(reason, cause);
        assertEquals(e.getMessage(), reason);
        assertEquals(e.getCause(), cause);
    }
    
    @Test(expectedExceptions = ArgumentCheck.class)
    public void testNullObjectFailure() {
        addDescription("Test that the null test failes with a null");
        ArgumentCheck.checkNotNull(null, "TEST");
    }
    
    @Test
    public void testNullObjectSuccess() {
        addDescription("Test the null test on a non-null object");
        ArgumentCheck.checkNotNull(new Object(), "TEST");
    }
    
    @Test(expectedExceptions = ArgumentCheck.class)
    public void testNullOrEmptyStringEmptyFailure() {
        addDescription("Test that the string test failes with an empty string");
        ArgumentCheck.checkNotNullOrEmpty("", "TEST");
    }

    @Test
    public void testNullOrEmptyStringEmptySuccess() {
        addDescription("Test the string test on an non-empty string");
        ArgumentCheck.checkNotNullOrEmpty("NON EMPTY STRING", "TEST");
    }
    
    @Test(expectedExceptions = ArgumentCheck.class)
    public void testNullOrEmptyByteArrayEmptyFailure() {
        addDescription("Test that the byte array test failes on an empty byte array");
        ArgumentCheck.checkNotNullOrEmpty(new byte[0], "TEST");
    }
    
    @Test
    public void testNullOrEmptyByteArrayEmptySuccess() {
        addDescription("Test the byte array test on an non-empty byte array");
        ArgumentCheck.checkNotNullOrEmpty("GNU".getBytes(), "TEST");
    }
    
    @Test(expectedExceptions = ArgumentCheck.class)
    public void testNonNegativeIntFailureNegative() {
        addDescription("Test that the non-negative integer test failes on negative numbers");
        ArgumentCheck.checkNotNegativeInt(-1, "TEST");
    }
    
    @Test
    public void testNonNegativeIntSuccesZero() {
        addDescription("Test the non-negative integer test on zero");
        ArgumentCheck.checkNotNegativeInt(0, "TEST");
    }
    
    @Test
    public void testNonNegativeIntSuccess() {
        addDescription("Test the non-negative integer test on positive numbers");
        ArgumentCheck.checkNotNegativeInt(1, "TEST");
    }

    @Test(expectedExceptions = ArgumentCheck.class)
    public void testPositiveIntFailureNegative() {
        addDescription("Test that the positive integer test failes on negative numbers");
        ArgumentCheck.checkPositiveInt(-1, "TEST");
    }
    
    @Test(expectedExceptions = ArgumentCheck.class)
    public void testPositiveIntFailureZero() {
        addDescription("Test that the positive integer test failes on zero");
        ArgumentCheck.checkPositiveInt(0, "TEST");
    }
    
    @Test
    public void testPositiveIntSucces() {
        addDescription("Test the positive integer test on positive numbers");
        ArgumentCheck.checkPositiveInt(1, "TEST");
    }
    
    @Test(expectedExceptions = ArgumentCheck.class)
    public void testNonNegativeLongFailureNegative() {
        addDescription("Test that the non-negative long test failes on negative numbers");
        ArgumentCheck.checkNotNegativeLong(-1L, "TEST");
    }
    
    @Test
    public void testNonNegativeLongSuccesZero() {
        addDescription("Test the non-negative long test on zero");
        ArgumentCheck.checkNotNegativeLong(0L, "TEST");
    }
    
    @Test
    public void testNonNegativeLongSuccess() {
        addDescription("Test the non-negative long test on positive numbers");
        ArgumentCheck.checkNotNegativeLong(1L, "TEST");
    }
    
    @Test(expectedExceptions = ArgumentCheck.class)
    public void testPositiveLongFailureNegative() {
        addDescription("Test that the positive long test failes on negative numbers");
        ArgumentCheck.checkPositiveLong(-1L, "TEST");
    }
    
    @Test(expectedExceptions = ArgumentCheck.class)
    public void testPositiveLongFailureZero() {
        addDescription("Test that the positive long test failes on zero");
        ArgumentCheck.checkPositiveLong(0L, "TEST");
    }
    
    @Test
    public void testPositiveLongSuccess() {
        addDescription("Test the positive long test on positive numbers");
        ArgumentCheck.checkPositiveLong(1L, "TEST");
    }
    
    @Test(expectedExceptions = ArgumentCheck.class)
    public void testNullOrEmptyCollectionFailure() {
        addDescription("Test that the null or empty collection test failes on an empty collection");
        ArgumentCheck.checkNotNullOrEmpty(new HashSet<Object>(), "TEST");
    }
    
    @Test
    public void testNullOrEmptyCollectionSuccess() {
        addDescription("Test the null or empty collection test on an non-empty collection");
        ArgumentCheck.checkNotNullOrEmpty(Arrays.asList(new Object()), "TEST");
    }
    
    @Test(expectedExceptions = ArgumentCheck.class)
    public void testTrueFailure() {
        addDescription("Test that the true test failes on a false");
        ArgumentCheck.checkTrue(false, "TEST");
    }
    
    @Test
    public void testTrueSucces() {
        addDescription("Test the true test on a true");
        ArgumentCheck.checkTrue(true, "TEST");
    }
    
    @Test(expectedExceptions = ArgumentCheck.class)
    public void testDirectoryFailureMissing() {
        addDescription("Test that the directory test failes on a non-existing file/directory");
        File f = new File(UUID.randomUUID().toString());
        assertFalse(f.exists());
        ArgumentCheck.checkExistsDirectory(f, "TEST");
    }
    
    @Test(expectedExceptions = ArgumentCheck.class)
    public void testDirectoryFailureFile() {
        addDescription("Test that the directory test failes on a file");
        File f = new File("README.md");
        assertTrue(f.isFile());
        ArgumentCheck.checkExistsDirectory(f, "TEST");
    }
    
    @Test
    public void testDirectorySuccess() {
        addDescription("Test the directory test on a directory");
        tempDir.mkdir();
        assertTrue(tempDir.isDirectory());
        ArgumentCheck.checkExistsDirectory(tempDir, "TEST");
    }
    
    @Test(expectedExceptions = ArgumentCheck.class)
    public void testFileFailureMissing() {
        addDescription("Test that the file test failes on a non-existing file/directory");
        File f = new File(UUID.randomUUID().toString());
        assertFalse(f.exists());
        ArgumentCheck.checkExistsNormalFile(f, "TEST");
    }
    
    @Test(expectedExceptions = ArgumentCheck.class)
    public void testFileFailureDirectory() {
        addDescription("Test that the file test failes on a directory");
        tempDir.mkdir();
        assertTrue(tempDir.isDirectory());
        ArgumentCheck.checkExistsNormalFile(tempDir, "TEST");
    }
    
    @Test
    public void testFileSuccess() {
        addDescription("Test the file test on a file");
        File f = new File("README.md");
        assertTrue(f.isFile());
        ArgumentCheck.checkExistsNormalFile(f, "TEST");
    }
}
