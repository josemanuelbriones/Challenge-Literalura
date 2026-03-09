package com.alura.literatura.service; // Ajustado a tu proyecto

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class ConsumoAPI {
    private static final Logger logger = LoggerFactory.getLogger(ConsumoAPI.class);

    public String obtenerDatos(String direccion) { // Cambié el nombre del parámetro a español
        HttpClient client = HttpClient.newBuilder()
                .followRedirects(HttpClient.Redirect.NORMAL)
                .build();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(direccion))
                .build();

        HttpResponse<String> response;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Log para que veas en consola qué está pasando
            logger.info("Status Code: " + response.statusCode());

            if (response.statusCode() >= 200 && response.statusCode() < 300) {
                return response.body();
            } else {
                throw new RuntimeException("Error al obtener datos. Código HTTP: " + response.statusCode());
            }
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Error de conexión con la API: " + e.getMessage(), e);
        }
    }
}