package br.edu.devdojo2.app.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class AnimePostReqDto {
    private Long id;

    /*O campo nome não pode ser vazio e nem nulo*/
    @NotEmpty(message = "The name must not be empty")
    @NotNull(message = "The name must not be null")
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
