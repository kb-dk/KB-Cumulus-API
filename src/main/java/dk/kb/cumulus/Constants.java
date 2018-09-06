/* $Id: Constants.java 2096 2013-08-23 15:29:23Z svc $
 * $Date: 2013-08-23 17:29:23 +0200 (fr, 23 aug. 2013) $
 * $Revision: 2096 $
 * $Author: svc $
 */

package dk.kb.cumulus;

/**
 * Constants taken from the old KB-DOMS.
 * 
 * 
 * This interface is not an interface.
 * It sums up some constants needed by the workflows.
 * This is an ugly hack! Many of these values could/should be defined 
 * as a map in one of the beans-files that are used to configure the 
 * KBDOMS installation. The default bean files are located in the directory 
 * conf/spring.
 */
public interface Constants {
    /**
     * The name of the Cumulus fields, which are used.
     */
    interface FieldNames {
        /** The name of the preservation check field.*/
        String BEVARING_CHECK = "Bevaring_check_type";
        /** The name of the preservation check status field.*/
        String BEVARING_CHECK_STATUS = "Bevaring_check_status";

        /** The name of the preservation check field.*/
        String BEVARING_IMPORTATION = "Bevaring_Import";
        /** The name of the preservation check status field.*/
        String BEVARING_IMPORTATION_STATUS = "Bevaring_Import_status";
        
        /** The name of the preservation date field.*/
        String BEVARINGS_DATO = "Bevarings dato";
        /** The name of the field for the metadata standards used.*/
        String BEVARINGS_METADATA = "Bevarings metadata";
        
        /** Name of the field containing the preservation status of the record. */
        String PRESERVATION_STATUS = "Preservation_status";
        
        /** The name of the field containing the MD5 checksum of the file
         * which has been stored in long term preservation.
         */
        String ARCHIVE_MD5 = "ARCHIVE_MD5";
 
        /** The package id for the resource. To be used instead of ARCHIVE_FILENAME*/
        String RESOURCE_PACKAGE_ID = "RESOURCE PACKAGE ID"; 
        /** The package id for the metadata (METS) */
        String METADATA_PACKAGE_ID = "METADATA PACKAGE ID"; 
        
        /** the ID of the collection which the object should belong to. */
        String COLLECTION_ID = "KB Samling";
        
        /** The GUID for the metadata stored as part of the record data in LTP. */
        String METADATA_GUID = "METADATA GUID";
        
        /** String constant for the field. */ 
        String RELATED_SUB_ASSETS = "Related Sub Assets";
        
        /** Name of the field containing the related object identifier value for the intellectual entity.*/
        String RELATED_OBJECT_IDENTIFIER_VALUE_INTELLECTUEL_ENTITY = "relatedObjectIdentifierValue_intellectualEntity";
        
        /** The UUID of the intellectual entity of the representation.*/
        String REPRESENTATION_INTELLECTUAL_ENTITY_UUID = "Representation intellectual guid";
        
        /** The UUID of the metadata for the representation.*/
        String REPRESENTATION_METADATA_GUID = "Representation metadata guid";
    
        /** Name of the field for the Global Unique IDentifier. */
        String GUID = "GUID";

        /** Name of the field for user-readable QA error messages. */
        String QA_ERROR = "QA_error";
        
        /** Name of the field containing the checksum of the original master. */
        String CHECKSUM_ORIGINAL_MASTER = "CHECKSUM_ORIGINAL_MASTER";
 
        /** The name of the record (usually the original name of the file). */
        String RECORD_NAME = "Record Name";
        
        /** The name of the field that determines if a record in prod is ready for publication. */
        String REGISTRATIONSTATE = "Registrationstate";

        /** String constant for the field. */ 
        String RELATED_MASTER_ASSETS = "Related Master Assets";
        
        /** The name of the field for the Catalog name. */
        String CATALOG_NAME = "Catalog Name";
        
        /** String constant for the field. */ 
        String FILE_DATA_SIZE = "File Data Size";
        
        /** The name of the field for Asset Reference. */
        String ASSET_REFERENCE = "Asset Reference";
        
        /** The file format identifier (mimetype). */
        String FILE_FORMAT_IDENTIFIER = "File Format Identifier";
        
        /** The name of the file format field. */
        String FORMAT_NAME = "formatName";
        
        /** The name of the field for the Item Modification Date*/
        String ITEM_MODIFICATION_DATE = "Item Modification Date";
        
        /** The name of the field for ID (the internal Cumulus identifier).*/
        String ID = "ID";
    }
    
    /**
     * The name of the Cumulus fields, which are no longer used directly.
     */
    interface DeprecatedFieldNames {
        /** The file format. */
        String FILE_FORMAT = "File Format";
        
        /** The name of the title field. */
        String TITEL = "Titel";

        /** The name of the serietitel field. */
        String SERIETITEL = "Serietitel";

        /** The name of the note field. */
        String NOTE = "Note";

        /** The name of the seriebeskrivelse field. */
        String SERIEBESKRIVELSE = "Seriebeskrivelse";

        /** The name of the color profile field. */
        String ICC_PROFILE = "ICC Profile Name";

        /** Name of field for initiated commands. */
        String LAST_CMD_INITIATED = "LastCmdInitiated";

        /** Name of field for successfully invoked commands. */
        String LAST_CMD_SUCCEDED = "LastCmdSucceeded";

        /** Name of the field that holds Cumulus's own (integer) ID. */
        String ASSET_IDENTIFIER = "Asset Identifier";

        /** Name of the field containing the datetime of publication. */
        String PUBLICATION_DATE = "PublicationDate";

        /** Name of the field for system-readable QA error status. */
        String QA_STATUS = "QA_status";

        /** Name of the field containing the last workflow that was applied. */
        String PUBLISHED_BY_VERSION = "PublishedByVersion";
        
        /** String constant for the field. */ 
        String BITS_PER_CHANNEL = "Bits Per Channel";

        /**
         * Name of the field indicating whether a record has been/is being
         * processed.
         */
        String MASTERTRANSFERSTATUS = "Mastertransferstatus";

        /**
         * Name of the boolean field determining if record should be processed
         * by Undo flow.
         */
        String UNDO = "Undo";
        
        /**
         * Name of the field containing the name of the production catalog the
         * record originally came from.
         */
        String PRODUCTION_CATALOG = "Production_Catalog";

        /**
         * Name of the field that records if the image asset (TIFF file)
         * is compressed, and if so how.
         */
        String COMPRESSION = "Compression";

        /** Name of the field containing the name of the Publication Catalog. */
        String PUBLICATION_CATALOG = "Publication_catalog";
        
        /** Not used. ??? 
         * FIXME: Check this with TLR. */
        String PUBLISHED = "Published";
        
        /** Used for what. ??? 
         * FIXME: Check this with TLR. */
        String LABEL = "Label";
        
        /** Used for what. ??? 
         * FIXME: Check this with TLR. */
        String QA_SW_VERSION = "QA_sw_version";
        
        /** Name of the field containing the checksum of the display copy. */
        String CHECKSUM_DISPLAY_COPY = "CHECKSUM_DISPLAY_COPY";
        
        /** String constant for the field. */ 
        String CATEGORIES = "Categories";
        
        /** String constant for the field. */ 
        String CONTACT_INFO_COUNTRY = "Contact Info Country";
        
        /** String constant for the field. */ 
        String ORIGINAL_BASE_MATERIAL = "Original Base Material";
        
        /** String constant for the field. */ 
        String APERTURE = "Aperture";
        
        /** String constant for the field. */ 
        String HORIZONTAL_PIXELS = "Horizontal Pixels";
        
        /** String constant for the field. */ 
        String VIDEO_CODEC = "Video Codec";
        
        /** String constant for the field. */ 
        String SCENE_TYPE = "Scene Type";
        
        /** String constant for the field. */ 
        String VIDEO_QUALITY = "Video Quality";
        
        /** String constant for the field. */ 
        String AUDIO_FORMAT = "Audio Format";
        
        /** String constant for the field. */ 
        String ALTITUDE = "Altitude";
        
        /** String constant for the field. */ 
        String CONTRAST = "Contrast";
        
        /** String constant for the field. */ 
        String AUDIO_CODEC_INFORMATION = "Audio Codec Information";
        
        /** String constant for the field. */ 
        String INTELLECTUAL_GENRE = "Intellectual Genre";
        
        /** String constant for the field. */ 
        String SUPPLEMENTAL_CATEGORIES = "Supplemental Categories";
        
        /** String constant for the field. */ 
        String SERVICE_ID = "Service Id";
        
        /** String constant for the field. */ 
        String EXPORT_FORMAT = "Export_format";
        
        /** String constant for the field. */ 
        String TIME_SENT = "Time Sent";
        
        /** String constant for the field. */ 
        String METER_MODE = "Meter Mode";
        
        /** String constant for the field. */ 
        String MAX_APERTURE_AS_STRING = "Max Aperture (String)";
        
        /** String constant for the field. */ 
        String ISO_SPEED = "ISO Speed";
        
        /** String constant for the field. */ 
        String OCR_TEXT = "OCR_text";
        
        /** String constant for the field. */ 
        String FLASH_MODE = "Flash Mode";
        
        /** String constant for the field. */ 
        String EXPOSURE_MODE = "Exposure Mode";
        
        /** String constant for the field. */ 
        String CONTACT_INFO_CITY = "Contact Info City";
        
        /** String constant for the field. */ 
        String APERTURE_AS_STRING = "Aperture (String)";
        
        /** String constant for the field. */ 
        String SERIAL_NUMBER = "Serial Number";
        
        /** String constant for the field. */ 
        String USER_COMMENT = "User Comment";
        
        /** String constant for the field. */ 
        String AUDIO_CODEC_DESCRIPTION = "Audio Codec Description";
        
        /** String constant for the field. */ 
        String FIRMWARE_VERSION = "Firmware Version";
        
        /** String constant for the field. */ 
        String EMNEORD = "Emneord";
        
        /** String constant for the field. */ 
        String MANUFACTURER = "Manufacturer";
        
        /** String constant for the field. */ 
        String IMAGE_ORIENTATION = "Image Orientation";
        
        /** String constant for the field. */ 
        String ORIGINAL_SIZE = "Original Size";
        
        /** String constant for the field. */ 
        String VIDEO_IS_VBR = "Video is VBR";
        
        /** String constant for the field. */ 
        String CONTACT_INFO_WEB_URLS = "Contact Info Web URL(s)";
        
        /** String constant for the field. */ 
        String PUBLISHEDBYVERSION = "PublishedByVersion";
        
        /** String constant for the field. */ 
        String CONTACT_INFO_POSTAL_CODE = "Contact Info Postal Code";
        
        /** String constant for the field. */ 
        String EXPOSURE_BIAS = "Exposure Bias";
        
        /** String constant for the field. */ 
        String ORIGINAL_COLOR = "Original Color";
        
        /** String constant for the field. */ 
        String EXPOSURE_PROGRAM = "Exposure Program";
        
        /** String constant for the field. */ 
        String PUBLICATIONDATE = "PublicationDate";
        
        /** String constant for the field. */ 
        String PUBLISHER = "Publisher";
        
        /** String constant for the field. */ 
        String SIGNATURE = "Signature";
        
        /** String constant for the field. */ 
        String DIGITAL_ZOOM_RATIO = "Digital Zoom Ratio";
        
        /** String constant for the field. */ 
        String LASTCMDINITIATED = "LastCmdInitiated";
        
        /** String constant for the field. */ 
        String PERSON = "Person";
        
        /** String constant for the field. */ 
        String SENSING_METHOD = "Sensing Method";
        
        /** String constant for the field. */ 
        String LIGHT_SOURCE = "Light Source";
        
        /** String constant for the field. */ 
        String VIDEO_CODEC_INFORMATION = "Video Codec Information";
        
        /** String constant for the field. */ 
        String EXPORTED = "Exported";
        
        /** String constant for the field. */ 
        String LONGITUDE = "Longitude";
        
        /** String constant for the field. */ 
        String LATITUDE = "Latitude";
        
        /** String constant for the field. */ 
        String THUMBNAIL_ROTATION = "Thumbnail Rotation";
        
        /** String constant for the field. */ 
        String SHARPNESS = "Sharpness";
        
        /** String constant for the field. */ 
        String CONTENT_MD5 = "Content MD5";
        
        /** String constant for the field. */ 
        String CONTACT_INFO_STATE_OR_PROVINCE = "Contact Info State/Province";
        
        /** String constant for the field. */ 
        String ORIGINAL_MATERIAL_SUBTYPE = "Original Material Subtype";
        
        /** String constant for the field. */ 
        String ORIGINAL_PRESERVATIONSTATE = "Original Preservationstate";
        
        /** String constant for the field. */ 
        String SCENE_CAPTURE_TYPE = "Scene Capture Type";
        
        /** String constant for the field. */ 
        String AUDIO_IS_VBR = "Audio is VBR";
        
        /** String constant for the field. */ 
        String REGISTRATIONDATE = "Registrationdate";
        
        /** String constant for the field. */ 
        String VIDEO_CODEC_DESCRIPTION = "Video Codec Description";
        
        /** String constant for the field. */ 
        String REFERENCE_NUMBER = "Reference Number";
        
        /** String constant for the field. */ 
        String CUSTOM_RENDERED = "Custom Rendered";
        
        /** String constant for the field. */ 
        String REFERENCE_DATE = "Reference Date";
        
        /** String constant for the field. */ 
        String LANGUAGE = "Language";
        
        /** String constant for the field. */ 
        String REFERENCE_SERVICE = "Reference Service";
        
        /** String constant for the field. */ 
        String ANNOTATION = "Annotation";
        
        /** String constant for the field. */ 
        String SERVER_NAME = "Server Name";
        
        /** String constant for the field. */ 
        String VOLUME_NAME = "Volume Name";
        
        /** String constant for the field. */ 
        String ASSET_NAME = "Asset Name";
        
        /** String constant for the field. */ 
        String CONTACT_INFO_PHONES = "Contact Info Phone(s)";
        
        /** String constant for the field. */ 
        String DURATION = "Duration";
        
        /** String constant for the field. */ 
        String RELEASE_TIME = "Release Time";
        
        /** String constant for the field. */ 
        String INFORMATION = "Information";
        
        /** String constant for the field. */ 
        String RELEASE_DATE = "Release Date";
        
        /** String constant for the field. */ 
        String EDIT_STATUS = "Edit Status";
        
        /** String constant for the field. */ 
        String F_NUMBER_AS_STRING = "F Number (String)";
        
        /** String constant for the field. */ 
        String MOVIE_INFORMATION = "Movie Information";
        
        /** String constant for the field. */ 
        String OBJECT_CYCLE = "Object Cycle";
        
        /** String constant for the field. */ 
        String COUNTRY_CODE = "Country Code";
        
        /** String constant for the field. */ 
        String LENS_INFO = "Lens Info";
        
        /** String constant for the field. */ 
        String PRODUCER = "Producer";
        
        /** String constant for the field. */ 
        String AUDIO_MODE = "Audio Mode";
        
        /** String constant for the field. */ 
        String FIXTURE_ID = "Fixture Id";
        
        /** String constant for the field. */ 
        String DIRECTOR = "Director";
        
        /** String constant for the field. */ 
        String CERTIFICATE = "Certificate";
        
        /** String constant for the field. */ 
        String ORIGINATING_PROGRAM = "Originating Program";
        
        /** String constant for the field. */ 
        String COLOR_CHANNELS = "Color Channels";
        
        /** String constant for the field. */ 
        String KEYWORDS = "Keywords";
        
        /** String constant for the field. */ 
        String CAMERA_ID = "Camera ID";
        
        /** String constant for the field. */ 
        String ASSET_SIZE_COMPRESSED = "Asset Size Compressed";
        
        /** String constant for the field. */ 
        String OWNER = "Owner";
        
        /** String constant for the field. */ 
        String VIDEO_MODE = "Video Mode";
        
        /** String constant for the field. */ 
        String FOLDER_NAME = "Folder Name";
        
        /** String constant for the field. */ 
        String COLOR_SPACE = "Color Space";
        
        /** String constant for the field. */ 
        String DATE_TIME_DIGITIZED = "Date Time Digitized";
        
        /** String constant for the field. */ 
        String RATING = "Rating";
        
        /** String constant for the field. */ 
        String PUBLISHING_YEAR = "Publishing Year";
        
        /** String constant for the field. */ 
        String FOCAL_LENGTH_IN_MM = "Focal Length [mm]";
        
        /** String constant for the field. */ 
        String SUBJECT_DISTANCE = "Subject Distance";
        
        /** String constant for the field. */ 
        String COPYRIGHT_APPLIED = "Copyright Applied";
        
        /** String constant for the field. */ 
        String F_NUMBER = "F Number";
        
        /** String constant for the field. */ 
        String EXPOSURE_TIME = "Exposure Time";
        
        /** String constant for the field. */ 
        String SHUTTER_TIME_S = "Shutter Time [s]";
        
        /** String constant for the field. */ 
        String CAPTURED_DATE = "Captured Date";
        
        /** String constant for the field. */ 
        String ASSET_COMPRESSION_RATE_IN_PERCENT = "Asset Compression Rate [%]";
        
        /** String constant for the field. */ 
        String IMAGE_URL = "Image URL";
        
        /** String constant for the field. */ 
        String SCANNER_MODEL = "Scanner Model";
        
        /** String constant for the field. */ 
        String SCANNER_MANUFACTURER = "Scanner Manufacturer";
        
        /** String constant for the field. */ 
        String CAMERA_MODEL = "Camera Model";
        
        /** String constant for the field. */ 
        String CAMERA_MANUFACTURER = "Camera Manufacturer";
        
        /** String constant for the field. */ 
        String VIDEO_FORMAT = "Video Format";
        
        /** String constant for the field. */ 
        String EXPOSURE_TIME_AS_STRING = "Exposure Time (String)";
        
        /** String constant for the field. */ 
        String FILEFORMAT_VALID = "Fileformat_valid";
        
        /** String constant for the field. */ 
        String DATE_SENT = "Date Sent";
        
        /** String constant for the field. */ 
        String IMAGE_SOURCE = "Image Source";
        
        /** String constant for the field. */ 
        String ORG_TRANS_REF = "Org. Trans. Ref.";
        
        /** String constant for the field. */ 
        String COUNTRY = "Country";
        
        /** String constant for the field. */ 
        String PROVINCE_OR_STATE = "Province/State";
        
        /** String constant for the field. */ 
        String CITY = "City";
        
        /** String constant for the field. */ 
        String LASTCMDSUCCEEDED = "LastCmdSucceeded";
        
        /** String constant for the field. */ 
        String OBJECT_NAME = "Object Name";
        
        /** String constant for the field. */ 
        String SOURCE = "Source";
        
        /** String constant for the field. */ 
        String CREDITS = "Credits";
        
        /** String constant for the field. */ 
        String BYLINE_TITLE = "Byline Title";
        
        /** String constant for the field. */ 
        String BYLINE = "Byline";
        
        /** String constant for the field. */ 
        String MAX_APERTURE = "Max Aperture";
        
        /** String constant for the field. */ 
        String BIT_RATE_AUDIO_KBITS = "Bit Rate Audio [kbit/s]";
        
        /** String constant for the field. */ 
        String URGENCY = "Urgency";
        
        /** String constant for the field. */ 
        String FLASHPIX_VERSION = "FlashPix Version";
        
        /** String constant for the field. */ 
        String BIT_RATE_VIDEO_KBITS = "Bit Rate Video [kbit/s]";
        
        /** String constant for the field. */ 
        String SPECIAL_INSTRUCTIONS = "Special Instructions";
        
        /** String constant for the field. */ 
        String CAPTION_WRITER = "Caption Writer";
        
        /** String constant for the field. */ 
        String MPEG_VERSION_AUDIO = "MPEG Version Audio";
        
        /** String constant for the field. */ 
        String MPEG_VERSION_VIDEO = "MPEG Version Video";
        
        /** String constant for the field. */ 
        String ID = "ID";
        
        /** String constant for the field. */ 
        String LOCATION = "Location";
        
        /** String constant for the field. */ 
        String ICC_PROFILE_NAME = "ICC Profile Name";
        
        /** String constant for the field. */ 
        String COPYRIGHT_NOTICE = "Copyright Notice";
        
        /** String constant for the field. */ 
        String CONTACT_INFO_EMAILS = "Contact Info Email(s)";
        
        /** String constant for the field. */ 
        String AUTHOR = "Author";
        
        /** String constant for the field. */ 
        String FORMAT_VERSION = "Format Version";
        
        /** String constant for the field. */ 
        String COLOR_MODEL = "Color Model";
        
        /** String constant for the field. */ 
        String THUMBNAIL_STANDARD_DEVIATION = "Thumbnail Standard Deviation";
        
        /** String constant for the field. */ 
        String THUMBNAIL_MEAN_VALUE = "Thumbnail Mean Value";
        
        /** String constant for the field. */ 
        String ASSET_MODIFICATION_STATE_IDENTIFIER = 
            "Asset Modification State Identifier";
        
        /** String constant for the field. */ 
        String BIT_RATE_KBITS = "Bit Rate [kbit/s]";
        
        /** String constant for the field. */ 
        String USAGE_TERMS = "Usage Terms";
        
        /** String constant for the field. */ 
        String GAIN_CONTROL = "Gain Control";
        
        /** String constant for the field. */ 
        String WHITE_BALANCE_MODE = "White Balance Mode";
        
        /** String constant for the field. */ 
        String DATE_TIME_ORIGINAL = "Date Time Original";
        
        /** String constant for the field. */ 
        String PROGRAM_VERSION = "Program Version";
        
        /** String constant for the field. */ 
        String MUX_RATE_KBITS = "Mux Rate [kbit/s]";
        
        /** String constant for the field. */ 
        String MPEG_LAYER = "MPEG Layer";
        
        /** String constant for the field. */ 
        String SUBJECT_DISTANCE_RANGE = "Subject Distance Range";
        
        /** String constant for the field. */ 
        String XMP_METADATA = "XMP Metadata";
        
        /** String constant for the field. */ 
        String MPEG_VERSION = "MPEG Version";
        
        /** String constant for the field. */ 
        String ORIGINAL_MATERIAL_TYPE = "Original Material Type";
        
        /** String constant for the field. */ 
        String VERTICAL_PIXELS = "Vertical Pixels";
        
        /** String constant for the field. */ 
        String AUDIO_CODEC = "Audio Codec";
        
        /** String constant for the field. */ 
        String STREGKODE = "Stregkode";
        
        /** String constant for the field. */ 
        String PAGETYPE = "Pagetype";
        
        /** String constant for the field. */ 
        String FOCAL_LENGTH_35MM_FILM_MM = "Focal Length 35mm Film [mm]";
        
        /** String constant for the field. */ 
        String UNIX_FILE_IDENTIFIER = "Unix File Identifier";
        
        /** String constant for the field. */ 
        String IMAGE_HEIGHT = "Image Height";
        
        /** String constant for the field. */ 
        String IMAGE_WIDTH = "Image Width";
        
        /** String constant for the field. */ 
        String VERTICAL_RESOLUTION = "Vertical Resolution";
        
        /** String constant for the field. */ 
        String ROLE = "Role";
        
        /** String constant for the field. */ 
        String HORIZONTAL_RESOLUTION = "Horizontal Resolution";
        
        /** String constant for the field. */ 
        String TIME = "Time";
        
        /** String constant for the field. */ 
        String CONTACT_INFO_ADDRESS = "Contact Info Address";
        
        /** String constant for the field. */ 
        String NOTES = "Notes";
        
        /** String constant for the field. */ 
        String THUMBNAIL = "Thumbnail";
        
        /** String constant for the field. */ 
        String SOFTWARE = "Software";
        
        /** String constant for the field. */ 
        String SATURATION = "Saturation";
        
        /** String constant for the field. */ 
        String CATALOGING_USER = "Cataloging User";
        
        /** String constant for the field. */ 
        String STATUS = "Status";
        
        /** String constant for the field. */ 
        String ASSET_MODIFICATION_DATE = "Asset Modification Date";
        
        /** String constant for the field. */ 
        String ASSET_CREATION_DATE = "Asset Creation Date";
        
        /** String constant for the field. */ 
        String DONT_DELETE_RECORD = "Don't Delete Record";
        
        /** String constant for the field. */ 
        String COPYRIGHT = "Copyright";
        
        /** String constant for the field. */ 
        String DEPARTMENT = "Department";
        
        /** String constant for the field. */ 
        String IP_LICENS = "IP_licens";
        
        /** String constant for the field. */ 
        String RECORD_MODIFICATION_DATE = "Record Modification Date";
        
        /** String constant for the field. */ 
        String IPTC_SCENE = "IPTC Scene";
        
        /** String constant for the field. */ 
        String IPTC_CATEGORY = "IPTC Category";
        
        /** String constant for the field. */ 
        String IPTC_SUBJECT_CODE = "IPTC Subject Code";
        
        /** String constant for the field. */ 
        String IPTC_TIME_CREATED = "IPTC Time Created";
        
        /** String constant for the field. */ 
        String IPTC_DATE_CREATED = "IPTC Date Created";
        
        /** String constant for the field. */ 
        String RECORD_CREATION_DATE = "Record Creation Date";
        
        /** String constant for the field. */ 
        String EVENT = "Event";
        
        /** String constant for the field. */ 
        String EXIF_VERSION = "EXIF Version";
    }
    
    /**
     * This interface exposes various constant String values used in field
     * values.
     */
    interface FieldValues {
        
        /** The "OK" state for the preservation validation field.*/
        String PRESERVATION_VALIDATION_OK = "OK";
        /** The "Simpelt check" state for the preservation validation field.*/
        String PRESERVATION_VALIDATION_SIMPLE_CHECK = "Simpelt check";
        /** The "Fuldt check" state for the preservation validation field.*/
        String PRESERVATION_VALIDATION_FULL_CHECK = "Fuldt check";
        /** The "Integritets fejl" state for the preservation validation field.*/
        String PRESERVATION_VALIDATION_FAILURE = "Integritets fejl";

        /** The "Ingen import" state for the preservation import field.*/
        String PRESERVATION_IMPORT_NONE = "Ingen import";
        /** The "Start import af filen" state for the preservation import field.*/
        String PRESERVATION_IMPORT_START = "Start import af filen";
        /** The "Import fejlet" state for the preservation import field.*/
        String PRESERVATION_IMPORT_FAILURE = "Import fejlet";

        /** The "OK" value for MASTERTRANSFERSTATUS. */
        String TRANSFER_STATUS_OK = "OK";
        /** The "FAILED" value for MASTERTRANSFERSTATUS. */
        String TRANSFER_STATUS_FAILED = "FAILED";
        /** The "IN PROGRESS" value for MASTERTRANSFERSTATUS. */
        String TRANSFER_STATUS_STARTED = "STARTED";

        /** 
         * The "Record may be damaged by unhandled error" value
         * for QA_STATUS.
         */
        String QA_STATUS_FAILED_RECORD_UNKNOWN_STATE = 
            "FAILED, POSSIBLY DAMAGED";

        /**
         * The "Record is not damaged, but needs attention to
         * fix QA problems" value for QA_STATUS.
         */
        String QA_STATUS_FAILED_RECORD_KNOWN_STATE = 
            "FAILED, NOT DAMAGED, PLEASE FIX QA ERROR";

        /** The value for REGISTRATIONSTATE that means that the record is not ready for publication yet. */
        String REGISTRATIONSTATE_NOT_READY_FOR_TRANSFER = "Under registrering";
        /** The value for REGISTRATIONSTATE that means that the record is not ready for publication yet.*/
        String REGISTRATIONSTATE_FINISHED = "Færdigregistreret";

        /** The value for PRESERVATION_STATUS that indicates that the record is ready to be reviews for archival. 
         * Though at this point the record is likely missing the presevation metadata.
         * This is the step before SEND_TO_ARCHIVE.*/
        String PRESERVATIONSTATE_READY_FOR_ARCHIVAL = "Klar til langtidsbevaring";
        /** The value for PRESERVATION_STATUS that indicates that the record is ready to be packaged and archived. */
        String PRESERVATIONSTATE_SEND_TO_ARCHIVE = "Send til langtidsbevaring";
        /** The value for PRESERVATION_STATUS that indicates that the record has been archived. */
        String PRESERVATIONSTATE_ARCHIVAL_COMPLETED = "Langtidsbevaret";
        /** The value for PRESERVATION_STATUS that indicates that the record is not to archived. */
        String PRESERVATIONSTATE_NO_ARCHIVAL = "Skal ikke langtidsbevares";
        /** The value for PRESERVATION_STATUS that indicates that the long-term preservation of this the 
         * record failed. */
        String PRESERVATIONSTATE_ARCHIVAL_FAILED = "Langtidsbevaring fejlet";
        /** The value for PRESERVATION_STATUS that indicates that the  */
        String PRESERVATIONSTATE_READY_FOR_UPDATE = "Send kun metadata til bevaring";

        /** The value for COMPRESSION that indicates that the image asset (TIFF file) is not compressed. */
        String UNCOMPRESSED = "Uncompressed";
        
        
        /** First unwanted GUID value. */
        String FIRST_UNWANTED_GUID_VALUE = "Uid:dk:kb:doms:2006-09/99999";
        
        /** Second unwanted GUID value. */
        String SECOND_UNWANTED_GUID_VALUE = "Uid:dk:kb:doms:0000-00/000000";
        
        /** 8 = One of the allowed values for the 
         * {@link PreservationFieldNames#BITS_PER_CHANNEL} field.
         */
        String EIGHT_BITS = "8";
        /** 16 = One of the allowed values for the 
         * {@link PreservationFieldNames#BITS_PER_CHANNEL} field.
         */
        String SIXTEEN_BITS = "16";
        
        /** TIFF Image is the only excepted value for the 
         * {@link FieldNames.FILE_FORMAT} 
         * field in the PublishTiffMasterWorkflow.
         */
        String TIFF_IMAGE = "TIFF Image";
    }

    /**
     * This interface exposes various String constants that are used in the
     * workflow for constructing field values etc.
     */
    interface StringConstants {

        /**
         * The prefix to add to a Cumulus-generated GUID. This is 
         * a change from previous iterations, as we used to use the
         * asset identifier on the assumption that it was a GUID - it
         * was not!
         * 
         * To be able to separate the old IDs from new ones, the prefix 
         * has therefore been changed.
         * 
         * This is VERSION 2 of the prefix.
         * 
         * Previous values:
         * 
         *   Version 1: "Uid:dk:kb:doms:2006-09/"
         */
        String GUID_PREFIX = "Uid:dk:kb:doms:2007-01/";
        
        /** A UNIX style newline (no CR). */
        String NEWLINE = "\n";

        /** An empty String. */
        String EMPTY_STRING = "";

        /** The canonical Java value for boolean value false as a String. */
        String FALSE_AS_STRING = Boolean.toString(false);

        /** The canonical Java value for boolean value true as a String. */
        String TRUE_AS_STRING = Boolean.toString(true);

    }
}
