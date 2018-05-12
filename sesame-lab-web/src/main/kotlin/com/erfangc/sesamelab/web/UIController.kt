package com.erfangc.sesamelab.web

import com.erfangc.sesamelab.shared.entities.Corpus
import com.erfangc.sesamelab.shared.repositories.CorpusRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

data class UIConfiguration(val corpuses: List<Corpus>)

@RestController
@RequestMapping("api/v1/ui-config")
class UIController(private val corpusRepository: CorpusRepository) {
    @GetMapping
    fun uiConfig(): UIConfiguration {
        val corpuses = corpusRepository.findAll().toList()
        return UIConfiguration(corpuses = corpuses)
    }
}