LiterAlura - Catálogo de Libros 📚
LiterAlura es una aplicación de consola desarrollada en Java utilizando el framework Spring Boot. Su objetivo es interactuar con la API de Gutendex para buscar libros por título, procesar los datos JSON y almacenarlos en una base de datos relacional para su posterior consulta y análisis estadístico.

🚀 Características
Búsqueda Inteligente: Localiza libros por título consumiendo datos en tiempo real desde una API externa.

Persistencia de Datos: Almacenamiento robusto en PostgreSQL mediante Spring Data JPA.

Gestión de Autores: Registro detallado de autores, incluyendo años de nacimiento y fallecimiento para filtrado histórico.

Filtros Avanzados:

Listar libros registrados.

Listar autores registrados.

Consultar autores vivos en un año específico.

Filtrar libros por idioma (Español, Inglés, Francés, etc.).

🛠️ Tecnologías Utilizadas
Java 17 (o superior).

Spring Boot 3.

Spring Data JPA: Para el mapeo objeto-relacional (ORM).

PostgreSQL: Base de datos relacional.

Jackson: Procesamiento y deserialización de JSON.

HttpClient: Para el consumo de la API Gutendex.

Desarrollado por: Jose Manuel Briones Hernandez