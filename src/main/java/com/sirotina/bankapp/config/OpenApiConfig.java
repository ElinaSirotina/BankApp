package com.sirotina.bankapp.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(
                title = "Bank Api",
                description = "Bank application for TelRan school", version = "1.0.0",
                contact = @Contact(
                        name = "Sirotina Elina"
                )
        )
)
public class OpenApiConfig {

}
