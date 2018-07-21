package com.erfangc.sesamelab.worker.model

import com.erfangc.sesamelab.shared.NERModelService
import com.erfangc.sesamelab.shared.TrainNERModelRequest
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.slf4j.LoggerFactory
import org.springframework.amqp.rabbit.annotation.RabbitHandler
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component

@Component
@RabbitListener(queues = ["train-ner-model"])
class AMQPListener(private val nerModelService: NERModelService) {
    private val objectMapper = ObjectMapper().findAndRegisterModules()
    private val logger = LoggerFactory.getLogger(AMQPListener::class.java)
    @RabbitHandler
    fun train(message: String) {
        val request = objectMapper.readValue<TrainNERModelRequest>(message)
        try {
            nerModelService.train(request = request)
        } catch (e: Exception) {
            // we cannot let exceptions go unhandled that would cause RabbitMQ to keep attempting to redeliver
            logger.info("Failed to train a model, reason: ${e.message}")
        }
    }

}