package com.utkucan.fxapp;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@OpenAPIDefinition(
		info = @Info(
				title = "Foreign Exchange API",
				version = "1.0",
				description = "Provides currency conversion and exchange rate services"
		)
)
@SpringBootApplication
@EnableScheduling
public class FxAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(FxAppApplication.class, args);
	}

}
