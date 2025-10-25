package com.example.cleanapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.List;

/**
 * CORS Configuration for the API
 * Configuração para permitir requests cross-origin de frontends específicos
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    private static final List<String> ALLOWED_ORIGINS = Arrays.asList(
        "http://localhost:3000",
        "http://localhost:8080",
        "http://localhost:5173", // Vite default
        "https://java-web-api.up.railway.app"
    );

    private static final List<String> ALLOWED_METHODS = Arrays.asList(
        "GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD", "PATCH"
    );

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(ALLOWED_ORIGINS.toArray(new String[0]))
                .allowedMethods(ALLOWED_METHODS.toArray(new String[0]))
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(3600)
                .exposedHeaders("Content-Type", "Authorization", "X-Total-Count");
    }

    /**
     * Configuração CORS adicional como Bean para garantir que seja aplicada
     * Necessário para casos onde @CrossOrigin ou outras configurações podem conflitar
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        
        // Configurar origens permitidas ESPECÍFICAS (não usar wildcard com credentials)
        configuration.setAllowedOrigins(ALLOWED_ORIGINS);
        
        // Métodos HTTP permitidos
        configuration.setAllowedMethods(ALLOWED_METHODS);
        
        // Headers permitidos
        configuration.setAllowedHeaders(Arrays.asList("*"));
        
        // Permitir cookies/credentials (necessário para Swagger UI)
        configuration.setAllowCredentials(true);
        
        // Headers expostos no response
        configuration.setExposedHeaders(Arrays.asList(
            "Content-Type", "Authorization", "X-Total-Count"
        ));
        
        // Cache do preflight
        configuration.setMaxAge(3600L);
        
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        
        return source;
    }
}
