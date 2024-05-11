package fr.univrouen.cv24.controllers;

import fr.univrouen.cv24.entities.Cv24Type;
import fr.univrouen.cv24.entities.responses.CVResponse;
import fr.univrouen.cv24.entities.responses.CVResponseStatus;
import fr.univrouen.cv24.exceptions.CVAlreadyInDatabaseException;
import fr.univrouen.cv24.exceptions.CVNotFoundException;
import fr.univrouen.cv24.exceptions.InvalidResourceException;
import fr.univrouen.cv24.exceptions.InvalidXMLException;
import fr.univrouen.cv24.repositories.CVRepository;
import fr.univrouen.cv24.services.CV24Service;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.servers.Server;
import jakarta.xml.bind.JAXBElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.w3c.dom.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.transform.dom.DOMSource;
import java.io.ByteArrayInputStream;
import java.util.Optional;

@RestController
@OpenAPIDefinition(servers = {@Server(url = "/", description = "Default Server URL")})
class CV24Controller {

    private final CV24Service cv24Service;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private Jaxb2Marshaller marshaller;

    private final CVRepository cvRepository;

    public CV24Controller(final CV24Service cv24Service, final CVRepository cvRepository) {
        this.cv24Service = cv24Service;
        this.cvRepository = cvRepository;
    }

    @GetMapping("/")
    @Operation(summary = "Homepage of the application")
    @org.springframework.web.bind.annotation.ResponseStatus(HttpStatus.OK)
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        return modelAndView;
    }

    @GetMapping(value = "/cv24/resume/xml",
            produces = MediaType.APPLICATION_XML_VALUE)
    @Operation(summary = "Return a resume of all the CV of the database in XML format", responses = {
            @ApiResponse(responseCode = "200", description = "Resume of the CVs")
    })
    public String resumeXML() {
        return cv24Service.createCVsResume();
    }

    @GetMapping(value = "/cv24/resume",
            produces = MediaType.TEXT_HTML_VALUE)
    @Operation(summary = "Return a resume of all the CV of the database in HTML format", responses = {
            @ApiResponse(responseCode = "200", description = "Resume of the CVs")
    })
    public String resumeHTML() {
        return cv24Service.createHTMLResume();
    }

    @GetMapping(value = "/cv24/xml",
        produces = MediaType.APPLICATION_XML_VALUE)
    @Operation(summary = "Display an XML CV by its id", responses = {
            @ApiResponse(responseCode = "200", description = "Found CV"),
            @ApiResponse(responseCode = "404", description = "Invalid id, no CV found")
    })
    public ResponseEntity<String> findXMLCVById(@RequestParam long id)
            throws CVNotFoundException {
        Optional<Cv24Type> optional = cvRepository.findById(id);

        if (optional.isEmpty()) {
            throw new CVNotFoundException(id);
        }

        Cv24Type cv = optional.get();
        String xml = cv24Service.transformCVToXML(cv);

        return new ResponseEntity<>(xml, HttpStatus.OK);
    }

    @GetMapping(value ="/cv24/html",
            produces = MediaType.TEXT_HTML_VALUE)
    @Operation(summary = "Display an XML CV by its id", responses = {
            @ApiResponse(responseCode = "200", description = "Found CV"),
            @ApiResponse(responseCode = "404", description = "Invalid id, no CV found")
    })
    public ResponseEntity<String> findHTMLCVById(@RequestParam long id, Model model) {
        Optional<Cv24Type> optional = cvRepository.findById(id);

        if (optional.isEmpty()) {
            return new ResponseEntity<>("<p>" + id + "</p><p>" + CVResponseStatus.NOT_FOUND + "</p>", HttpStatus.NOT_FOUND);
        }

        Cv24Type cv = optional.get();

        return new ResponseEntity<>(cv24Service.createHTML(cv), HttpStatus.OK);
    }

    @PostMapping(value = "/cv24/insert",
            consumes = MediaType.APPLICATION_XML_VALUE,
            produces = MediaType.APPLICATION_XML_VALUE)
    @Operation(summary = "Insert a CV", responses = {
            @ApiResponse(responseCode = "200", description = "CV inserted"),
            @ApiResponse(responseCode = "406", description = "Invalid xml format"),
            @ApiResponse(responseCode = "409", description = "Duplicated identity"),
            @ApiResponse(responseCode = "500", description = "Internal error occurred")
    })
    public ResponseEntity<CVResponse> insertCV(@RequestBody String cv)
            throws InvalidResourceException,
            InvalidXMLException,
            CVAlreadyInDatabaseException {
        Document document = cv24Service.getXMLCVDocumentFromInputStream(new ByteArrayInputStream(cv.getBytes()));
        if (!cv24Service.isValidCV(document)) {
            throw new InvalidXMLException("Invalid XML format");
        }
        if (cv24Service.isAlreadyInDatabase(document)) {
            throw new CVAlreadyInDatabaseException("There is already a CV with the same identity");
        }

        JAXBElement<Cv24Type> resultCV = (JAXBElement<Cv24Type>) marshaller.unmarshal(new DOMSource(document));

        Cv24Type cvInserted =  cv24Service.saveCV(resultCV.getValue());

        logger.info("New CV inserted");

        return new ResponseEntity<>(
                new CVResponse(cvInserted.getId(), CVResponseStatus.INSERTED),
                HttpStatus.OK
        );
    }


    @DeleteMapping(value = "/cv24/delete",
        produces = MediaType.APPLICATION_XML_VALUE
    )
    @Operation(summary = "Delete a CV", responses = {
            @ApiResponse(responseCode = "200", description = "CV deleted"),
            @ApiResponse(responseCode = "404", description = "CV not found")
    })
    public ResponseEntity<CVResponse> deleteCV(@RequestParam Long id) throws CVNotFoundException {
        Optional<Cv24Type> optional = cvRepository.findById(id);

        if (optional.isEmpty()) {
            throw new CVNotFoundException(id);
        }

        cvRepository.delete(optional.get());

        logger.info("CV deleted");

        return new ResponseEntity<>(new CVResponse(id, CVResponseStatus.DELETED), HttpStatus.OK);
    }

}
