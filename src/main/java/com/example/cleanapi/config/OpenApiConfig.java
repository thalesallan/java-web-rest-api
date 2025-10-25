package com.example.cleanapi.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * OpenAPI/Swagger configuration with dynamic version and build info
 */
@Configuration
public class OpenApiConfig {

    @Value("${app.version:1.0.0}")
    private String appVersion;

    @Value("${app.branch:main}")
    private String gitBranch;

    @Bean
    public OpenAPI customOpenAPI() {
        String buildTimestamp = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        
        String deployUrl = "https://java-web-api.up.railway.app";
        
        String description = String.format("""
            🚀 **Java REST API seguindo Clean Architecture**
            
            📋 **Informações do Deploy:**
            • **Branch:** %s
            • **Versão:** %s  
            • **Build:** %s
            • **URL:** [%s](%s)
            
            🏗️ **Arquitetura:**
            • Clean Architecture com SOLID principles
            • Separação clara de responsabilidades
            • Testes unitários completos
            • Documentação automática
            
            🔧 **Tecnologias:**
            • Java 17 + Spring Boot 3.2.0
            • Spring Data JPA + H2 Database
            • Maven + GitHub Actions
            • Railway Deploy
            """, 
            gitBranch, appVersion, buildTimestamp, deployUrl, deployUrl);        return new OpenAPI()
                .servers(List.of(
                    new Server().url("https://java-web-api.up.railway.app").description("Production Server"),
                    new Server().url("http://localhost:8080").description("Development Server")
                ))
                .info(new Info()
                        .title("🌟 Clean Architecture REST API")
                        .version(appVersion + " (" + gitBranch + ")")
                        .description(description)
                        .contact(new Contact()
                                .name("Thales Allan Dev")
                                .url("https://github.com/thalesallan")
                                .email("trabalhecomthales@gmail.com"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")));
    }
}
