package br.edu.devdojo.microservices.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class CourseDTO implements Serializable {
    private Long id;
    private String title;
}