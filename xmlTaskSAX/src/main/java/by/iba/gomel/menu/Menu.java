package by.iba.gomel.menu;

import java.nio.charset.Charset;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import by.iba.gomel.Constants;
import by.iba.gomel.sax.SparesSAXHandler;

/**
 * This class contains methods to working with menu.
 */
public class Menu {

    private static final Logger LOGGER = LoggerFactory.getLogger(Menu.class);
    public static final Scanner in     = new Scanner(System.in, Charset.defaultCharset().name());

    /**
     * Default private constructor.
     */
    private Menu() {

    }

    /**
     * This class shows menu on the console.
     */
    public static void showMenu() {
        // final PersonsDOMBuilder builder = new PersonsDOMBuilder(Constants.PATH_TO_XML_FILE);
        final SparesSAXHandler handler = new SparesSAXHandler(Constants.PATH_TO_XML_FILE);
        int choice;
        while (true) {
            Menu.LOGGER.info(Constants.ITEMS_MENU);
            choice = Integer.parseInt(Menu.in.nextLine());
            switch (choice) {
                case Constants.ONE:
                    handler.showAllSpares();
                    // builder.showAllRecords();
                    break;
                case Constants.TWO:
                    // final String[] addValues = Menu.getPersonValuesFromConsole();
                    // builder.addRecord(addValues[Constants.ZERO], addValues[Constants.ONE],
                    // addValues[Constants.TWO], addValues[Constants.THREE]);
                    break;
                case Constants.THREE:
                    // final String[] changeValues = Menu.getPersonValuesFromConsole();
                    // builder.changeRecord(changeValues[Constants.ZERO],
                    // changeValues[Constants.ONE],
                    // changeValues[Constants.TWO], changeValues[Constants.THREE]);
                    break;
                case Constants.FOUR:
                    Menu.LOGGER.info(Constants.PHRASE_ID);
                    final String id = Menu.in.nextLine();
                    // builder.deleteRecord(id);
                    break;
                case Constants.FIVE:
                    Menu.LOGGER.info(Constants.PHRASE_SEARCH_INFO);
                    final String searchInfo = Menu.in.nextLine();
                    // builder.searchRecord(searchInfo);
                    break;
                case Constants.SIX:
                    Menu.LOGGER.info(Constants.PHRASE_ENTER_PATH);
                    final String pathToXMLFile = Menu.in.nextLine();
                    // builder.includeAnotherFile(pathToXMLFile);
                    break;
                case Constants.SEVEN:
                    Menu.in.close();
                    return;
                default:
                    break;
            }
        }
    }

    // /**
    // *
    // * @return array of all values for adding new record..
    // */
    // private static String[] getPersonValuesFromConsole() {
    // final String[] values = new String[Constants.FOUR];
    // Menu.LOGGER.info(Constants.PHRASE_ID);
    // values[Constants.ZERO] = Menu.in.nextLine();
    // Menu.LOGGER.info(Constants.PHRASE_FULL_NAME);
    // values[Constants.ONE] = Menu.in.nextLine();
    // Menu.LOGGER.info(Constants.PHRASE_ADDRESS);
    // values[Constants.TWO] = Menu.in.nextLine();
    // Menu.LOGGER.info(Constants.PHRASE_TELEPHONE);
    // values[Constants.THREE] = Menu.in.nextLine();
    // return values;
    // }
}
