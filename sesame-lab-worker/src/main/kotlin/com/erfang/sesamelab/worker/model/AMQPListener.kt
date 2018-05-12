package com.erfang.sesamelab.worker.model

import com.erfangc.sesamelab.shared.TrainNERModelRequest
import org.springframework.amqp.rabbit.annotation.RabbitHandler
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component

@Component
@RabbitListener(queues = ["train-ner-model"])
class AMQPListener(private val nerModelService: NERModelService) {

    @RabbitHandler
    fun train(request: TrainNERModelRequest) {
        nerModelService.train(request = request)
    }

}