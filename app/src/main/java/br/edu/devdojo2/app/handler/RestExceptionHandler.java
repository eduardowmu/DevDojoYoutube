package br.edu.devdojo2.app.handler;

import br.edu.devdojo2.app.Exception.BadRequestException;
import br.edu.devdojo2.app.Exception.BadRequestExceptionDetails;
import br.edu.devdojo2.app.Exception.ValidationExceptionDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class RestExceptionHandler {
    /*Se lançarmos uma exceção e for do tipo BAD_REQUEST,
    teremos que usar o seguinte método*/
    /*
    * BadRequestExceptionDetails.builder()
                .timeStamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .title("Bad request Exception")
                .details(be.getMessage())
                .developerMessage(be.getClass().getName())
                .build(), HttpStatus.BAD_REQUEST
    * */
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<BadRequestExceptionDetails> handlerBadRequestException(BadRequestException be) {
        return new ResponseEntity<>(new BadRequestExceptionDetails("Bad request Exception",
                HttpStatus.BAD_REQUEST.value(), be.getMessage(), be.getClass().getName(),
                LocalDateTime.now()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationExceptionDetails> MethodArgumentNotValidException(
            MethodArgumentNotValidException me) {
        List<FieldError> fieldErrors = me.getBindingResult().getFieldErrors();
        /*Coletar os campos com erros e filtra-los por vírgula*/
        String fields = fieldErrors.stream().map(FieldError::getField).collect(Collectors.joining(","));
        String messages = fieldErrors.stream().map(FieldError::getDefaultMessage).collect(Collectors.joining(","));
        return new ResponseEntity<>(new ValidationExceptionDetails("Invalid fields",
                HttpStatus.BAD_REQUEST.value(), me.getMessage(), me.getClass().getName(),
                LocalDateTime.now(), fields, messages), HttpStatus.BAD_REQUEST);
    }
}
