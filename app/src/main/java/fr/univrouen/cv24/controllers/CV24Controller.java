package fr.univrouen.cv24.controllers;

import com.fasterxml.jackson.annotation.JacksonInject;
import fr.univrouen.cv24.entities.CV;
import fr.univrouen.cv24.entities.responses.*;
import fr.univrouen.cv24.exceptions.InvalidResourceException;
import fr.univrouen.cv24.exceptions.InvalidXMLException;
import fr.univrouen.cv24.repositories.CVRepository;
import fr.univrouen.cv24.services.CV24Service;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Description;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Optional;

@RestController
class CV24Controller {

    private final CV24Service cv24Service;

    private final CVRepository cvRepository;

    public CV24Controller(final CV24Service cv24Service, final CVRepository cvRepository) {

        this.cv24Service = cv24Service;
        this.cvRepository = cvRepository;
    }

    @GetMapping("/")
    @Operation(summary = "Homepage of the application")
    @ResponseStatus(HttpStatus.OK)
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        return modelAndView;
    }

    @GetMapping(value = "/cv24/xml",
        produces = MediaType.APPLICATION_XML_VALUE)
    @Operation(summary = "Display an XML CV by its id", responses = {
            @ApiResponse(responseCode = "200", description = "Found CV"),
            @ApiResponse(responseCode = "406", description = "Invalid id, no CV found")
    })
    public ResponseEntity<Response> findXMLCVById(@RequestParam long id) {
        Optional<CV> optional = cvRepository.findById(id);

        return optional.<ResponseEntity<Response>>map(cv -> new ResponseEntity<>(
                new XMLResponse(cv, fr.univrouen.cv24.entities.responses.ResponseStatus.FOUND),
                HttpStatus.OK
            )
        ).orElseGet(() -> new ResponseEntity<>(
                new ErrorResponse("Incorrect id, no CV are registered with it"),
                HttpStatus.NOT_ACCEPTABLE
            )
        );
    }

    @GetMapping("/cv24/html")
    @Operation(summary = "Display an XML CV by its id", responses = {
            @ApiResponse(responseCode = "200", description = "Found CV"),
            @ApiResponse(responseCode = "406", description = "Invalid id, no CV found")
    })
    public ResponseEntity<Response> findHTMLCVById(@RequestParam long id) {
        Optional<CV> optional = cvRepository.findById(id);

        return optional.<ResponseEntity<Response>>map(cv -> {
                String content = cv24Service.createHTML(cv);
                return new ResponseEntity<>(
                        new HTMLResponse(fr.univrouen.cv24.entities.responses.ResponseStatus.FOUND, content),
                        HttpStatus.OK
                );
        }
        ).orElseGet(() -> new ResponseEntity<>(
                        new ErrorResponse("Incorrect id, no CV are registered with it"),
                        HttpStatus.BAD_REQUEST
                )
        );
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
        try {
            if (cv24Service.isValidCV(new ByteArrayInputStream(cv.getBytes()))) {
                return new ResponseEntity<>(
                        new InsertedCVResponse(1, fr.univrouen.cv24.entities.responses.ResponseStatus.INSERTED),
                        HttpStatus.OK
                );
            } else {
                return new ResponseEntity<>(
                        new ErrorResponse("The xml is not a valid CV"),
                        HttpStatus.NOT_ACCEPTABLE
                );
            }
        } catch (InvalidResourceException e) {
            return new ResponseEntity<>(
                    new ErrorResponse(e.getMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        } catch (InvalidXMLException e) {
            return new ResponseEntity<>(
                    new ErrorResponse("The xml is not a valid CV"),
                    HttpStatus.NOT_ACCEPTABLE
            );
        }
    }

}
