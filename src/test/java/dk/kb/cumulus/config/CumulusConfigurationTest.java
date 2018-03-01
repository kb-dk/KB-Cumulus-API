package dk.kb.cumulus.config;

import org.jaccept.structure.ExtendedTestCase;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.UUID;

public class CumulusConfigurationTest extends ExtendedTestCase {

    @Test
    public void testConfiguration() {
        addDescription("Test the configuration");

        boolean writeAccess = false;
        String serverUrl = UUID.randomUUID().toString();
        String userName = UUID.randomUUID().toString();
        String userPassword = UUID.randomUUID().toString();
        String catalog = UUID.randomUUID().toString();
        Collection<String> catalogs = Arrays.asList(catalog);

        CumulusConfiguration conf = new CumulusConfiguration(writeAccess, serverUrl, userName, userPassword, catalogs);

        Assert.assertEquals(conf.getWriteAccess(), writeAccess);
        Assert.assertEquals(conf.getServerUrl(), serverUrl);
        Assert.assertEquals(conf.getUserName(), userName);
        Assert.assertEquals(conf.getUserPassword(), userPassword);
        Assert.assertEquals(conf.getCatalogs().size(), catalogs.size());
        Assert.assertTrue(conf.getCatalogs().contains(catalog));
    }

}

