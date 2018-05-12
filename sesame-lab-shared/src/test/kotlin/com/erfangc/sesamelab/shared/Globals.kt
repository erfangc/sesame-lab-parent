package com.erfangc.sesamelab.shared

import com.fasterxml.jackson.databind.ObjectMapper

val objectMapper: ObjectMapper = ObjectMapper().findAndRegisterModules()