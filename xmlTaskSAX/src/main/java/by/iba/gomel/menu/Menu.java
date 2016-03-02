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

    private static final Logger LOGGER        = LoggerFactory.getLogger(Menu.class);
    private final Scanner       in            = new Scanner(System.in,
            Charset.defaultCharset().name());
    private String              pathToXMLFile = "src/main/resources/Spares.xml";
    private String              pathToXSDFile = "src/main/resources/SparesXSD.xsd";

    /**
     *
     * @param pathToXMLFile
     *            set path to xml file.
     */
    public void setPathToXMLFile(final String pathToXMLFile) {
        this.pathToXMLFile = pathToXMLFile;
    }

    /**
     *
     * @param pathToXSDFile
     *            set path to xsd file.
     */
    public void setPathToXSDFile(final String pathToXSDFile) {
        this.pathToXSDFile = pathToXSDFile;
    }

    /**
     * This class shows menu on the console.
     */
    public void showMenu() {
        final SparesSAXBuilder saxBuilder = new SparesSAXBuilder(this.pathToXMLFile,
                this.pathToXSDFile, this.in);
        int choice;
        while (true) {
            Menu.LOGGER.info(Constants.ITEMS_MENU);
            choice = Integer.parseInt(this.in.nextLine());
            switch (choice) {
                case Constants.ONE:
                    saxBuilder.showAllRecords();
                    break;
                case Constants.TWO:
                    final String[] addValues = this.getPersonValuesFromConsole();
                    saxBuilder.addRecord(addValues[Constants.ZERO], addValues[Constants.ONE],
                            addValues[Constants.TWO], Integer.parseInt(addValues[Constants.THREE]));
                    break;
                case Constants.THREE:
                    final String[] changeValues = this.getPersonValuesFromConsole();
                    saxBuilder.changeRecord(changeValues[Constants.ZERO],
                            changeValues[Constants.ONE], changeValues[Constants.TWO],
                            Integer.parseInt(changeValues[Constants.THREE]));
                    break;
                case Constants.FOUR:
                    Menu.LOGGER.info(Constants.PHRASE_KEY);
                    final String key = this.in.nextLine();
                    saxBuilder.deleteRecord(key);
                    break;
                case Constants.FIVE:
                    Menu.LOGGER.info(Constants.PHRASE_SEARCH_INFO);
                    final String searchInfo = this.in.nextLine();
                    saxBuilder.searchRecord(searchInfo);
                    break;
                case Constants.SIX:
                    Menu.LOGGER.info(Constants.PHRASE_ENTER_PATH);
                    final String pathToAnotherXMLFile = this.in.nextLine();
                    saxBuilder.includeAnotherFile(pathToAnotherXMLFile);
                    break;
                case Constants.SEVEN:
                    this.in.close();
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
    private String[] getPersonValuesFromConsole() {
        final String[] values = new String[Constants.FOUR];
        Menu.LOGGER.info(Constants.PHRASE_KEY);
        values[Constants.ZERO] = this.in.nextLine();
        Menu.LOGGER.info(Constants.PHRASE_MARK_AUTO);
        values[Constants.ONE] = this.in.nextLine();
        Menu.LOGGER.info(Constants.PHRASE_MODEL_AUTO);
        values[Constants.TWO] = this.in.nextLine();
        Menu.LOGGER.info(Constants.PHRASE_COST);
        values[Constants.THREE] = this.in.nextLine();
        return values;
    }
}
