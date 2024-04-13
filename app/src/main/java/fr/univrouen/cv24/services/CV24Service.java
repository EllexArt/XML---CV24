package fr.univrouen.cv24.services;

import fr.univrouen.cv24.entities.Cv24Type;
import fr.univrouen.cv24.entities.GenreType;
import fr.univrouen.cv24.entities.IdentiteType;
import fr.univrouen.cv24.exceptions.InvalidResourceException;
import fr.univrouen.cv24.exceptions.InvalidXMLException;
import fr.univrouen.cv24.repositories.CVRepository;
import fr.univrouen.cv24.repositories.IdentityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.xml.SimpleNamespaceContext;
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
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Optional;

@Service
public class CV24Service {

    @Autowired
    private IdentityRepository identityRepository;

    @Autowired
    private CVRepository cvRepository;


    /**
     * getXMLDocumentFromInputStream: parse the inputstream in entry with the cv xsd and return it
     * @param cv the file to parse
     * @return the document
     * @throws InvalidXMLException if the xsd can't be loaded
     */
    public Document getXMLCVDocumentFromInputStream(InputStream cv) throws InvalidXMLException {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);
            DocumentBuilder dBuilder = factory.newDocumentBuilder();
            return dBuilder.parse(cv);
        } catch (SAXException | IOException | ParserConfigurationException e) {
            throw new InvalidXMLException("Impossible to validate the xml");
        }
    }


    /**
     * isValidCV: return true if the xml file in entry is a valid CV by validating it with the xsd
     * @param cv the file to parse
     * @return true if valid CV
     * @throws InvalidResourceException if the xsd can't be loaded
     * @throws InvalidXMLException if the file can't be parsed
     */
    public boolean isValidCV(Document cv) throws InvalidResourceException, InvalidXMLException {
        SimpleErrorHandler simpleErrorHandler = new SimpleErrorHandler();
        Schema schema;
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
            validator.validate(new DOMSource(cv));
        } catch (SAXException | IOException e) {
            throw new InvalidXMLException("Impossible to validate the xml");
        }
        return !simpleErrorHandler.errorOccurred;
    }

    /**
     * isAlreadyInDatabase: return true if a cv with the same identity already exist in the database
     * @param cv the cv to analyse
     * @return true if already exist
     */
    public boolean isAlreadyInDatabase(Document cv) {
        XPath xPath = XPathFactory.newInstance().newXPath();
        SimpleNamespaceContext nsCtx = new SimpleNamespaceContext();
        xPath.setNamespaceContext(nsCtx);
        nsCtx.bindNamespaceUri("cv24", "http://univ.fr/cv24");

        String xpathExpressionName = "/cv24:cv24/cv24:identite/cv24:nom/text()";
        String xpathExpressionFirstName = "/cv24:cv24/cv24:identite/cv24:prenom/text()";
        String xpathExpressionGender = "/cv24:cv24/cv24:identite/cv24:genre/text()";
        String xpathExpressionPhone = "/cv24:cv24/cv24:identite/cv24:tel/text()";

        String name;
        String firstName;
        String gender;
        String phone;
        try {
            name = (String) xPath.compile(xpathExpressionName).evaluate(cv, XPathConstants.STRING);
            firstName = (String) xPath.compile(xpathExpressionFirstName).evaluate(cv, XPathConstants.STRING);
            gender = (String) xPath.compile(xpathExpressionGender).evaluate(cv, XPathConstants.STRING);
            phone = (String) xPath.compile(xpathExpressionPhone).evaluate(cv, XPathConstants.STRING);
        } catch (XPathExpressionException e) {
            throw new RuntimeException(e);
        }

        System.out.println("test");
        System.out.println(cv.getDocumentElement().getChildNodes().toString());

        Optional<IdentiteType> duplicatedIdentity =
                identityRepository.findByNomAndPrenomAndGenreAndTel(
                        name, firstName, GenreType.fromValue(gender), phone
                );

        return duplicatedIdentity.isPresent();
    }

    /**
     * saveCV: save the cv in the database
     * @param cv the cv to save
     */
    public void saveCV(Cv24Type cv) {
        cvRepository.save(cv);
    }

}
