package com.bs14.plugins

import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.http.content.*
import io.ktor.server.plugins.autohead.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.io.File

private val privateFiles = setOf("")

fun Application.configureRouting() {
    install(AutoHeadResponse)

    routing {
        staticFiles("/", File("./www/pages"), "index.html") {
            extensions("html")
            exclude {
                file -> privateFiles.contains(file.name)
            }
        }

        staticFiles("/assets", File("./www/assets")) {
            extensions("png")
        }

        staticFiles("/styles", File("./www/styles")) {
            extensions("css", "min.css", "rtl.css")
        }

        staticFiles("/scripts", File("./www/scripts")) {
            extensions("js")
        }

        authenticate {
            staticFiles("/", File("./www/pages")) {
                extensions("html")
                exclude {
                        file -> !privateFiles.contains(file.name)
                }
            }
        }
    }
}
