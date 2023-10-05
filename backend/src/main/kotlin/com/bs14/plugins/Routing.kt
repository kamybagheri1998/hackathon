package com.bs14.plugins

import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.plugins.autohead.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.io.File

fun Application.configureRouting() {
    install(AutoHeadResponse)

    routing {
        get("/test") {
            call.respondText { "Test" }
        }

        staticFiles("/", File("./www/pages"), "index.html")
        staticFiles("/assets", File("./www/assets"))
        staticFiles("/styles", File("./www/styles"))
        staticFiles("/scripts", File("./www/scripts"))
    }
}
