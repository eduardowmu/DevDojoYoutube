package br.com.devdojo.endpoint;

import br.com.devdojo.model.Student;
import lombok.Builder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/*Classe que contém o endpoint onde os usuários irão acessar a API.
* A diferença entre as notações @Controller e @RestController, é que
* o segundo já possui a ResponseBody, sem precisar adicionar a respectiva
* notação.*/
@RestController
@RequestMapping("/student")
public class StudentController {

    @GetMapping
    public ResponseEntity<List<Student>> listAll() {
        List<Student> students = new ArrayList<>();
        students.add(Student.builder().name("Eduardo").build());
        students.add(Student.builder().name("Camila").build());
        return ResponseEntity.ok(students);
    }
}