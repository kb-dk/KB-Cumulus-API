package dk.kb.cumulus;

import org.jaccept.structure.ExtendedTestCase;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ConstantTest extends ExtendedTestCase {

    @Test
    public void testInstantiatedElements() {
        Assert.assertEquals(Constants.StringConstants.FALSE_AS_STRING, "false");
        Assert.assertEquals(Constants.StringConstants.TRUE_AS_STRING, "true");
    }
}
