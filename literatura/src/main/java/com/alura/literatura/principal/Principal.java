package com.alura.literatura.principal; // Ajustado

import com.alura.literatura.model.Autor;
import com.alura.literatura.model.AutorDTO;
import com.alura.literatura.model.Libro; // Cambiado Libro -> Libro
import com.alura.literatura.model.LibroDTO; // Cambiado LibroDTO -> LibroDTO
import com.alura.literatura.repository.LibroRepository;
import com.alura.literatura.service.ConsumoAPI;
import com.alura.literatura.service.ConvertirDatos;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Year;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class Principal {

    private LibroRepository libroRepository;

    private ConsumoAPI consumoAPI;

    private ConvertirDatos convertirDatos;

    private final Scanner lectura = new Scanner(System.in);

    // Constructor ajustado
    public Principal(LibroRepository libroRepository, ConsumoAPI consumoAPI, ConvertirDatos convertirDatos) {
        this.libroRepository = libroRepository;
        this.consumoAPI = consumoAPI;
        this.convertirDatos = convertirDatos;
    }

    public void ejecutar() {
        boolean running = true;
        while (running) {
            exibirMenu();
            var opcao = lectura.nextInt();
            lectura.nextLine();

            switch (opcao) {
                case 1 -> buscarLibrosPeloTitulo();
                case 2 -> listarLibrosRegistrados();
                case 3 -> listarAutoresRegistrados();
                case 4 -> listarAutoresVivos();
                case 5 -> listarAutoresVivosRefinado();
                case 6 -> listarAutoresPoranioDeMorte();
                case 7 -> listarLibrosPorIdioma();
                case 0 -> {
                    System.out.println("Encerrando a Literatura!"); // Ajustado nombre
                    running = false;
                }
                default -> System.out.println("Opção inválida!");
            }
        }
    }

    private void exibirMenu() {
        System.out.println("""
            ===========================================================
                                  LITERATURA
                   Uma aplicação para você que gosta de libros !
                   Escolha um número no menu abaixo:
            -----------------------------------------------------------
                                     Menu
                       1- Buscar libros pelo título
                       2- Listar libros registrados
                       3- Listar autores registrados
                       4- Listar autores vivos em um determinado anio
                       5- Listar autores nascidos em determinado anio
                       6- Listar autores por anio de sua morte
                       7- Listar libros em um determinado idioma
                       0- Sair
            """);
    }

    private void salvarLibros(List<Libro> libros) {
        libros.forEach(libroRepository::save);
    }

    private void buscarLibrosPeloTitulo() {
        String baseURL = "https://gutendex.com/books?search=";

        try {
            System.out.println("Digite o título do libro: ");
            String titulo = lectura.nextLine();
            String endereco = baseURL + titulo.replace(" ", "%20");

            String jsonResponse = consumoAPI.obtenerDatos(endereco);

            if (jsonResponse.isEmpty()) {
                System.out.println("Resposta da API está vazia.");
                return;
            }

            JsonNode rootNode = convertirDatos.getObjectMapper().readTree(jsonResponse);
            JsonNode resultsNode = rootNode.path("results");

            if (resultsNode.isEmpty()) {
                System.out.println("Não foi possível encontrar o libro buscado.");
                return;
            }

            // Mapeo al nuevo LibroDTO
            List<LibroDTO> librosDTO = convertirDatos.getObjectMapper()
                    .readerForListOf(LibroDTO.class)
                    .readValue(resultsNode);

            // Evitar duplicados
            List<Libro> librosExistentes = libroRepository.findByTituloContainingIgnoreCase(titulo);
            if (!librosExistentes.isEmpty()) {
                for (Libro libroExistente : librosExistentes) {
                    librosDTO.removeIf(dto -> libroExistente.getTitulo().equalsIgnoreCase(dto.titulo()));
                }
            }

            if (!librosDTO.isEmpty()) {
                List<Libro> nuevosLibros = librosDTO.stream()
                        .map(Libro::new)
                        .collect(Collectors.toList());
                salvarLibros(nuevosLibros);
                System.out.println("Libros salvos com sucesso!");

                librosDTO.forEach(System.out::println);
            } else {
                System.out.println("Todos os libros encontrados já estão no banco de dados.");
            }

        } catch (Exception e) {
            System.out.println("Erro ao buscar libros: " + e.getMessage());
        }
    }
    private void listarLibrosRegistrados() {
        List<Libro> libros = libroRepository.findAll();
        if (libros.isEmpty()) {
            System.out.println("Nenhum libro registrado.");
        } else {
            libros.forEach(System.out::println);
        }
    }

    private void listarAutoresRegistrados() {
        List<Libro> libros = libroRepository.findAll();
        if (libros.isEmpty()) {
            System.out.println("Nenhum autor registrado.");
        } else {
            libros.stream()
                    .map(Libro::getAutor)
                    .distinct()
                    .forEach(autor -> System.out.println(autor.getAutor()));
        }
    }

    private void listarAutoresVivos() {
        System.out.println("Digite o anio: ");
        Integer anio = lectura.nextInt();
        lectura.nextLine();

        Year year = Year.of(anio);

        List<Autor> autores = libroRepository.findAutoresVivos(year);
        if (autores.isEmpty()) {
            System.out.println("Nenhum autor vivo encontrado.");
        } else {
            System.out.println("Lista de autores vivos no anio de " + anio + ":\n");

            autores.forEach(autor -> {
                if(Autor.possuianio(autor.getanioNascimento()) && Autor.possuianio(autor.getanioFalecimento())){
                    String nomeAutor = autor.getAutor();
                    String anioNascimento = autor.getanioNascimento().toString();
                    String anioFalecimento = autor.getanioFalecimento().toString();
                    System.out.println(nomeAutor + " (" + anioNascimento + " - " + anioFalecimento + ")");
                }
            });
        }
    }

    private void listarAutoresVivosRefinado() {
        System.out.println("Digite o anio: ");
        Integer anio = lectura.nextInt();
        lectura.nextLine();

        Year year = Year.of(anio);

        List<Autor> autores = libroRepository.findAutoresVivosRefinado(year);
        if (autores.isEmpty()) {
            System.out.println("Nenhum autor vivo encontrado.");
        } else {
            System.out.println("Lista de autores nascidos no anio de " + anio + ":\n");

            autores.forEach(autor -> {
                if(Autor.possuianio(autor.getanioNascimento()) && Autor.possuianio(autor.getanioFalecimento())){
                    String nomeAutor = autor.getAutor();
                    String anioNascimento = autor.getanioNascimento().toString();
                    String anioFalecimento = autor.getanioFalecimento().toString();
                    System.out.println(nomeAutor + " (" + anioNascimento + " - " + anioFalecimento + ")");

                }
            });
        }
    }

    private void listarAutoresPoranioDeMorte() {
        System.out.println("Digite o anio: ");
        Integer anio = lectura.nextInt();
        lectura.nextLine();

        Year year = Year.of(anio);

        List<Autor> autores = libroRepository.findAutoresPoranioDeMorte(year);
        if (autores.isEmpty()) {
            System.out.println("Nenhum autor vivo encontrado.");
        } else {

            System.out.println("Lista de autores que morreram no anio de " + anio + ":\n");


            autores.forEach(autor -> {
                if (Autor.possuianio(autor.getanioNascimento()) && Autor.possuianio(autor.getanioFalecimento())){
                    String nomeAutor = autor.getAutor();
                    String anioNascimento = autor.getanioNascimento().toString();
                    String anioFalecimento = autor.getanioFalecimento().toString();
                    System.out.println(nomeAutor + " (" + anioNascimento + " - " + anioFalecimento + ")");
                }
            });
        }
    }


    private void listarLibrosPorIdioma() {
        System.out.println("""
            Digite o idioma pretendido:
            Inglês (en)
            Português (pt)
            Espanhol (es)
            Francês (fr)
            Alemão (de)
            """);
        String idioma = lectura.nextLine();

        List<Libro> libros = libroRepository.findByIdioma(idioma);
        if (libros.isEmpty()) {
            System.out.println("Nenhum libro encontrado no idioma especificado.");
        } else {
            libros.forEach(libro -> {
                String titulo = libro.getTitulo();
                String autor = libro.getAutor().getAutor();
                String idiomaLibro = libro.getIdioma();

                System.out.println("Título: " + titulo);
                System.out.println("Autor: " + autor);
                System.out.println("Idioma: " + idiomaLibro);
                System.out.println("----------------------------------------");
            });
        }
    }


}