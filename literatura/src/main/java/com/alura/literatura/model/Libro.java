package com.alura.literatura.model;
import jakarta.persistence.*;

@Entity
@Table(name = "libros")
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String titulo;

    @ManyToOne(cascade = CascadeType.ALL)
    private Autor autor;

    private String idioma;

    private Integer anioNascimentoAutor;

    private Integer anioFalecimentoAutor;

    private Double numeroDownloads;

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public Autor getAutor() { return autor; }
    public void setAutor(Autor autor) { this.autor = autor; }

    public String getIdioma() { return idioma; }
    public void setIdioma(String idioma) { this.idioma = idioma; }

    public Integer getanioNascimentoAutor() { return anioNascimentoAutor; }
    public void setanioNascimentoAutor(Integer anioNascimentoAutor) { this.anioNascimentoAutor = anioNascimentoAutor; }

    public Integer getanioFalecimentoAutor() { return anioFalecimentoAutor; }
    public void setanioFalecimentoAutor(Integer anioFalecimentoAutor) { this.anioFalecimentoAutor = anioFalecimentoAutor; }

    public Double getNumeroDownloads() { return numeroDownloads; }
    public void setNumeroDownloads(Double numeroDownloads) { this.numeroDownloads = numeroDownloads; }

    // Construtores
    public Libro() {}

    // ESTE ES EL CONSTRUCTOR QUE CAMBIAMOS PARA QUE COINCIDA CON EL DTO
    public Libro(LibroDTO libroDTO) {
        this.titulo = libroDTO.titulo();

        // Verificamos que existan autores para no causar un NullPointerException
        if (libroDTO.autores() != null && !libroDTO.autores().isEmpty()) {
            this.autor = new Autor(libroDTO.autores().get(0));
            // Opcional: llenar los años del autor directamente en Libro si lo deseas
            this.anioNascimentoAutor = this.autor.getanioNascimento() != null ? this.autor.getanioNascimento().getValue() : null;
            this.anioFalecimentoAutor = this.autor.getanioFalecimento() != null ? this.autor.getanioFalecimento().getValue() : null;
        }

        // En el DTO usamos 'idiomas' (plural), aquí tomamos el primero
        if (libroDTO.idiomas() != null && !libroDTO.idiomas().isEmpty()) {
            this.idioma = libroDTO.idiomas().get(0);
        }

        this.numeroDownloads = libroDTO.numeroDownload();
    }

    public Libro(String titulo, Autor autor, String idioma, Double numeroDownload) {
        this.titulo = titulo;
        this.autor = autor;
        this.idioma = idioma;
        this.numeroDownloads = numeroDownload;
    }

    @Override
    public String toString() {
        return "Título: " + titulo + "\n" +
                "Autor: " + (autor != null ? autor.getAutor() : "Desconocido") + "\n" +
                "Idioma: " + idioma + "\n" +
                "Downloads: " + numeroDownloads + "\n" +
                "----------------------------------------";
    }
}