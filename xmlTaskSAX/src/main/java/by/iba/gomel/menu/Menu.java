package by.iba.gomel.menu;

import java.nio.charset.Charset;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import by.iba.gomel.Constants;
import by.iba.gomel.sax.SparesSAXBuilder;

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
        final SparesSAXBuilder saxBuilder = new SparesSAXBuilder(Constants.PATH_TO_XML_FILE,
                Constants.PATH_TO_XSD_FILE);
        int choice;
        while (true) {
            Menu.LOGGER.info(Constants.ITEMS_MENU);
            choice = Integer.parseInt(Menu.in.nextLine());
            switch (choice) {
                case Constants.ONE:
                    saxBuilder.showAllSpares();
                    break;
                case Constants.TWO:
                    final String[] addValues = Menu.getPersonValuesFromConsole();
                    saxBuilder.addRecord(addValues[Constants.ZERO], addValues[Constants.ONE],
                            addValues[Constants.TWO], Integer.parseInt(addValues[Constants.THREE]));
                    break;
                case Constants.THREE:
                    final String[] changeValues = Menu.getPersonValuesFromConsole();
                    saxBuilder.updateRecord(changeValues[Constants.ZERO],
                            changeValues[Constants.ONE], changeValues[Constants.TWO],
                            Integer.parseInt(changeValues[Constants.THREE]));
                    break;
                case Constants.FOUR:
                    Menu.LOGGER.info(Constants.PHRASE_KEY);
                    final String key = Menu.in.nextLine();
                    saxBuilder.deleteRecord(key);
                    break;
                case Constants.FIVE:
                    Menu.LOGGER.info(Constants.PHRASE_SEARCH_INFO);
                    final String searchInfo = Menu.in.nextLine();
                    saxBuilder.searchRecord(searchInfo);
                    break;
                case Constants.SIX:
                    Menu.LOGGER.info(Constants.PHRASE_ENTER_PATH);
                    final String pathToAnotherXMLFile = Menu.in.nextLine();
                    saxBuilder.includeAnotherFile(pathToAnotherXMLFile);
                    break;
                case Constants.SEVEN:
                    Menu.in.close();
                    return;
                default:
                    break;
            }
        }
    }

    /**
     *
     * @return array of all values for adding new record.
     */
    private static String[] getPersonValuesFromConsole() {
        final String[] values = new String[Constants.FOUR];
        Menu.LOGGER.info(Constants.PHRASE_KEY);
        values[Constants.ZERO] = Menu.in.nextLine();
        Menu.LOGGER.info(Constants.PHRASE_MARK_AUTO);
        values[Constants.ONE] = Menu.in.nextLine();
        Menu.LOGGER.info(Constants.PHRASE_MODEL_AUTO);
        values[Constants.TWO] = Menu.in.nextLine();
        Menu.LOGGER.info(Constants.PHRASE_COST);
        values[Constants.THREE] = Menu.in.nextLine();
        return values;
    }
}
