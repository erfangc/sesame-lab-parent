package com.erfangc.sesamelab.worker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan("com.erfangc.sesamelab")
@EntityScan("com.erfangc.sesamelab")
@EnableJpaRepositories("com.erfangc.sesamelab")
public class SesameLabWorkerApplication {

    public static void main(String[] args) {
        final SpringApplication springApplication = new SpringApplication(SesameLabWorkerApplication.class);
        springApplication.setWebApplicationType(WebApplicationType.NONE);
        springApplication.run(args);
    }
}
