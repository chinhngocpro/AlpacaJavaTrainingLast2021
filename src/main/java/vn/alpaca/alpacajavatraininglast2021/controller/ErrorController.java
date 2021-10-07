package vn.alpaca.alpacajavatraininglast2021.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import vn.alpaca.alpacajavatraininglast2021.exception.AccessDeniedException;
import vn.alpaca.alpacajavatraininglast2021.exception.ResourceNotFoundException;
import vn.alpaca.alpacajavatraininglast2021.wrapper.response.ErrorResponse;

import java.sql.SQLException;

@ControllerAdvice
public class ErrorController {

    @ResponseStatus(
            value = HttpStatus.NOT_FOUND,
            reason = "The resource you search for does not exist."
    )
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

    @ResponseStatus(
            value = HttpStatus.FORBIDDEN,
            reason = "You're not authorized to access this resource."
    )
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleForbiddenException(
            AccessDeniedException exception
    ) {
        ErrorResponse error = new ErrorResponse(
                HttpStatus.FORBIDDEN.value(),
                exception.getMessage()
        );

        return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
    }

    @ResponseStatus(
            value = HttpStatus.UNAUTHORIZED,
            reason = "You're not authorized to access this feature."
    )
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleUnauthorizedException(
            org.springframework.security.access.AccessDeniedException exception
    ) {
        ErrorResponse error = new ErrorResponse(
                HttpStatus.UNAUTHORIZED.value(),
                exception.getMessage()
        );

        return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
    }

    @ResponseStatus(
            value = HttpStatus.INTERNAL_SERVER_ERROR,
            reason = "Server can not response right now, " +
                    "please try another time!"
    )
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleSQLException(
            SQLException exception
    ) {
        ErrorResponse error = new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                exception.getMessage()
        );

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ResponseStatus(
            value = HttpStatus.BAD_REQUEST,
            reason = "Something was wrong with your query, " +
                    "please check and try again!"
    )
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

}
