package com.erfangc.sesamelab.web

import com.erfangc.sesamelab.shared.entities.Corpus
import org.slf4j.LoggerFactory
import org.springframework.data.rest.core.annotation.HandleBeforeCreate
import org.springframework.data.rest.core.annotation.RepositoryEventHandler
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service

@Service
@RepositoryEventHandler
class CorpusEventHandler(private val userService: UserService) {

    private val logger = LoggerFactory.getLogger(CorpusEventHandler::class.java)

    @HandleBeforeCreate
    fun handleBeforeCreate(corpus: Corpus) {
        val authentication = SecurityContextHolder.getContext().authentication
        if (!authentication.isAuthenticated) {
            throw RuntimeException("cannot create a corpus with an unauthenticated principal")
        }
        val (id) = userService.getUserFromAuthenticatedPrincipal(authentication)
        logger.info("Inject user id {} into corpus {}", id, corpus.title)
        corpus.userID = id
    }

}
