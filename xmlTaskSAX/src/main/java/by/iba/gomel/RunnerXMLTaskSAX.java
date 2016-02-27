package by.iba.gomel;

import java.io.FileNotFoundException;

import by.iba.gomel.menu.Menu;

/**
 * This class contains method main that launches this application.
 */
public class RunnerXMLTaskSAX {

    /**
     * Default private constructor.
     */
    private RunnerXMLTaskSAX() {

    }

    /**
     *
     * @param args
     *            input parameters (not used).
     * @throws FileNotFoundException
     */
    public static void main(final String[] args) throws FileNotFoundException {
        Menu.showMenu();
    }

}
