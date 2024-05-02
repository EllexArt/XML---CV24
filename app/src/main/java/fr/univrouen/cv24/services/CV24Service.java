package fr.univrouen.cv24.services;

import fr.univrouen.cv24.entities.Cv24Type;
import fr.univrouen.cv24.entities.GenreType;
import fr.univrouen.cv24.entities.IdentiteType;
import fr.univrouen.cv24.entities.resume.CVList;
import fr.univrouen.cv24.entities.resume.CVid;
import fr.univrouen.cv24.exceptions.InvalidResourceException;
import fr.univrouen.cv24.exceptions.InvalidXMLException;
import fr.univrouen.cv24.repositories.CVRepository;
import fr.univrouen.cv24.repositories.IdentiteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Service;
import org.springframework.util.xml.SimpleNamespaceContext;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
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
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Optional;

@Service
public class CV24Service {

    @Autowired
    private IdentiteRepository identityRepository;

    @Autowired
    private CVRepository cvRepository;

    @Autowired
    private Jaxb2Marshaller marshaller;


    /**
     * getXMLDocumentFromInputStream: parse the inputstream in entry and return it
     * @param cv the file to parse
     * @return the document
     * @throws InvalidXMLException if the file can't be parsed
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
     */
    public boolean isValidCV(Document cv) throws InvalidResourceException {
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
            return false;
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

        Optional<IdentiteType> duplicatedIdentity =
                identityRepository.findByNomAndPrenomAndGenreAndTel(
                        name, firstName, GenreType.fromValue(gender), phone
                );

        return duplicatedIdentity.isPresent();
    }

    /**
     * transformCVToXML : transform a Cv24Type into an XML string
     * @param cv Cv24Type
     * @return String
     */
    public String transformCVToXML(Cv24Type cv) {
        StringWriter writer = new StringWriter();
        marshaller.marshal(cv, new StreamResult(writer));
        return writer.toString();
    }

    /**
     * saveCV: save the cv in the database
     * @param cv the cv to save
     * @return the cv inserted
     */
    public Cv24Type saveCV(Cv24Type cv) {
        return cvRepository.save(cv);
    }

    /**
     * createHTML:  transform XML data
     */
    public String createHTML(Cv24Type cv) {
        return this.applySelectedXSLTOnCV(cv, "src/main/resources/templates/cv.xslt", "5");
    }


    /**
     * createCVsResume: create a list of resume for all the cvs of the database
     * @return the list of the resume of the cvs in XML format
     */
    public String createCVsResume() {
        Iterable<Cv24Type> cvList = cvRepository.findAll();

        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = null;
        try {
            docBuilder = docFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        }
        Document doc = docBuilder.newDocument();
        Element root = doc.createElement("cvlist");
        doc.appendChild(root);

        for (Cv24Type cv : cvList) {
            Element cvElement = doc.createElement("resume");
            root.appendChild(cvElement);

            Element idElement = doc.createElement("id");
            idElement.setTextContent(String.valueOf(cv.getId()));
            cvElement.appendChild(idElement);

            String resumeValue = this.createResumeForCV(cv);
            try {
                Node node =  doc.importNode(docBuilder.parse(new ByteArrayInputStream(resumeValue.getBytes()))
                        .getDocumentElement(), true);
                cvElement.appendChild(node);
            } catch (SAXException | IOException e) {
                throw new RuntimeException(e);
            }

        }
        try {
            return this.transformDocumentInString(doc);
        } catch (TransformerException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * createHTMLResume: create a HTML resume of all the CV of the database
     * @return
     */
    public String createHTMLResume() {
        String XMLResume = createCVsResume();
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer;
        Source xslt = new StreamSource("src/main/resources/templates/htmlResume.xslt");
        assert !xslt.isEmpty();
        Source xml = new StreamSource(new StringReader(XMLResume));
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

    /**
     * createResumeForCV: create a short resume of the cv
     * @param cv the cv to resume
     * @return string in XML format
     */
    private String createResumeForCV(Cv24Type cv) {
        return this.applySelectedXSLTOnCV(cv, "src/main/resources/templates/resume.xslt", "1.0");
    }


    /**
     * applySelectedXSLTOnCV: apply the selected xslt transformation on the selected cv
     * @param cv the cv to transform
     * @param xsltPath the xslt transformation to apply
     * @return the transformed cv
     */
    private String applySelectedXSLTOnCV(Cv24Type cv, String xsltPath, String version) {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer;
        Source xslt = new StreamSource(xsltPath);
        assert !xslt.isEmpty();
        Source xml = new StreamSource(new StringReader(transformCVToXML(cv)));
        assert !xml.isEmpty();
        try {
            transformer = transformerFactory.newTransformer(xslt);
        } catch (TransformerConfigurationException e) {
            throw new RuntimeException(e);
        }
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.setOutputProperty(OutputKeys.VERSION, version);
        StringWriter writer = new StringWriter();
        try {
            transformer.transform(xml, new StreamResult(writer));
        } catch (TransformerException e) {
            throw new RuntimeException(e);
        }
        return writer.getBuffer().toString();
    }

    /**
     * transformDocumentInString: transform the selected document into a string
     * @param dom the dom to transform
     * @return string representation of the document
     * @throws TransformerException if a problem occur during the transformation
     */
    private String transformDocumentInString(Document dom)
            throws TransformerException {
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
        StringWriter writer = new StringWriter();
        transformer.transform(new DOMSource(dom), new StreamResult(writer));
        return writer.getBuffer().toString();
    }

}
