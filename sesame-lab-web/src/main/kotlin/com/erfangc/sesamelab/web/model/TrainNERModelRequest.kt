package com.erfangc.sesamelab.web.model

import com.erfangc.sesamelab.web.user.User

data class TrainNERModelRequest(val user: User, val name: String, val description: String?, val corpusID: Long, val modifiedAfter: Long)