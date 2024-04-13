package fr.univrouen.cv24.controllers;

import fr.univrouen.cv24.entities.Cv24Type;
import fr.univrouen.cv24.entities.responses.ErrorResponse;
import fr.univrouen.cv24.entities.responses.InsertedCVResponse;
import fr.univrouen.cv24.entities.responses.Response;
import fr.univrouen.cv24.exceptions.InvalidResourceException;
import fr.univrouen.cv24.exceptions.InvalidXMLException;
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
import fr.univrouen.cv24.entities.responses.ResponseStatus;

import javax.xml.transform.dom.DOMSource;
import java.io.ByteArrayInputStream;

@RestController
class CV24Controller {

    private final CV24Service cv24Service;

    @Autowired
    private Jaxb2Marshaller marshaller;

    public CV24Controller(final CV24Service cv24Service) {
        this.cv24Service = cv24Service;
    }

    @GetMapping("/")
    @Operation(summary = "Homepage of the application")
    @org.springframework.web.bind.annotation.ResponseStatus(HttpStatus.OK)
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        return modelAndView;
    }

    @PostMapping(value = "/cv24/insert",
            consumes = MediaType.APPLICATION_XML_VALUE,
            produces = MediaType.APPLICATION_XML_VALUE)
    @Operation(summary = "Insert a CV", responses = {
            @ApiResponse(responseCode = "200", description = "CV inserted"),
            @ApiResponse(responseCode = "406", description = "Invalid xml format"),
            @ApiResponse(responseCode = "500", description = "Internal error occurred")
    })
    public ResponseEntity<Response> insertCV(@RequestBody String cv) {
        Document document;
        try {
            document = cv24Service.getXMLCVDocumentFromInputStream(new ByteArrayInputStream(cv.getBytes()));
            if (!cv24Service.isValidCV(document)) {
                return new ResponseEntity<>(
                        new ErrorResponse("The xml is not a valid CV", ResponseStatus.ERROR),
                        HttpStatus.NOT_ACCEPTABLE
                );
            }
        } catch (InvalidResourceException e) {
            return new ResponseEntity<>(
                    new ErrorResponse(e.getMessage(), ResponseStatus.ERROR),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        } catch (InvalidXMLException e) {
            return new ResponseEntity<>(
                    new ErrorResponse("The xml is not a valid CV", ResponseStatus.ERROR),
                    HttpStatus.NOT_ACCEPTABLE
            );
        }

        if (cv24Service.isAlreadyInDatabase(document)) {
            return new ResponseEntity<>(
                    new ErrorResponse("Already in database", ResponseStatus.DUPLICATED),
                    HttpStatus.NOT_ACCEPTABLE
            );
        }

        JAXBElement<Cv24Type> resultCV = (JAXBElement<Cv24Type>) marshaller.unmarshal(new DOMSource(document));

        cv24Service.saveCV(resultCV.getValue());

        return new ResponseEntity<>(
                new InsertedCVResponse(1),
                HttpStatus.OK
        );
    }

}
