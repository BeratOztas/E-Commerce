package com.beratoztas.starter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = {"com.beratoztas"})
@EntityScan(basePackages = {"com.beratoztas"})
@ComponentScan(basePackages = {"com.beratoztas"})
public class ECommerceApplicationStarter {

	public static void main(String[] args) {
		SpringApplication.run(ECommerceApplicationStarter.class, args);
		System.out.println("Hello E-Commerce");
	}

}
