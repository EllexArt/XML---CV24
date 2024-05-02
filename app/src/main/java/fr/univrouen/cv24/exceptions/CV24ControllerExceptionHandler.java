package fr.univrouen.cv24.exceptions;

import fr.univrouen.cv24.entities.responses.CVResponseStatus;
import fr.univrouen.cv24.entities.responses.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class CV24ControllerExceptionHandler {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(InvalidResourceException.class)
    public ResponseEntity<ErrorResponse> handleInvalidResourceException(Exception e) {
        logger.error(e.getMessage());
        return new ResponseEntity<>(
                new ErrorResponse(e.getMessage(), CVResponseStatus.ERROR),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    @ExceptionHandler(InvalidXMLException.class)
    public ResponseEntity<ErrorResponse> handleInvalidXMLException(Exception e) {
        return new ResponseEntity<>(
                new ErrorResponse(e.getMessage(), CVResponseStatus.ERROR),
                HttpStatus.NOT_ACCEPTABLE
        );
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(CVAlreadyInDatabaseException.class)
    public ResponseEntity<ErrorResponse> handleCVAlreadyInDatabaseException(Exception e) {
        return new ResponseEntity<>(
                new ErrorResponse(e.getMessage(), CVResponseStatus.DUPLICATED),
                HttpStatus.CONFLICT
        );
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(CVNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCVNotFoundException() {
        return new ResponseEntity<>(
                new ErrorResponse("CV not found",
                        CVResponseStatus.NOT_FOUND),
                HttpStatus.NOT_FOUND
        );
    }

}
