package com.bs14

import com.bs14.plugins.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.github.oshai.kotlinlogging.KotlinLogging

private val LOG = KotlinLogging.logger {}

fun main() {
    LOG.info { "Starting webapp..." }

    val server = embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)

    server.addShutdownHook {
        LOG.info { "Webapp shutting down..." }
    }

    LOG.info { "Starting server" }
    server.start(wait = true)
}

fun Application.module() {
    configureAuthentication()
    configureSecurity()
    configureMonitoring()
    configureSerialization()
    configureDatabases()
    configureRouting()
}
