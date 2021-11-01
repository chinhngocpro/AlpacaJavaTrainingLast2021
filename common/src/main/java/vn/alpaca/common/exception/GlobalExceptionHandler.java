package vn.alpaca.common.exception;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import vn.alpaca.common.dto.wrapper.ErrorResponse;

import javax.servlet.ServletException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Log4j2
@RequiredArgsConstructor
public class GlobalExceptionHandler {

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> handleException(Exception exception) {
    ErrorResponse response = new ErrorResponse();

    response.setStatusCode(HttpStatus.BAD_REQUEST.value());
    response.setMessage(exception.getMessage());

    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponse> handleValidationExceptions(
      MethodArgumentNotValidException exception) {
    Map<String, String> errors = new HashMap<>();
    exception
        .getBindingResult()
        .getAllErrors()
        .forEach(
            (error) -> {
              String fieldName = ((FieldError) error).getField();
              String errorMessage = error.getDefaultMessage();
              errors.put(fieldName, errorMessage);
            });

    ErrorResponse response = new ErrorResponse();
    response.setStatusCode(HttpStatus.BAD_REQUEST.value());
    response.setMessage(
        "Something were wrong with your query, " + "please check errors and try again.");
    response.setErrors(errors);

    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(AccessDeniedException.class)
  public ResponseEntity<ErrorResponse> handleUnauthorizedException(
      AccessDeniedException exception) {
    ErrorResponse response = new ErrorResponse();

    Collection<? extends GrantedAuthority> authorities =
        SecurityContextHolder.getContext().getAuthentication().getAuthorities();

    if (authorities.contains(new SimpleGrantedAuthority("ROLE_ANONYMOUS"))) {
      response.setStatusCode(HttpStatus.UNAUTHORIZED.value());
      response.setMessage("Unauthorized");
      return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    } else {
      response.setStatusCode(HttpStatus.FORBIDDEN.value());
      response.setMessage(exception.getMessage());
      return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }
  }

  @ExceptionHandler(TokenExpiredException.class)
  public ResponseEntity<ErrorResponse> handleRefreshTokenException(
      TokenExpiredException exception) {
    ErrorResponse response = new ErrorResponse();

    response.setStatusCode(HttpStatus.UNAUTHORIZED.value());
    response.setMessage(exception.getMessage());

    return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
  }

  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleNotFoundException(
      ResourceNotFoundException exception) {
    ErrorResponse response = new ErrorResponse();

    response.setStatusCode(HttpStatus.NOT_FOUND.value());
    response.setMessage(
        ObjectUtils.isEmpty(exception.getMessage())
            ? "Resource not found"
            : exception.getMessage());

    return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(ServletException.class)
  public ResponseEntity<ErrorResponse> handleServerException(ServletException exception) {
    ErrorResponse response = new ErrorResponse();

    response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
    response.setMessage(exception.getMessage());

    return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
