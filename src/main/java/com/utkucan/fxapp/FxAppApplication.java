package com.utkucan.fxapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class FxAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(FxAppApplication.class, args);
	}

}
