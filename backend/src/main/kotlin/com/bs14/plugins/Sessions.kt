package com.bs14.plugins

import io.github.oshai.kotlinlogging.KotlinLogging
import io.ktor.server.application.*
import io.ktor.server.sessions.*
import kotlin.collections.set

private val LOG = KotlinLogging.logger {}

data class JwtSession(val value: String = "")

fun Application.configureSessions() {
    LOG.info { "Configuring sessions..." }

    install(Sessions) {
        cookie<JwtSession>("JWT") {
            cookie.path = "/"
            cookie.maxAgeInSeconds = 60000
        }
    }
}
