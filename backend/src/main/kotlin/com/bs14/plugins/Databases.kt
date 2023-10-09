package com.bs14.plugins

import DeviceLoanService
import DeviceService
import LoanService
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.sql.*
import java.util.*

fun Application.configureDatabases() {
    val database = Database.connect(
        url = "jdbc:postgresql://database:5432/postgres",
        user = "postgres",
        driver = "org.postgresql.Driver",
        password = "postgres"
    )

    val userService = UserService(database)
    val loanService = LoanService(database)
    val deviceService = DeviceService(database)
    val deviceLoanService = DeviceLoanService(database)

    routing {
        get("/user") {
            val email = call.request.queryParameters["email"]!!
            val passwordHash = call.request.queryParameters["password"]!!.hashCode()

            if (!userService.login(email, passwordHash)) {
                call.respond(HttpStatusCode.Unauthorized)
                return@get
            }

            call.respond(HttpStatusCode.OK)
        }

        post("/user") {
            val email = call.request.queryParameters["email"]
            if (email.isNullOrBlank()) {
                call.respond(HttpStatusCode.BadRequest, "message email is empty")
                return@post
            }

            val institute = call.request.queryParameters["institute"]!!
            val password = call.request.queryParameters["password"]!!
            val id = userService.create(email, institute, password)

            val token = JWT.create()
                .withAudience(jwtAudience)
                .withIssuer(jwtAudience)
                .withClaim("username", email)
                .withExpiresAt(Date(System.currentTimeMillis() + 60000))
                .sign(Algorithm.HMAC256(jwtSecret))

            call.respond(HttpStatusCode.Created, hashMapOf("token" to token, "userId" to id))
        }

        authenticate {
            put("/user") {

            }

            delete("/user") {

            }
        }
    }
}
