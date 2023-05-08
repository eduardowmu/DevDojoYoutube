package br.edu.devdojo2.app.Exception;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

/*O handler é uma forma de que temos de interceptar e dizer para o
* controller interceptar as exceções e adicionar o que definirmos
* dentro do método.*/
@Getter
@SuperBuilder
public class ValidationExceptionDetails extends ExceptionDetails {
    private final String fields;
    private final String fieldsMessages;

    public ValidationExceptionDetails(String title, int status, String details,
                                      String developerMessage, LocalDateTime timeStamp,
                                      String fields, String fieldsMessages) {
        super(title, status, details, developerMessage, timeStamp);
        this.fields = fields;
        this.fieldsMessages = fieldsMessages;
    }

    public String getFields() {
        return fields;
    }

    public String getFieldsMessages() {
        return fieldsMessages;
    }
}
