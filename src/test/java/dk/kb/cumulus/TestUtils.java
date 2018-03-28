package dk.kb.cumulus;

import java.io.File;
import java.io.FileInputStream;
import java.util.Arrays;
import java.util.LinkedHashMap;

import org.testng.SkipException;
import org.yaml.snakeyaml.Yaml;

import dk.kb.cumulus.config.CumulusConfiguration;

public class TestUtils {

    public static CumulusConfiguration getTestConfiguration() throws Exception {
        File f = new File("cumulus-password.yml");
        if(!f.exists()) {
            throw new SkipException("Coult not find a YAML at '" + f.getAbsolutePath() + "'");
        }
        Object o = new Yaml().load(new FileInputStream(f));
        if (!(o instanceof LinkedHashMap)) {
            throw new SkipException("Could not read YAML file: " + f.getAbsolutePath());
        }
        LinkedHashMap<String, Object> settings = (LinkedHashMap<String, Object>) o;

        String serverUrl = (String) settings.get("server_url");
        String userName = (String) settings.get("login");
        String userPassword = (String) settings.get("password");
        String catalog = (String) settings.get("catalog");;
        
        return new CumulusConfiguration(true, serverUrl, userName, userPassword, Arrays.asList(catalog));
    }
}
