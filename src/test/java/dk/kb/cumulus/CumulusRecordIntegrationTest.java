package dk.kb.cumulus;

import org.jaccept.structure.ExtendedTestCase;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import dk.kb.cumulus.config.CumulusConfiguration;

public class CumulusRecordIntegrationTest extends ExtendedTestCase {

    CumulusConfiguration conf;
    
    @BeforeClass
    public void setup() throws Exception {
        conf = TestUtils.getTestConfiguration();
    }

    @Test
    public void testCreatingSubAsset() throws Exception {
        addDescription("Test creating the relation through settings a sub asset");

        String testCatalog = conf.getCatalogs().get(0);
        
        try (CumulusServer server = new CumulusServer(conf)) {
            String name1 = "KS000313_05.tif";// "501981.tif";
            CumulusRecord record1 = server.findCumulusRecordByName(testCatalog, name1);
            Assert.assertNotNull(record1);

            String name2 =  "KS000313.tif"; //"501981x.tif";
            CumulusRecord record2 = server.findCumulusRecordByName(testCatalog, name2);
            Assert.assertNotNull(record2);

            record2.addSubAsset(record1);
            
            Assert.assertTrue(record2.isMasterAsset());
            Assert.assertFalse(record2.isSubAsset());
            Assert.assertTrue(record1.isSubAsset());
        }
    }
    
    @Test
    public void testCreatingMasterAsset() throws Exception {
        addDescription("Test creating the relation through settings a master asset");

        String testCatalog = conf.getCatalogs().get(0);
        
        try (CumulusServer server = new CumulusServer(conf)) {
            String name1 = "KS000313_05.tif"; //"501981.tif";
            CumulusRecord record1 = server.findCumulusRecordByName(testCatalog, name1);
            Assert.assertNotNull(record1);

            String name2 = "KS000313.tif"; //"501981x.tif";
            CumulusRecord record2 = server.findCumulusRecordByName(testCatalog, name2);
            Assert.assertNotNull(record2);

            record1.addMasterAsset(record2);
            
            Assert.assertTrue(record2.isMasterAsset());
            Assert.assertFalse(record2.isSubAsset());
            Assert.assertTrue(record1.isSubAsset());
        }
    }
}
