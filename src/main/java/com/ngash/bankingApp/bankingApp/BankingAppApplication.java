package com.ngash.bankingApp.bankingApp;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "The Java Academy Bank App",
				description = "Backend Rest APIs for TJA Bank",
				version = "v1.0",
				contact = @Contact(
						name = "Amos Kimani",
						email = "kimaniamos82@gmail.com",
						url = "https://github.com/Ngash01"
				),
				license = @License(
						name = "The Java Academy",
						url = "https://github.com/musdon/tja_bank"
				)
		),
		externalDocs = @ExternalDocumentation(
				description = "The Java Academy Bank App Documentation",
				url = "https://github.com/musdon/tja_bank"
		)
)
public class BankingAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(BankingAppApplication.class, args);
	}

}
