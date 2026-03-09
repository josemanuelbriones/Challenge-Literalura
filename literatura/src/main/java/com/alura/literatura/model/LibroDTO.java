package com.alura.literatura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record LibroDTO(
        @JsonAlias("title") String titulo,
        @JsonAlias("download_count") Double numeroDownload,
        @JsonAlias("languages") List<String> idiomas, // Cambiado a plural para mayor claridad
        @JsonAlias("authors") List<AutorDTO> autores
) {
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Título: ").append(titulo).append("\n");
        sb.append("Autor(es): \n");
        if (autores != null) {
            for (AutorDTO autor : autores) {
                sb.append("  - ").append(autor.autor()).append("\n");
            }
        }
        sb.append("Idioma(s): ").append(idiomas != null ? String.join(", ", idiomas) : "Desconocido").append("\n");
        sb.append("Downloads: ").append(numeroDownload).append("\n");
        sb.append("----------------------------------------");
        return sb.toString();
    }
}