package com.example.jjangushrine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class JjanguShrineApplication {

	public static void main(String[] args) {
		SpringApplication.run(JjanguShrineApplication.class, args);
	}

}
