package com.tehnovsky.task.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;

@OpenAPIDefinition(
        info = @Info(
                title = "REST account-service",
                description = "The service provides opportunities to replenish the user's balance, " +
                        "as well as to transfer money between users",
                summary = "The service provides opportunities to replenish the user's balance, " +
                        "as well as to transfer money between users",
                termsOfService = "T&C",
                contact = @Contact(
                        name = "Evgeny Savonkin",
                        url = "https://t.me/eugene_savonkin",
                        email = "evgenysavonkin@gmail.com"
                ),
                license = @License(
                        name = "Some license number"
                ),
                version = "v1"
        )
)
public class OpenApiConfig {
}
