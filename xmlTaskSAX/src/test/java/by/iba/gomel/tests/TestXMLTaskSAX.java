package by.iba.gomel.tests;

import java.io.ByteArrayInputStream;
import java.util.Scanner;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.SystemOutRule;

import by.iba.gomel.Constants;
import by.iba.gomel.RunnerXMLTaskSAX;
import by.iba.gomel.menu.Menu;
import by.iba.gomel.sax.SparesSAXBuilder;
import by.iba.gomel.spare.Spare;

public class TestXMLTaskSAX {

    private static final String PATH_TO_TEST_XML_FILE = "src/test/resources/testSpares.xml";
    private static final String PATH_TO_TEST_XSD_FILE = "src/test/resources/testSparesXSD.xml";

    @Rule
    public final SystemOutRule  RULE                  = new SystemOutRule().enableLog();

    @Test
    public void includeAnotherNotValidFile() {
        final Scanner testIn = new Scanner(System.in);
        final SparesSAXBuilder testSAXBuilder = new SparesSAXBuilder(
                TestXMLTaskSAX.PATH_TO_TEST_XML_FILE, Constants.PATH_TO_XSD_FILE, testIn);
        testSAXBuilder.includeAnotherFile("src/test/resources/anotherNotValidTestSpares.xml");
        final String expectedException = "org.xml.sax.SAXParseException";
        Assert.assertTrue(
                "Mistake is in class SparesSAXBuilder method includeAnotherFile. Wrong validation",
                this.RULE.getLog().contains(expectedException));
        testIn.close();
    }

    @Test
    public void includeAnotherValidFile() {
        final Scanner testIn = new Scanner(System.in);
        final SparesSAXBuilder testSAXBuilder = new SparesSAXBuilder(
                TestXMLTaskSAX.PATH_TO_TEST_XML_FILE, Constants.PATH_TO_XSD_FILE, testIn);
        testSAXBuilder.includeAnotherFile("src/test/resources/anotherTestSpares.xml");
        testSAXBuilder.showAllRecords();
        final boolean[] expectedResults = {true, true};
        final boolean[] actualResults = {this.RULE.getLog().contains("VAZ"),
                this.RULE.getLog().contains("VW")};
        Assert.assertArrayEquals("Mistake is in class SparesSAXBuilder method includeAnotherFile",
                expectedResults, actualResults);
        testIn.close();
    }

    @Test
    public void testAddRecordConsole() {
        final ByteArrayInputStream in = new ByteArrayInputStream(
                "2\ns3\nAudi\nA6\n255000\n1\n4\ns3\n1\n7".getBytes());
        System.setIn(in);
        final Menu testMenu = new Menu();
        testMenu.setPathToXMLFile(TestXMLTaskSAX.PATH_TO_TEST_XML_FILE);
        testMenu.setPathToXSDFile(TestXMLTaskSAX.PATH_TO_TEST_XSD_FILE);
        testMenu.showMenu();
        final boolean[] expectedResults = {true, true, true};
        final boolean[] actualResults = {this.RULE.getLog().contains("Volvo"),
                this.RULE.getLog().contains("Seat"), this.RULE.getLog().contains("Audi")};
        Assert.assertArrayEquals("Mistake is in class SparesSAXBuilder method addRecord",
                expectedResults, actualResults);
    }

    @Test
    public void testAddRecordObject() {
        final ByteArrayInputStream in = new ByteArrayInputStream("1".getBytes());
        System.setIn(in);
        final Scanner testIn = new Scanner(System.in);
        final SparesSAXBuilder testSAXBuilder = new SparesSAXBuilder(
                TestXMLTaskSAX.PATH_TO_TEST_XML_FILE, TestXMLTaskSAX.PATH_TO_TEST_XSD_FILE, testIn);
        final Spare testSpare = new Spare();
        testSpare.setId("s3");
        testSpare.setMarkAuto("Audi");
        testSpare.setModelAuto("A6");
        testSpare.setCost(1560000);
        testSAXBuilder.addRecord(testSpare);
        testSAXBuilder.showAllRecords();
        testSAXBuilder.deleteRecord("s3");
        final boolean[] expectedResults = {true, true, true};
        final boolean[] actualResults = {this.RULE.getLog().contains("Volvo"),
                this.RULE.getLog().contains("Seat"), this.RULE.getLog().contains("Audi")};
        Assert.assertArrayEquals("Mistake is in class SparesSAXBuilder method addRecord(Person)",
                expectedResults, actualResults);
        testIn.close();
    }

    @Test
    public void testDeleteRecord() {
        final ByteArrayInputStream in = new ByteArrayInputStream("1".getBytes());
        System.setIn(in);
        final Scanner testIn = new Scanner(System.in);
        final SparesSAXBuilder testSAXBuilder = new SparesSAXBuilder(
                TestXMLTaskSAX.PATH_TO_TEST_XML_FILE, TestXMLTaskSAX.PATH_TO_TEST_XSD_FILE, testIn);
        final Spare testSpare = new Spare();
        testSpare.setId("s3");
        testSpare.setMarkAuto("Audi");
        testSpare.setModelAuto("A6");
        testSpare.setCost(1560000);
        testSAXBuilder.addRecord(testSpare);
        testSAXBuilder.deleteRecord("s3");
        testSAXBuilder.showAllRecords();
        final boolean[] expectedResults = {true, true, false};
        final boolean[] actualResults = {this.RULE.getLog().contains("Volvo"),
                this.RULE.getLog().contains("Seat"), this.RULE.getLog().contains("Audi")};
        Assert.assertArrayEquals("Mistake is in class SparesSAXBuilder method deleteRecord()",
                expectedResults, actualResults);
        testIn.close();
    }

    @Test
    public void testEditRecord() {
        final ByteArrayInputStream in = new ByteArrayInputStream("1".getBytes());
        System.setIn(in);
        final Scanner testIn = new Scanner(System.in);
        final SparesSAXBuilder testSAXBuilder = new SparesSAXBuilder(
                TestXMLTaskSAX.PATH_TO_TEST_XML_FILE, TestXMLTaskSAX.PATH_TO_TEST_XSD_FILE, testIn);
        final Spare testSpare = new Spare();
        testSpare.setId("s3");
        testSpare.setMarkAuto("Audi");
        testSpare.setModelAuto("A6");
        testSpare.setCost(1560000);
        testSAXBuilder.addRecord(testSpare);
        testSAXBuilder.changeRecord("s3", "Datcha", "Logan", 120000);
        testSAXBuilder.showAllRecords();
        testSAXBuilder.deleteRecord("s3");
        final boolean[] expectedResults = {true, true, true};
        final boolean[] actualResults = {this.RULE.getLog().contains("Volvo"),
                this.RULE.getLog().contains("Seat"), this.RULE.getLog().contains("Datcha")};
        Assert.assertArrayEquals("Mistake is in class SparesSAXBuilder method changeRecord()",
                expectedResults, actualResults);
        testIn.close();
    }

    @Test
    public void testMain() {
        final ByteArrayInputStream in = new ByteArrayInputStream("7".getBytes());
        System.setIn(in);
        RunnerXMLTaskSAX.main(new String[] {});
        Assert.assertEquals("Mistakes are in class Menu, method showMenu",
                "Menu:\n1) Show all records\n2) Add record\n3) Edit record\n4) Delete record\n5) Search record\n6) Include another xml file\n7) Exit\nPlease, enter number: ",
                this.RULE.getLog());
    }

    @Test
    public void testSearchRecord() {
        final ByteArrayInputStream in = new ByteArrayInputStream("1".getBytes());
        System.setIn(in);
        final Scanner testIn = new Scanner(System.in);
        final SparesSAXBuilder testSAXBuilder = new SparesSAXBuilder(
                TestXMLTaskSAX.PATH_TO_TEST_XML_FILE, TestXMLTaskSAX.PATH_TO_TEST_XSD_FILE, testIn);
        final Spare testSpare = new Spare();
        testSpare.setId("s3");
        testSpare.setMarkAuto("Audi");
        testSpare.setModelAuto("A6");
        testSpare.setCost(1560000);
        testSAXBuilder.addRecord(testSpare);
        testSAXBuilder.searchRecord("Audi");
        testSAXBuilder.deleteRecord("s3");
        final boolean[] expectedResults = {false, false, true};
        final boolean[] actualResults = {this.RULE.getLog().contains("Volvo"),
                this.RULE.getLog().contains("Seat"), this.RULE.getLog().contains("Audi")};
        Assert.assertArrayEquals("Mistake is in class SparesSAXBuilder method searchRecord()",
                expectedResults, actualResults);
        testIn.close();
    }

    @Test
    public void testShowAllRecords() {
        final ByteArrayInputStream in = new ByteArrayInputStream("1\n7".getBytes());
        System.setIn(in);
        final Menu testMenu = new Menu();
        testMenu.setPathToXMLFile(TestXMLTaskSAX.PATH_TO_TEST_XML_FILE);
        testMenu.setPathToXSDFile(TestXMLTaskSAX.PATH_TO_TEST_XSD_FILE);
        testMenu.showMenu();
        final boolean[] expectedResults = {true, true};
        final boolean[] actualResults = {this.RULE.getLog().contains("Volvo"),
                this.RULE.getLog().contains("Seat")};
        Assert.assertArrayEquals("Mistake is in class PersonsDOMBuilder method showAllRecords",
                expectedResults, actualResults);
    }
}
