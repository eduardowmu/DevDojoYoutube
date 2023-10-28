package br.edu.devdojo.microservices.service.impl;

import br.edu.devdojo.microservices.dto.CourseDTO;
import br.edu.devdojo.microservices.mapper.CourseMapper;
import br.edu.devdojo.microservices.model.Course;
import br.edu.devdojo.microservices.repository.CourseRepository;
import br.edu.devdojo.microservices.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {
    private final CourseRepository courseRepository;

    private final CourseMapper courseMapper;

    @Autowired
    public CourseServiceImpl(CourseRepository courseRepository, CourseMapper courseMapper) {
        this.courseRepository = courseRepository;
        this.courseMapper = courseMapper;
    }

    @Override
    public CourseDTO create(CourseDTO courseDTO) {
        var course = this.courseMapper.toModel(courseDTO);
        return this.courseMapper.toDTO(this.courseRepository.save(course));
    }

    @Override
    public Page<Course> findAll(Pageable pageable) {
        Page<Course> courses = this.courseRepository.findAll(pageable);
        return null;
    }
}