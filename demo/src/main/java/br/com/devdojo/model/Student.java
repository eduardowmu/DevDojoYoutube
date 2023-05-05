package br.com.devdojo.model;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Builder
@Entity
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
}
