package fr.univrouen.cv24.exceptions;

import fr.univrouen.cv24.entities.responses.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class CV24ControllerExceptionHandler {

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(InvalidResourceException.class)
    public ResponseEntity<ErrorResponse> handleInvalidResourceException(Exception e) {
        return new ResponseEntity<>(
                new ErrorResponse(e.getMessage(), fr.univrouen.cv24.entities.responses.ResponseStatus.ERROR),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    @ExceptionHandler(InvalidXMLException.class)
    public ResponseEntity<ErrorResponse> handleInvalidXMLException(Exception e) {
        return new ResponseEntity<>(
                new ErrorResponse(e.getMessage(), fr.univrouen.cv24.entities.responses.ResponseStatus.ERROR),
                HttpStatus.NOT_ACCEPTABLE
        );
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(CVAlreadyInDatabaseException.class)
    public ResponseEntity<ErrorResponse> handleCVAlreadyInDatabaseException(Exception e) {
        return new ResponseEntity<>(
                new ErrorResponse(e.getMessage(), fr.univrouen.cv24.entities.responses.ResponseStatus.DUPLICATED),
                HttpStatus.CONFLICT
        );
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(CVNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCVNotFoundException() {
        return new ResponseEntity<>(
                new ErrorResponse("CV not found",
                        fr.univrouen.cv24.entities.responses.ResponseStatus.NOT_FOUND),
                HttpStatus.NOT_FOUND
        );
    }

}