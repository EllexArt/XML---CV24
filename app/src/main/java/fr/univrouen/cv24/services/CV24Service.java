package fr.univrouen.cv24.services;

import fr.univrouen.cv24.exceptions.InvalidResourceException;
import fr.univrouen.cv24.exceptions.InvalidXMLException;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

@Service
public class CV24Service {

    /**
     * isValidCV: return true if the xml file in entry is a valid CV by validating it with the xsd
     * @param cv the file to parse
     * @return true if valid CV
     * @throws InvalidResourceException if the xsd can't be loaded
     * @throws InvalidXMLException if the file can't be parsed
     */
    public boolean isValidCV(InputStream cv) throws InvalidResourceException, InvalidXMLException {
        SimpleErrorHandler simpleErrorHandler = new SimpleErrorHandler();
        Schema schema = null;
        URL xsd = getClass().getClassLoader().getResource("static/cv24.xsd");
        try {
            String language = XMLConstants.W3C_XML_SCHEMA_NS_URI;
            SchemaFactory factory = SchemaFactory.newInstance(language);
            schema = factory.newSchema(xsd);
        } catch (Exception e) {
            throw new InvalidResourceException("Impossible to load the xsd");
        }
        Validator validator = schema.newValidator();
        validator.setErrorHandler(simpleErrorHandler);
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);
            DocumentBuilder dBuilder = factory.newDocumentBuilder();
            Document doc = dBuilder.parse(cv);
            validator.validate(new DOMSource(doc));
        } catch (SAXException | IOException | ParserConfigurationException e) {
            throw new InvalidXMLException("Impossible to validate the xml");
        }
        return !simpleErrorHandler.errorOccurred;
    }

}
