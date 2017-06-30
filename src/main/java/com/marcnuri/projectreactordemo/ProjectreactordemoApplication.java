package com.marcnuri.projectreactordemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({
	"com.marcnuri.projectreactordemo",
	"com.marcnuri.projectreactordemo.rest"})
public class ProjectreactordemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjectreactordemoApplication.class, args);
	}

}
