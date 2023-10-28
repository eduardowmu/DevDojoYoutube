package br.edu.devdojo.microservices.service;

import br.edu.devdojo.microservices.dto.CourseDTO;
import br.edu.devdojo.microservices.model.Course;
import org.springframework.data.domain.Pageable;

public interface CourseService {
    CourseDTO create(CourseDTO courseDTO);
    Iterable<Course> findAll(Pageable pageable);
}