package com.alura.literatura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public record AutorDTO(
        @JsonAlias("name") String autor,
        @JsonAlias("birth_year") Integer anioNascimento,
        @JsonAlias("death_year") Integer anioFalecimento
) {
    @Override
    public String toString() {
        return "Autor: " + autor;
    }
}