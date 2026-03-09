package com.alura.literatura.model;

import jakarta.persistence.*;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "autores")
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String autor;

    @Column(name = "anio_nascimento")
    private Year anioNascimento;

    @Column(name = "anio_falecimento")
    private Year anioFalecimento;

    @OneToMany(mappedBy = "autor", fetch = FetchType.EAGER)
    private List<Libro> libros = new ArrayList<>();

    // Getters e setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public Year getanioNascimento() {
        return anioNascimento;
    }

    public void setanioNascimento(Year anioNascimento) {
        this.anioNascimento = anioNascimento;
    }

    public Year getanioFalecimento() {
        return anioFalecimento;
    }

    public void setanioFalecimento(Year anioFalecimento) {
        this.anioFalecimento = anioFalecimento;
    }

    public List<Libro> getLibros() {
        return libros;
    }

    public void setLibros(List<Libro> libros) {
        this.libros = libros;
    }

    // Construtores
    public Autor() {}

    public static boolean possuianio(Year anio) {
        return anio != null && !anio.equals(Year.of(0));
    }

    public Autor(AutorDTO autorDTO) {
        this.autor = autorDTO.autor();
        this.anioNascimento = autorDTO.anioNascimento() != null ? Year.of(autorDTO.anioNascimento()) : null;
        this.anioFalecimento = autorDTO.anioFalecimento() != null ? Year.of(autorDTO.anioFalecimento()) : null;
    }


    public Autor(String autor, Year anioNascimento, Year anioFalecimento) {
        this.autor = autor;
        this.anioNascimento = anioNascimento;
        this.anioFalecimento = anioFalecimento;
    }

    @Override
    public String toString() {
        String anioNascimentoStr = anioNascimento != null ? anioNascimento.toString() : "Desconhecido";
        String anioFalecimentoStr = anioFalecimento != null ? anioFalecimento.toString() : "Desconhecido";

        return "Autor: " + autor + " (nascido em " + anioNascimentoStr + ", falecido em " + anioFalecimentoStr + ")";
    }
}