package com.tcmp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
public class PortafolioApplication {

	public static void main(String[] args) {
		//SpringApplication.run(PortafolioApplication.class, args);
		
		SpringApplicationBuilder builder = new SpringApplicationBuilder(PortafolioApplication.class);

		builder.headless(false);

		ConfigurableApplicationContext context = builder.run(args);
	}

}
