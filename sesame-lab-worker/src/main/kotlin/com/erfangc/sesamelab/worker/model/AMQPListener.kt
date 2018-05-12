package com.erfangc.sesamelab.worker.model

import com.erfangc.sesamelab.shared.TrainNERModelRequest
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.springframework.amqp.rabbit.annotation.RabbitHandler
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component

@Component
@RabbitListener(queues = ["train-ner-model"])
class AMQPListener(private val nerModelService: NERModelService) {
    private val objectMapper = ObjectMapper().findAndRegisterModules()
    @RabbitHandler
    fun train(message: String) {
        val request = objectMapper.readValue<TrainNERModelRequest>(message)
        nerModelService.train(request = request)
    }

}