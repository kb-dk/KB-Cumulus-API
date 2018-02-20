# KB-Cumulus-API
A common Cumulus API for the different application on The Royal Danish Library in Copenhagen.

It deals with accessing a Cumulus server, finding and extracting Cumulus records, extracting and writing metadata for these records.


Prerequisite
--------------------------
This package requires the installation of Cumulus 10 libraries for Linux, which is asserted to be located at '/usr/local/Cumulus_Java_SDK/CumulusJC.jar'.
Please contact your Cumulus provider to obtain the installation package.


Usage
--------------------------
The following show a simple way of using this 
 
```java

    String serverUrl = $SERVER_URL;
    String username = $USERNAME;
    String password = $PASSWORD;
    List<String> catalogs = $CUMULUS_CATALOGS;
    boolean writeAccess = true;
    try (CumulusServer server = new CumulusServer(serverUrl, username, password, catalogs, writeAccess) {
        String myCatalog = catalogs.get(0);
        CumulusQuery query = CumulusQuery.getQueryForAllInCatalog(myCatalog);
        CumulusRecordCollection recordCollection = server.getItems(myCatalog, query);
    
        for(CumulusRecord record : recordCollection) {
            String name = record.getStringValue("Record Name");
            File f = record.getFile();
            record.setStringValueInField("status", "We found record named '" + name + "' with file at location: " + 
                    f.getAbsolutePath());
        }
    }
```


License
--------------------------
We need to figure out precisely which open-source license to use; GNU, Apache, etc...

Basically; you are free to use the code, but not for proprietary applications.
Also, please tell us if you use it.