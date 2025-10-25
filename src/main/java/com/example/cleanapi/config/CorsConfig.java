package com.example.cleanapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;
import java.util.List;

/**
 * CORS Configuration for the API
 * Configuração RESTRITIVA para permitir requests cross-origin apenas de frontends específicos
 */
@Configuration
public class CorsConfig {

    private static final List<String> ALLOWED_ORIGINS = Arrays.asList(
        "http://localhost:3000",
        "http://localhost:8080",
        "http://localhost:5173", // Vite default
        "https://java-web-api.up.railway.app"
    );

    private static final List<String> ALLOWED_METHODS = Arrays.asList(
        "GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD", "PATCH"
    );    /**
     * Configuração CORS como Bean Principal para garantir precedência
     * Substitui qualquer configuração padrão do Spring Boot
     * Configuração específica para Swagger UI funcionar corretamente
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        
        // IMPORTANTE: Configurar origens permitidas ESPECÍFICAS (não usar wildcard com credentials)
        configuration.setAllowedOrigins(ALLOWED_ORIGINS);
        
        // Métodos HTTP permitidos
        configuration.setAllowedMethods(ALLOWED_METHODS);
        
        // Headers permitidos - importante para Swagger UI
        configuration.setAllowedHeaders(Arrays.asList(
            "*", 
            "Authorization", 
            "Content-Type", 
            "Accept", 
            "Origin", 
            "Access-Control-Request-Method", 
            "Access-Control-Request-Headers"
        ));
        
        // Permitir cookies/credentials (necessário para Swagger UI)
        configuration.setAllowCredentials(true);
        
        // Headers expostos no response
        configuration.setExposedHeaders(Arrays.asList(
            "Content-Type", 
            "Authorization", 
            "X-Total-Count",
            "Access-Control-Allow-Origin",
            "Access-Control-Allow-Credentials"
        ));
        
        // Cache do preflight
        configuration.setMaxAge(3600L);
        
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        
        return source;
    }

    /**
     * CorsFilter Bean para garantir que a configuração seja aplicada
     * Tem precedência sobre configurações padrão do Spring Boot
     */
    @Bean
    public CorsFilter corsFilter() {
        return new CorsFilter(corsConfigurationSource());
    }
}
