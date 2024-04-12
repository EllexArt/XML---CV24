package fr.univrouen.cv24.controllers;

import com.fasterxml.jackson.annotation.JacksonInject;
import fr.univrouen.cv24.entities.CV;
import fr.univrouen.cv24.entities.responses.ErrorResponse;
import fr.univrouen.cv24.entities.responses.InsertedCVResponse;
import fr.univrouen.cv24.entities.responses.Response;
import fr.univrouen.cv24.exceptions.InvalidResourceException;
import fr.univrouen.cv24.exceptions.InvalidXMLException;
import fr.univrouen.cv24.services.CV24Service;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Description;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

@RestController
class CV24Controller {

    private final CV24Service cv24Service;

    public CV24Controller(final CV24Service cv24Service) {
        this.cv24Service = cv24Service;
    }

    @GetMapping("/")
    @Operation(summary = "Homepage of the application")
    @ResponseStatus(HttpStatus.OK)
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
