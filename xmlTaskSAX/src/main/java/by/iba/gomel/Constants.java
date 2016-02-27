package by.iba.gomel;

/**
 * This class contains all constants in this application.
 */
public class Constants {

    public static final int    ZERO                         = 0;
    public static final int    ONE                          = 1;
    public static final int    TWO                          = 2;
    public static final int    THREE                        = 3;
    public static final int    FOUR                         = 4;
    public static final int    FIVE                         = 5;
    public static final int    SIX                          = 6;
    public static final int    SEVEN                        = 7;
    public static final String PHRASE_ITEM                  = "Item";
    public static final String PHRASE_ID                    = "Key";
    public static final String PHRASE_MARK_AUTO             = "Mark auto";
    public static final String PHRASE_MODEL_AUTO            = "Model auto";
    public static final String PHRASE_COST                  = "Cost";
    public static final String FORMAT_SPARE                 = "%4d%10s%25s%25s%20d%n";
    public static final String FORMAT_HEADER                = "%2s%10s%25s%25s%20s%n";
    public static final String DIVIDING_LINE                = "------------------------------------------------------------------------------------\n";
    public static final String SPACE                        = " ";
    public static final String PLUS                         = "+";
    public static final String LINE_BREAK                   = "\n";
    public static final String ELEMENT_SPARES               = "spares";
    public static final String ELEMENT_SPARE                = "spare";
    public static final String ELEMENT_MARK_AUTO            = "markAuto";
    public static final String ELEMENT_MODEL_AUTO           = "modelAuto";
    public static final String ELEMENT_COST                 = "cost";
    public static final String ATTRIBUTE_KEY                = "key";
    public static final String PARSER_CONFIG_EXCEPTION      = "ParserConfigurationException";
    public static final String TRANSFORMER_CONFIG_EXCEPTION = "TransformerConfigurationException";
    public static final String TRANSFORMER_EXCEPTION        = "TransformerException";
    public static final String SAX_EXCEPTION                = "SAXException";
    public static final String SAX_PARSE_EXCEPTION          = "SAXParseException";
    public static final String IO_EXCEPTION                 = "IOException";
    public static final String SHOW_RECORDS                 = "Show all records";
    public static final String ADD_RECORD                   = "Add record";
    public static final String EDIT_RECORD                  = "Edit record";
    public static final String DELETE_RECORD                = "Delete record";
    public static final String SEARCH_RECORD                = "Search record";
    public static final String INCLUDE_FILE                 = "Include another xml file";
    public static final String EXIT                         = "Exit";
    public static final String ENTER_NUMBER                 = "Please, enter number: ";
    public static final String ITEMS_MENU                   = "Menu:\n1) Show all records\n2) Add record"
            + "\n3) Edit record\n4) Delete record\n5) Search record\n6) Include another xml file\n7) Exit\nPlease, enter number: ";
    public static final String PHRASE_YES                   = "yes";
    public static final String PHRASE_NOT_FOUND_RECORD      = "Record wasn't found\n";
    public static final String PHRASE_RECORD_ADDED          = "Record was added!\n";
    public static final String PHRASE_RECORD_CHANGED        = "Record was successfully changed!\n";
    public static final String PHRASE_RECORD_DELETED        = "Record was successfully deleted!\n";
    public static final String PHRASE_SEARCH_INFO           = "Enter name or address or telephone: ";
    public static final String PHRASE_ENTER_PATH            = "Enter path to xml file: ";
    public static final String PHRASE_CONFIRMATION          = "Are you sure? (1 - yes, 2-no): ";
    public static final String PATH_TO_XML_FILE             = "src/main/resources/Spares.xml";
    public static final String PHRASE_NOT_INCLUDE           = "Another file was icnluded with errors, please, include another file\n";

    /**
     * Default private constructor.
     */
    private Constants() {

    }
}
