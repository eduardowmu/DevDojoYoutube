package br.edu.devdojo2.app.handler;

import br.edu.devdojo2.app.Exception.BadRequestException;
import br.edu.devdojo2.app.Exception.BadRequestExceptionDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class RestExceptionHandler {
    /*Se lançarmos uma exceção e for do tipo BAD_REQUEST,
    teremos que usar o seguinte método*/
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<BadRequestExceptionDetails> handlerBadRequestException(BadRequestException be) {
        return new ResponseEntity<>(BadRequestExceptionDetails.builder()
                .timeStamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .title("Bad request Exception")
                .details(be.getMessage())
                .developerMessage(be.getClass().getName())
                .build(), HttpStatus.BAD_REQUEST);
    }
}
