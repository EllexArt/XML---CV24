package fr.univrouen.cv24.services;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class SimpleErrorHandler implements ErrorHandler {
    boolean errorOccurred;

    //REQUETE
    public boolean hasError() {
        return errorOccurred;
    }

    //COMMANDES

    @Override
    public void warning(SAXParseException e) throws SAXException {
        errorOccurred = true;
    }

    @Override
    public void error(SAXParseException e) throws SAXException {
        errorOccurred = true;
    }

    @Override
    public void fatalError(SAXParseException e) throws SAXException {
        errorOccurred = true;
    }
}
