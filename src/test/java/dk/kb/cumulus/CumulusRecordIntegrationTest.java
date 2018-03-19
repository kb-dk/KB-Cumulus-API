package dk.kb.cumulus;

import java.io.File;
import java.io.FileInputStream;
import java.util.Arrays;
import java.util.LinkedHashMap;

import org.jaccept.structure.ExtendedTestCase;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.yaml.snakeyaml.Yaml;

public class CumulusRecordIntegrationTest extends ExtendedTestCase {


    String testServerUrl;
    String testUserName;
    String testUserPassword;
    String testCatalog;

    @BeforeClass
    public void setup() throws Exception {
        File f = new File(System.getenv("HOME") + "/cumulus-password.yml");
        if(!f.exists()) {
            throw new SkipException("Coult not find a YAML at '" + f.getAbsolutePath() + "'");
        }
        Object o = new Yaml().load(new FileInputStream(f));
        if (!(o instanceof LinkedHashMap)) {
            throw new SkipException("Could not read YAML file: " + f.getAbsolutePath());
        }
        LinkedHashMap<String, Object> settings = (LinkedHashMap<String, Object>) o;

        testServerUrl = (String) settings.get("server_url");
        testUserName = (String) settings.get("login");
        testUserPassword = (String) settings.get("password");
        testCatalog = (String) settings.get("catalog");;
    }

    @Test(enabled = false)
    public void testCreatingNewRelations() throws Exception {
        addDescription("");

        try (CumulusServer server = new CumulusServer(testServerUrl, testUserName, testUserPassword, Arrays.asList(testCatalog), true)) {
            String name1 = "501981.tif";
            CumulusRecord record1 = server.findCumulusRecordByName(testCatalog, name1);
            Assert.assertNotNull(record1);

            String name2 = "501981x.tif";
            CumulusRecord record2 = server.findCumulusRecordByName(testCatalog, name2);
            Assert.assertNotNull(record2);

//            record1.addMasterAsset(record2);
            record2.addSubAsset(record1);
        }
    }
}
