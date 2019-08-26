package dk.kb.cumulus;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;

/**
 * Class with main method for extracting the raw metadata record for a single record.
 */
public class CumulusExtractor {
    /** The URL for the Cumulus server.*/
    static String serverUrl = null;
    /** The username for login to the Cumulus server.*/
    static String username = null;
    /** The password for login to the Cumulus server.*/
    static String userPassword = null;
    /** The name of the catalog, with the record.*/
    static String catalog = null;
    /** The name of the file for the Cumulus record to have its metadata extracted.*/
    static String filename = null;
    /** The name/path of the metadata output file.*/
    static String outputFilename = null;

    /**
     * Main method.
     * @param args The arguments described in the 'fail' method.
     */
    public static void main(String ... args) {
        if(args.length < 3) {
            fail();
        }

        for(String arg : args) {
            if(arg.startsWith("-s")) {
                serverUrl = arg.replaceFirst("-s", "");
                continue;
            } else if(arg.startsWith("-u")) {
                username = arg.replaceFirst("-u", "");
                continue;
            } else if(arg.startsWith("-p")) {
                userPassword = arg.replaceFirst("-p", "");
                continue;
            } else if(arg.startsWith("-c")) {
                catalog = arg.replaceFirst("-c", "");
                continue;
            } else if(arg.startsWith("-f")) {
                filename = arg.replaceFirst("-f", "");
                continue;
            } else if(arg.startsWith("-o")) {
                outputFilename = arg.replaceFirst("-o", "");
                continue;
            } else if(arg.startsWith("-h")) {
                fail();
            } else {
                System.err.println("Does not handle argument: " + arg);
                System.err.println("Tries to continue;");
            }
        }

        checkArgs();

        // Put the metadata output file locally, if nothing else is specified.
        if(outputFilename == null) {
            outputFilename = filename + ".raw.xml";
        }

        try (CumulusServer server = new CumulusServer(serverUrl, username, userPassword, Arrays.asList(catalog),
            false)) {
            CumulusRecord record = server.findCumulusRecordByName(catalog, filename);
            if(record == null) {
                throw new IllegalStateException("Cannot find record '" + filename + "'");
            }
            extractMetadata(record);
        } catch (IOException e) {
            System.err.println("Failure to the extract metadata: ");
            e.printStackTrace();
        }
    }

    /**
     * Extracts the metadata of the Cumulus record, and put it into the output file.
     * @param record The record to have its metadata extracted.
     * @throws IOException If it fails to create the output file, extract the metadata,
     * transform the metadata into XML, or similar.
     */
    protected static void extractMetadata(CumulusRecord record) throws IOException{
        File outputFile = new File(outputFilename);
        if(outputFile.exists()) {
            deprecateFile(outputFile);
        }
        try (OutputStream out = new FileOutputStream(outputFile)) {
            record.writeFieldMetadata(out);
        } catch(ParserConfigurationException | TransformerException e) {
            throw new IOException("Failed to parse or transform the Cumulus record metadata.", e);
        }
    }

    /**
     * Checks the input arguments.
     * Will fail, if they are not set.
     */
    protected static void checkArgs() {
        boolean fail = false;
        if(serverUrl == null) {
            fail = true;
            System.err.println("Missing argument: '-s'");
        }
        if(username == null) {
            fail = true;
            System.err.println("Missing argument: '-u'");
        }
        if(userPassword == null) {
            fail = true;
            System.err.println("Missing argument: '-p'");
        }
        if(catalog == null) {
            fail = true;
            System.err.println("Missing argument: '-c'");
        }
        if(filename == null) {
            fail = true;
            System.err.println("Missing argument: '-f'");
        }

        if(fail) {
            fail();
        }
    }

    /**
     * Fail!!!
     * Will print out the usage of this class, the arguments it takes, and then exit.
     */
    protected static void fail() {
        System.err.println("The CumulusExtractor can extract the raw-metadata file for a single Cumulus record.");
        System.err.println("Arguments: ");
        System.err.println(" -s \t[Required] The URL for the Cumulus server");
        System.err.println(" -u \t[Required] The username for login to the Cumulus server");
        System.err.println(" -p \t[Required] The password for login to the Cumulus server");
        System.err.println(" -c \t[Required] The Catalog on the Cumulus server with the given record");
        System.err.println(" -f \t[Required] The filename for the record to find");
        System.err.println(" -o \t[Optional] The name/path of the output file (default is the name of the file "
                + "+ \'.raw.xml\')");
        System.err.println(" -h \tTo get this help");

        System.exit(-1);
    }

    /**
     * Deprecates a file, by moving it to 'filepath' + '.old'.
     * This will happen recursively, if a similar file has already been deprecated.
     * @param f The file to deprecate.
     */
    protected static void deprecateFile(File f) {
        File oldFile = new File(f.getAbsolutePath() + ".old");
        if(oldFile.exists()) {
            deprecateFile(oldFile);
        }
        boolean success = f.renameTo(oldFile);
        if(!success) {
            throw new IllegalStateException("Could not deprecate the file '" + f.getAbsolutePath() + "'");
        }
    }
}
