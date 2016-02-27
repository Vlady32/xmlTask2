package by.iba.gomel.sax;

import java.io.IOException;
import java.util.EnumSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

import by.iba.gomel.Constants;
import by.iba.gomel.process.OutputSpareProcess;
import by.iba.gomel.process.Process;
import by.iba.gomel.spare.Spare;

/**
 * This class includes methods to working with SAX parsing.
 */
public class SparesSAXHandler extends DefaultHandler {

    private static final Logger           LOGGER          = LoggerFactory
            .getLogger(SparesSAXHandler.class);
    private XMLReader                     reader;
    private final String                  pathToXMLFile;
    private Spare                         currentSpare    = null;
    private SparesElements                currentEnum     = null;
    private Process                       process         = null;
    private final EnumSet<SparesElements> rangeSpareFeatures;
    private int                           currentPosition = Constants.ONE;

    /**
     * Constructor creates set of spares.
     */
    public SparesSAXHandler(final String pathToXMLFile) {
        this.rangeSpareFeatures = EnumSet.range(SparesElements.MARKAUTO, SparesElements.COST);
        this.pathToXMLFile = pathToXMLFile;
        try {
            this.reader = XMLReaderFactory.createXMLReader();
            this.reader.setContentHandler(this);
        } catch (final SAXException e) {
            SparesSAXHandler.LOGGER.error(Constants.SAX_EXCEPTION, e);
        }
    }

    @Override
    public void characters(final char[] ch, final int start, final int length) throws SAXException {
        final String line = new String(ch, start, length);
        if (this.currentEnum != null) {
            switch (this.currentEnum) {
                case MARKAUTO:
                    this.currentSpare.setMarkAuto(line);
                    break;
                case MODELAUTO:
                    this.currentSpare.setModelAuto(line);
                    break;
                case COST:
                    this.currentSpare.setCost(Integer.parseInt(line));
                    break;
                default:
                    throw new EnumConstantNotPresentException(this.currentEnum.getDeclaringClass(),
                            this.currentEnum.name());
            }
        }
        this.currentEnum = null;
    }

    @Override
    public void endDocument() throws SAXException {
        this.process = null;
        this.currentPosition = Constants.ONE;
    }

    @Override
    public void endElement(final String uri, final String localName, final String qName)
            throws SAXException {
        if (SparesElements.SPARE.getNameElement().equals(localName)) {
            this.process.processing(this.currentSpare, this.currentPosition);
            this.currentPosition++;
        }
    }

    public void showAllSpares() {
        this.process = new OutputSpareProcess();
        final String header = String.format(Constants.FORMAT_HEADER, Constants.PHRASE_ITEM,
                Constants.PHRASE_ID, Constants.PHRASE_MARK_AUTO, Constants.PHRASE_MODEL_AUTO,
                Constants.PHRASE_COST);
        SparesSAXHandler.LOGGER.info(header + Constants.DIVIDING_LINE);
        try {
            this.reader.parse(this.pathToXMLFile);
        } catch (final IOException e) {
            SparesSAXHandler.LOGGER.error(Constants.IO_EXCEPTION, e);
        } catch (final SAXException e) {
            SparesSAXHandler.LOGGER.error(Constants.SAX_EXCEPTION, e);
        }
    }

    @Override
    public void startElement(final String uri, final String localName, final String qName,
            final Attributes attributes) throws SAXException {
        if (SparesElements.SPARE.getNameElement().equals(localName)) {
            this.currentSpare = new Spare();
            this.currentSpare.setId(attributes.getValue(0));
        } else {
            final SparesElements temp = SparesElements.valueOf(localName.toUpperCase());
            if (this.rangeSpareFeatures.contains(temp)) {
                this.currentEnum = temp;
            }
        }
    }

}
