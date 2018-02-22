package dk.kb.cumulus;

import java.util.EnumSet;
import java.util.Locale;
import java.util.UUID;

import org.jaccept.structure.ExtendedTestCase;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.canto.cumulus.constants.CombineMode;
import com.canto.cumulus.constants.FindFlag;

public class CumulusQueryTest extends ExtendedTestCase {

    @Test
    public void testConstructor() {
        String query = UUID.randomUUID().toString();
        EnumSet<FindFlag> findFlags = EnumSet.of(
                FindFlag.FIND_MISSING_STRING_LIST_VALUES_REPLACE, 
                FindFlag.SEARCH_CATEGORIES_USE_USER_SETTINGS);    
        CombineMode combineMode = CombineMode.FIND_NARROW;
        CumulusQuery cq = new CumulusQuery(query, findFlags, combineMode);
        
        Assert.assertEquals(cq.getQuery(), query);
        Assert.assertEquals(cq.getFindFlags(), findFlags);
        Assert.assertEquals(cq.getCombineMode(), combineMode);
        Assert.assertNull(cq.getLocale());
        
        cq.setLocale(Locale.ROOT);
        Assert.assertNotNull(cq.getLocale());
        Assert.assertEquals(cq.getLocale(), Locale.ROOT);
    }
    
    @Test
    public void testGetQueryForSpecificUUID() {
        String catalogName = UUID.randomUUID().toString();  
        String uuid = UUID.randomUUID().toString();
        CumulusQuery cq = CumulusQuery.getQueryForSpecificGUID(catalogName, uuid);
        
        Assert.assertTrue(cq.getQuery().contains(catalogName));        
        Assert.assertTrue(cq.getQuery().contains(uuid));        
    }
    
    @Test
    public void testGetQueryForSpecificRecordName() {
        String catalogName = UUID.randomUUID().toString();  
        String recordName = UUID.randomUUID().toString();
        CumulusQuery cq = CumulusQuery.getQueryForSpecificRecordName(catalogName, recordName);
        
        Assert.assertTrue(cq.getQuery().contains(catalogName));        
        Assert.assertTrue(cq.getQuery().contains(recordName));        
    }
    
    @Test
    public void testGetQueryForAllInCatalog() {
        String catalogName = UUID.randomUUID().toString();  
        CumulusQuery cq = CumulusQuery.getQueryForAllInCatalog(catalogName);
        
        Assert.assertTrue(cq.getQuery().contains(catalogName));        
    }
}
