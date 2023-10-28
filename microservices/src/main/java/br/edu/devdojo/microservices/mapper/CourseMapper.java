package br.edu.devdojo.microservices.mapper;

import br.edu.devdojo.microservices.dto.CourseDTO;
import br.edu.devdojo.microservices.model.Course;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CourseMapper {
    @Mapping(target = "id", expression = "java(courseDTO.getId() != null ? courseDTO.getId() : null)")
    @Mapping(target = "title", expression = "java(courseDTO.getTitle() != null ? courseDTO.getTitle() : \"\")")
    Course toModel(CourseDTO courseDTO);

    @Mapping(target = "id", expression = "java(course.getId() != null ? course.getId() : null)")
    @Mapping(target = "title", expression = "java(course.getTitle() != null ? course.getTitle() : \"\")")
    CourseDTO toDTO(Course course);
}