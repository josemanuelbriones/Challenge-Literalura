package com.alura.literatura.service; // Ajustado

import com.alura.literatura.model.LibroDTO; // Ajustado a LibroDTO
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConvertirDatos implements IConvertirDatos {
    private final ObjectMapper objectMapper = new ObjectMapper();

    // Este método lo usa tu clase Principal para leer el nodo "results"
    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    /**
     * Convierte un JSON a cualquier clase que le pidas (Genérico)
     */
    public <T> T obtenerDados(String json, Class<T> clase) {
        try {
            return objectMapper.readValue(json, clase);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error al convertir los datos: " + e.getMessage());
        }
    }

    /**
     * Convierte un JSON específicamente a una lista de LibroDTO
     */
    public List<LibroDTO> obtenerListaDeLibros(String json) {
        try {
            CollectionType listType = objectMapper.getTypeFactory()
                    .constructCollectionType(List.class, LibroDTO.class);
            return objectMapper.readValue(json, listType);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error al convertir la lista de libros: " + e.getMessage());
        }
    }
}