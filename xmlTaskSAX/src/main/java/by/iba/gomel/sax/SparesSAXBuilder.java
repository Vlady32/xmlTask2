package by.iba.gomel.sax;

import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.util.Scanner;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import by.iba.gomel.Constants;
import by.iba.gomel.process.OutputSpareProcess;
import by.iba.gomel.process.SearchSpareProcess;
import by.iba.gomel.sax.filters.AddFilter;
import by.iba.gomel.sax.filters.DeleteFilter;
import by.iba.gomel.sax.filters.UpdateFilter;
import by.iba.gomel.spare.Spare;

/**
 * This class builds SparesSAXHandler and realizes methods for working with menu.
 */
public class SparesSAXBuilder {

    private static final Logger    LOGGER = LoggerFactory.getLogger(SparesSAXBuilder.class);
    private Scanner                in     = new Scanner(System.in, Charset.defaultCharset().name());
    private final SparesSAXHandler saxHandler;
    private XMLReader              reader;
    private String                 pathToXMLFile;
    private final String           pathToXSDFile;

    /**
     *
     * @param pathToXMLFile
     *            path to xml file.
     */
    public SparesSAXBuilder(final String pathToXMLFile, final String pathToXSDFile,
            final Scanner in) {
        this.in = in;
        this.saxHandler = new SparesSAXHandler();
        this.pathToXMLFile = pathToXMLFile;
        this.pathToXSDFile = pathToXSDFile;
        try {
            this.reader = XMLReaderFactory.createXMLReader();
            this.reader.setContentHandler(this.saxHandler);
            this.reader.setErrorHandler(new SparesErrorHandler());
        } catch (final SAXException e) {
            SparesSAXBuilder.LOGGER.error(Constants.SAX_EXCEPTION, e);
        }

    }

    /**
     *
     * @param spare
     *            add object in xml file.
     */
    public void addRecord(final Spare spare) {
        final XMLReader xr = new AddFilter(this.reader, spare.getId(), spare.getMarkAuto(),
                spare.getModelAuto(), spare.getCost());
        this.writeToFile(xr, this.pathToXMLFile, true);
    }

    /**
     *
     * @param key
     *            key for updating.
     * @param markAuto
     *            new mark auto.
     * @param modelAuto
     *            new model auto.
     * @param cost
     *            new cost.
     */
    public void addRecord(final String key, final String markAuto, final String modelAuto,
            final int cost) {
        final XMLReader xr = new AddFilter(this.reader, key, markAuto, modelAuto, cost);
        this.writeToFile(xr, this.pathToXMLFile, true);
    }

    /**
     *
     * @param key
     *            key for updating.
     * @param markAuto
     *            new mark auto.
     * @param modelAuto
     *            new model auto.
     * @param cost
     *            new cost.
     */
    public void changeRecord(final String key, final String markAuto, final String modelAuto,
            final int cost) {
        final XMLReader xr = new UpdateFilter(this.reader, key, markAuto, modelAuto, cost);
        this.writeToFile(xr, this.pathToXMLFile, true);
    }

    /**
     *
     * @param key
     *            key for deleting.
     */
    public void deleteRecord(final String key) {
        final XMLReader xr = new DeleteFilter(this.reader, key, this.in);
        this.writeToFile(xr, this.pathToXMLFile, false);
    }

    /**
     *
     * @param pathToXMLFile
     *            path to include another xml file.
     */
    public void includeAnotherFile(final String pathToXMLFile) {
        if (ValidatorSAXXSD.validate(pathToXMLFile, this.pathToXSDFile)) {
            this.pathToXMLFile = pathToXMLFile;
        } else {
            SparesSAXBuilder.LOGGER.info(Constants.ERROR_XSD);
        }
    }

    /**
     *
     * @param searchInfo
     *            searching information in xml file.
     */
    public void searchRecord(final String searchInfo) {
        this.saxHandler.setProcess(new SearchSpareProcess(searchInfo.toLowerCase()));
        final String header = String.format(Constants.FORMAT_HEADER, Constants.PHRASE_ITEM,
                Constants.PHRASE_KEY, Constants.PHRASE_MARK_AUTO, Constants.PHRASE_MODEL_AUTO,
                Constants.PHRASE_COST);
        SparesSAXBuilder.LOGGER.info(header + Constants.DIVIDING_LINE);
        try {
            this.reader.parse(this.pathToXMLFile);
        } catch (final IOException e) {
            SparesSAXBuilder.LOGGER.error(Constants.IO_EXCEPTION, e);
        } catch (final SAXException e) {
            SparesSAXBuilder.LOGGER.error(Constants.SAX_EXCEPTION, e);
        }
    }

    /**
     * To print out all spares to console.
     */
    public void showAllRecords() {
        this.saxHandler.setProcess(new OutputSpareProcess());
        final String header = String.format(Constants.FORMAT_HEADER, Constants.PHRASE_ITEM,
                Constants.PHRASE_KEY, Constants.PHRASE_MARK_AUTO, Constants.PHRASE_MODEL_AUTO,
                Constants.PHRASE_COST);
        SparesSAXBuilder.LOGGER.info(header + Constants.DIVIDING_LINE);
        try {
            this.reader.parse(this.pathToXMLFile);
        } catch (final IOException e) {
            SparesSAXBuilder.LOGGER.error(Constants.IO_EXCEPTION, e);
        } catch (final SAXException e) {
            SparesSAXBuilder.LOGGER.error(Constants.SAX_EXCEPTION, e);
        }
    }

    /**
     *
     * @param xr
     *            updated xmlReader.
     * @param pathToXMLFile
     *            file for writing.
     */
    private void writeToFile(final XMLReader xr, final String pathToXMLFile,
            final boolean validation) {
        final StringWriter writtenResult = new StringWriter();
        final Source src = new SAXSource(xr, new InputSource(pathToXMLFile));
        final Result res = new StreamResult(writtenResult);

        if (validation
                && !ValidatorSAXXSD.validate(src, new StreamSource(Constants.PATH_TO_XSD_FILE))) {
            SparesSAXBuilder.LOGGER.info(Constants.ERROR_XSD);
            this.reader.setContentHandler(this.saxHandler);
            return;
        }
        try {
            TransformerFactory.newInstance().newTransformer().transform(src, res);
        } catch (final TransformerConfigurationException e) {
            SparesSAXBuilder.LOGGER.error(Constants.TRANSFORMER_CONFIG_EXCEPTION, e);
        } catch (final TransformerException e) {
            SparesSAXBuilder.LOGGER.error(Constants.TRANSFORMER_EXCEPTION, e);
        }
        final String result = writtenResult.toString();
        FileWriter write;
        try {
            write = new FileWriter(this.pathToXMLFile);
            write.write(result);
            write.close();
            this.reader.setContentHandler(this.saxHandler);
        } catch (final IOException e) {
            SparesSAXBuilder.LOGGER.error(Constants.IO_EXCEPTION, e);
        }

    }
}
