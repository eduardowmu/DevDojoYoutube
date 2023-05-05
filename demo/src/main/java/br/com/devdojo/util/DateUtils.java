package br.com.devdojo.util;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
/*Esta notação indica que esta classe é um componente*/
@Component
public class DateUtils {
    public String FormatLocalDateTimeToDatabaseStyle(LocalDateTime localDateTime) {
        return DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(localDateTime);
    }
}