package com.erfangc.sesamelab.shared

import com.erfangc.sesamelab.shared.User

data class TrainNERModelRequest(val user: User, val name: String, val description: String?, val corpusID: Long, val modifiedAfter: Long)