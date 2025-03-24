package com.beratoztas.starter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ECommerceApplicationStarter {

	public static void main(String[] args) {
		SpringApplication.run(ECommerceApplicationStarter.class, args);
		System.out.println("Hello E-Commerce");
	}

}
