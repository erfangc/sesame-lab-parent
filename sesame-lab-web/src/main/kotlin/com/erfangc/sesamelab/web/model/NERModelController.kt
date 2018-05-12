package com.erfangc.sesamelab.web.model

import com.erfangc.sesamelab.shared.TrainNERModelRequest
import com.erfangc.sesamelab.web.user.UserService
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.security.Principal

@CrossOrigin
@RestController
@RequestMapping("api/v1/ner")
class ModelController(private val userService: UserService,
                      private val rabbitTemplate: RabbitTemplate) {

    @PostMapping("train")
    fun train(@RequestParam corpusID: Long,
              @RequestParam(required = false) name: String?,
              @RequestParam(required = false) description: String?,
              @RequestParam(required = false) modifiedAfter: Long?,
              principal: Principal?): ResponseEntity<Any> {
        val user = userService.getUserFromAuthenticatedPrincipal(principal)
        val request = TrainNERModelRequest(
                user = user,
                name = name ?: "No Name",
                description = description ?: "No Description",
                corpusID = corpusID,
                modifiedAfter = modifiedAfter ?: 0)
        rabbitTemplate.convertAndSend("train-ner-model", request)
        return ResponseEntity.ok("")
    }

    @GetMapping("{modelFilename}/run")
    fun run(@PathVariable modelFilename: String,
            @RequestParam sentence: String): String {
        return "TODO"
    }

}