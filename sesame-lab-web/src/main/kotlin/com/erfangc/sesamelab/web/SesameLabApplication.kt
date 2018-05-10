package com.erfangc.sesamelab.web

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@SpringBootApplication
@ComponentScan("com.erfangc.sesamelab")
@EntityScan("com.erfangc.sesamelab")
@EnableJpaRepositories("com.erfangc.sesamelab")
class SesameLabApplication

fun main(args: Array<String>) {
    runApplication<SesameLabApplication>(*args)
}

@RestController
class HeartBeatController {
    @GetMapping("/")
    fun get(): String {
        return "ok"
    }
}
