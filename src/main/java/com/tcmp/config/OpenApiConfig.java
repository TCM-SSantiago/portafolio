package com.tcmp.config;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@Configuration
@OpenAPIDefinition(
          info = @Info(
          title = "Portfolio Kosmos",
          description = "Servicio Portfolio Kosmos - Banco Base",
          version = "v1.0",
          contact = @Contact(
            name = "TCM Partners",
            url = "https://www.tcmpartners.com",
            email = "soporte@tcmpartners.com"
          ),
          license = @License(
            name = "MIT Licence",
            url = "https://github.com/thombergs/code-examples/blob/master/LICENSE"
          ))
          
        )
@SecurityRequirement(name = "BasicAuth")

public class OpenApiConfig {
	
}
