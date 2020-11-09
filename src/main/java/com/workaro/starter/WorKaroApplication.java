package com.workaro.starter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan({"com.workaro"})
@EntityScan("com.workaro.model")
@EnableJpaRepositories("com.workaro.repository")
public class WorKaroApplication {

	public static void main(String[] args) {
		SpringApplication.run(WorKaroApplication.class, args);
	}

}
