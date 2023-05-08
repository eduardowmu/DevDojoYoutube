package br.edu.devdojo2.app.Exception;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@SuperBuilder
public class BadRequestExceptionDetails extends ExceptionDetails{
    public BadRequestExceptionDetails(String title, int status, String details,
                                      String developerMessage, LocalDateTime timeStamp) {
        super(title, status, details, developerMessage, timeStamp);
    }
}
