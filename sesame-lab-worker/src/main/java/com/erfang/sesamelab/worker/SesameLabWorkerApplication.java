package com.erfang.sesamelab.worker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.erfangc.sesamelab")
public class SesameLabWorkerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SesameLabWorkerApplication.class, args);
	}
}
