package dk.kb.cumulus;

import java.util.Iterator;
import java.util.UUID;

import org.jaccept.structure.ExtendedTestCase;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.canto.cumulus.AllCategoriesItemCollection;
import com.canto.cumulus.Catalog;
import com.canto.cumulus.CategoryItem;
import com.canto.cumulus.Item;

import dk.kb.cumulus.config.CumulusConfiguration;

public class CumulusServerIntegrationTest extends ExtendedTestCase {

    CumulusConfiguration conf;
    String uuid = "70786480-0e45-11e7-919b-00505688346e"; // "df919440-717f-11e0-82d7-002185371280";
    String name = "501981.tif";
    
    @BeforeClass
    public void setup() throws Exception {
        conf = TestUtils.getTestConfiguration();
    }
    
    @Test
    public void testInstantiationAndCategories() throws Exception {
        addDescription("Test the instantiation and the categories");
        
        String catalogName = conf.getCatalogs().get(0);
        
        try (CumulusServer server = new CumulusServer(conf)) {
            Assert.assertEquals(conf.getCatalogs(), server.getCatalogNames());
            Catalog catalog = server.getCatalog(catalogName);
            AllCategoriesItemCollection c = catalog.getAllCategoriesItemCollection();

            Item expectedCategory;
            Iterator<Item> categories = c.iterator();
            int count = 0;
            int maxCount = 10;
            while((expectedCategory = categories.next()) != null && count < maxCount) {
                count++;
                CategoryItem actualCategory = server.getCategory(catalogName, expectedCategory.getID());
                Assert.assertEquals(actualCategory.getID(), expectedCategory.getID());
                Assert.assertEquals(actualCategory.getDisplayString(), expectedCategory.getDisplayString());
            }
        }
    }
    
    @Test(expectedExceptions = IllegalStateException.class)
    public void testConnectionFailureWithConfiguration() throws Exception {
        addDescription("Failing the connection");
        
        CumulusConfiguration testConf = new CumulusConfiguration(true, conf.getServerUrl(), UUID.randomUUID().toString(), UUID.randomUUID().toString(), conf.getCatalogs());
        
        try (CumulusServer server = new CumulusServer(testConf)) {}
    }
    
    @Test(expectedExceptions = IllegalStateException.class)
    public void testConnectionFailureWithVariables() throws Exception {
        addDescription("Failing the connection");
        
        try (CumulusServer server = new CumulusServer(conf.getServerUrl(), UUID.randomUUID().toString(), UUID.randomUUID().toString(), conf.getCatalogs(), true)) {}
    }

    @Test
    public void testFindingRecordThroughUUID() throws Exception {
        addDescription("Test the finding of a record through the UUID");
        
        
        String catalogName = conf.getCatalogs().get(0);

        try (CumulusServer server = new CumulusServer(conf)) {
            CumulusRecord uuidRecord = server.findCumulusRecord(catalogName, uuid);
            
            Assert.assertEquals(uuidRecord.getUUID(), uuid);
            Assert.assertEquals(uuidRecord.getFieldValue(Constants.FieldNames.RECORD_NAME), name);
        }
    }
    
    @Test
    public void testFindingRecordThroughName() throws Exception {
        addDescription("Test the finding of a record through the Record name");
        
        String catalogName = conf.getCatalogs().get(0);

        try (CumulusServer server = new CumulusServer(conf.getServerUrl(), conf.getUserName(), conf.getUserPassword(), conf.getCatalogs(), conf.getWriteAccess())) {
            CumulusRecord uuidRecord = server.findCumulusRecordByName(catalogName, name);
            
            Assert.assertEquals(uuidRecord.getUUID(), uuid);
            Assert.assertEquals(uuidRecord.getFieldValue(Constants.FieldNames.RECORD_NAME), name);
        }
    }
}
