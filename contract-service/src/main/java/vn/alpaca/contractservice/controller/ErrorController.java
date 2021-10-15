package vn.alpaca.contractservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import vn.alpaca.response.exception.AccessDeniedException;
import vn.alpaca.response.exception.ResourceNotFoundException;
import vn.alpaca.response.wrapper.ErrorResponse;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ErrorController {

    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleNotFoundException(
            ResourceNotFoundException exception
    ) {
        ErrorResponse error = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                exception.getMessage()
        );

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleUnauthorizedException(
            org.springframework.security.access.AccessDeniedException exception
    ) {
        ErrorResponse error = new ErrorResponse(
                HttpStatus.UNAUTHORIZED.value(),
                !ObjectUtils.isEmpty(exception.getMessage())
                        ? exception.getMessage()
                        : "You're not authorized to access this feature."
        );

        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }

    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleForbiddenException(
            AccessDeniedException exception
    ) {
        ErrorResponse error = new ErrorResponse(
                HttpStatus.FORBIDDEN.value(),
                !ObjectUtils.isEmpty(exception.getMessage())
                        ? exception.getMessage()
                        : "You do not have permission to access this feature."
        );

        return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
    }

    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleSQLException(
            SQLException exception
    ) {
        ErrorResponse error = new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Server can not response right now, " +
                        "please try another time."
        );

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(
            Exception exception
    ) {
        ErrorResponse error = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                exception.getMessage()
        );

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(
            MethodArgumentNotValidException exception
    ) {

        Map<String, String> errors = new HashMap<>();
        exception.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        ErrorResponse error = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Something were wrong with your query, " +
                        "please check errors and try again.",
                errors
        );

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }


}
