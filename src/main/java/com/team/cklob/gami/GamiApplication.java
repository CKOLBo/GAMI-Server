package com.team.cklob.gami;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class GamiApplication {

	public static void main(String[] args) {
		SpringApplication.run(GamiApplication.class, args);
	}

}
