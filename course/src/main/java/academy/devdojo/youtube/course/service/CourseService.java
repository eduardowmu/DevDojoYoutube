package academy.devdojo.youtube.course.service;

import academy.devdojo.youtube.course.model.Course;
import academy.devdojo.youtube.course.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;



@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CourseService {
    private final CourseRepository courseRepository;

    public Page<Course> list(Pageable pageable) {
        log.info("Listing all courses");
        return this.courseRepository.findAll(pageable);
    }
}