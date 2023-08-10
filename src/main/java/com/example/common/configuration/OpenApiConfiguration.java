package com.example.common.configuration;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
    info = @Info(title = "shop", version = "v0.0.1", description = "Spring Base"),
    externalDocs = @ExternalDocumentation(url = "https://github.com/tw-beach-team2/spring_base"))
@Configuration
class OpenApiConfiguration {}
