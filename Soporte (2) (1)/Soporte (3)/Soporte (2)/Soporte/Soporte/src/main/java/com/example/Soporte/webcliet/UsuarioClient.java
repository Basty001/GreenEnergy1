package com.example.Soporte.webcliet;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class UsuarioClient {

    private final WebClient webClient;

    public UsuarioClient(@Value("${usuario-service.url}") String baseUrl) {
        this.webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    // Método para obtener todos los usuarios
    public List<Map<String, Object>> getUsuarios() {
        try {
            return webClient.get()
                    .uri("/api/v1/usuario/users")  // Ruta completa a todos los usuarios
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<List<Map<String, Object>>>() {})
                    .block();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


}
