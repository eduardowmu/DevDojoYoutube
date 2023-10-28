package br.edu.devdojo.microservices.controller;

import br.edu.devdojo.microservices.dto.CourseDTO;
import br.edu.devdojo.microservices.model.Course;
import br.edu.devdojo.microservices.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/courses")
public class CourseController {
    private final CourseService courseService;
    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @PostMapping("/save")
    public ResponseEntity<CourseDTO> create(@RequestBody CourseDTO courseDTO) {
        return ResponseEntity.ok(this.courseService.create(courseDTO));
    }

    @GetMapping
    public ResponseEntity<Page<Course>> findAll(Pageable pageable) {
        return null;
    }
}