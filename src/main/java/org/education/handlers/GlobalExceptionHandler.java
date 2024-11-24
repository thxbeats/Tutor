
package org.education.handlers;

import org.education.exceptions.BaseException;
import org.education.exceptions.ErrorResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    //private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ErrorResponse> handleBaseException(BaseException ex) {
        System.out.println("BaseException occurred: {} - {}" + ex.getErrorCode() + ex.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), ex.getErrorCode());
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
        //logger.error("Unexpected error occurred", ex);
        System.out.println(ex.getMessage());
        ErrorResponse errorResponse = new ErrorResponse("Unexpected error", "INTERNAL_ERROR");
        return ResponseEntity.internalServerError().body(errorResponse);
    }
}
