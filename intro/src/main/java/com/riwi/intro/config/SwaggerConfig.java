package com.riwi.intro.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "API de Gestión de Eventify",
                version = "1.0",
                description = "Documentación detallada de los endpoints para la gestión de eventos y lugares.",
                contact = @Contact(name = "Soporte Riwi", email = "soporte@riwi.io")
        )
)
public class SwaggerConfig {
    // Aquí puedes añadir configuraciones adicionales como seguridad JWT
}
