package fr.univrouen.cv24.controllers;

import fr.univrouen.cv24.entities.Cv24Type;
import fr.univrouen.cv24.entities.responses.InsertedCVResponse;
import fr.univrouen.cv24.entities.responses.Response;
import fr.univrouen.cv24.exceptions.CVAlreadyInDatabaseException;
import fr.univrouen.cv24.exceptions.CVNotFoundException;
import fr.univrouen.cv24.exceptions.InvalidResourceException;
import fr.univrouen.cv24.exceptions.InvalidXMLException;
import fr.univrouen.cv24.repositories.CVRepository;
import fr.univrouen.cv24.services.CV24Service;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.xml.bind.JAXBElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.w3c.dom.Document;

import javax.xml.transform.dom.DOMSource;
import java.io.ByteArrayInputStream;
import java.util.Optional;

@RestController
class CV24Controller {

    private final CV24Service cv24Service;

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
            throw new CVNotFoundException();
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
    public ResponseEntity<String> findHTMLCVById(@RequestParam long id) {
        Optional<Cv24Type> optional = cvRepository.findById(id);

        if (optional.isEmpty()) {
            return new ResponseEntity<>("CV not found", HttpStatus.NOT_FOUND);
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
    public ResponseEntity<Response> insertCV(@RequestBody String cv)
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

        cv24Service.saveCV(resultCV.getValue());

        return new ResponseEntity<>(
                new InsertedCVResponse(1),
                HttpStatus.OK
        );
    }

}
