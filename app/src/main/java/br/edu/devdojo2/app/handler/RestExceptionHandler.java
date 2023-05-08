package br.edu.devdojo2.app.handler;

import br.edu.devdojo2.app.Exception.BadRequestException;
import br.edu.devdojo2.app.Exception.BadRequestExceptionDetails;
import br.edu.devdojo2.app.Exception.ValidationExceptionDetails;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    /*Se lançarmos uma exceção e for do tipo BAD_REQUEST,
    teremos que usar o seguinte método*/
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<BadRequestExceptionDetails> handlerBadRequestException(BadRequestException be) {
        return new ResponseEntity<>(new BadRequestExceptionDetails("Bad request Exception",
                HttpStatus.BAD_REQUEST.value(), be.getMessage(), be.getClass().getName(),
                LocalDateTime.now()), HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        /*Coletar os campos com erros e filtra-los por vírgula*/
        String fields = fieldErrors.stream().map(FieldError::getField).collect(Collectors.joining(","));
        String messages = fieldErrors.stream().map(FieldError::getDefaultMessage).collect(Collectors.joining(","));
        return new ResponseEntity<>(new ValidationExceptionDetails("Invalid fields",
                HttpStatus.BAD_REQUEST.value(), ex.getMessage(), ex.getClass().getName(),
                LocalDateTime.now(), fields, messages), HttpStatus.BAD_REQUEST);
    }

    /*Copiado da Classe ResponseEntityExceptionHandler*/
    @Override
    protected ResponseEntity<Object> handleExceptionInternal(
            Exception ex, @Nullable Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {

        BadRequestExceptionDetails exception = new BadRequestExceptionDetails(ex.getCause().getMessage(),
                HttpStatus.BAD_REQUEST.value(), ex.getMessage(), ex.getClass().getName(),
                LocalDateTime.now());
        return new ResponseEntity<>(exception, headers, status);
    }
}
