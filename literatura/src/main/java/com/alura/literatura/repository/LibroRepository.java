package com.alura.literatura.repository;

import com.alura.literatura.model.Autor;
import com.alura.literatura.model.Libro; // Ajustado a Libro
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Year;
import java.util.List;

public interface LibroRepository extends JpaRepository<Libro, Long> {

    // Cambiado para que coincida con la llamada en Principal
    List<Libro> findByTituloContainingIgnoreCase(String titulo);

    @Query("SELECT a FROM Autor a WHERE a.anioNascimento <= :anio AND (a.anioFalecimento IS NULL OR a.anioFalecimento >= :anio)")
    List<Autor> findAutoresVivos(@Param("anio") Year anio);

    @Query("SELECT a FROM Autor a WHERE a.anioNascimento = :anio AND (a.anioFalecimento IS NULL OR a.anioFalecimento >= :anio)")
    List<Autor> findAutoresVivosRefinado(@Param("anio") Year anio);

    @Query("SELECT a FROM Autor a WHERE a.anioNascimento <= :anio AND a.anioFalecimento = :anio")
    List<Autor> findAutoresPoranioDeMorte(@Param("anio") Year anio);

    @Query("SELECT l FROM Libro l WHERE l.idioma LIKE %:idioma%")
    List<Libro> findByIdioma(@Param("idioma") String idioma);
}