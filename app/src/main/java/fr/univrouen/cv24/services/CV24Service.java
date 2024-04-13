package fr.univrouen.cv24.services;

import fr.univrouen.cv24.entities.CV;
import fr.univrouen.cv24.exceptions.InvalidResourceException;
import fr.univrouen.cv24.exceptions.InvalidXMLException;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.*;
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

    /**
     * createHTML:  transform XML data
     */
    public String createHTML(CV cv) {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer;
        Source xslt = new StreamSource("src/main/resources/templates/cv.xslt");
        assert !xslt.isEmpty();
        Source xml = new StreamSource(new StringReader(cv.getContent()));
        assert !xml.isEmpty();
        try {
            transformer = transformerFactory.newTransformer(xslt);
        } catch (TransformerConfigurationException e) {
            throw new RuntimeException(e);
        }
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.setOutputProperty(OutputKeys.VERSION, "5");
        StringWriter writer = new StringWriter();
        try {
            transformer.transform(xml, new StreamResult(writer));
        } catch (TransformerException e) {
            throw new RuntimeException(e);
        }
        return writer.getBuffer().toString();
    }

}
