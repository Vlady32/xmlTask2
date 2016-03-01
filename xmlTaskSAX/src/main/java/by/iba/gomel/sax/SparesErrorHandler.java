package by.iba.gomel.sax;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

import by.iba.gomel.Constants;

/**
 * This class describes methods errors.
 */
public class SparesErrorHandler extends DefaultHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(SparesErrorHandler.class);

    @Override
    public void error(final SAXParseException exception) {
        SparesErrorHandler.LOGGER.error(Constants.PHRASE_NOT_INCLUDE);
        SparesErrorHandler.LOGGER.error(Constants.SAX_PARSE_EXCEPTION, exception);
    }

    @Override
    public void fatalError(final SAXParseException exception) {
        SparesErrorHandler.LOGGER.error(Constants.SAX_PARSE_EXCEPTION, exception);
    }

    @Override
    public void warning(final SAXParseException exception) {
        SparesErrorHandler.LOGGER.error(Constants.SAX_PARSE_EXCEPTION, exception);
    }
}
