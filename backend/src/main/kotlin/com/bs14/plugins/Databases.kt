package com.bs14.plugins

import DeviceLoanService
import DeviceService
import LoanService
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.github.oshai.kotlinlogging.KotlinLogging
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.sql.*
import java.util.*

private val LOG = KotlinLogging.logger {}

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
            LOG.info { "Test 1" }
            val email: String = call.request.queryParameters["email"]!!
            LOG.info { "Test 2" }
            val password: String = call.request.queryParameters["password"]!!
            LOG.info { "Test 3" }
            val passwordHash: Int = password.hashCode()
            LOG.info { "Test 4" }

            if (!userService.login(email, passwordHash)) {
                call.respond(HttpStatusCode.Unauthorized)
                return@get
            }

            LOG.info { "Test 5" }

            val token = JWT.create()
                .withAudience(jwtAudience)
                .withIssuer(jwtAudience)
                .withClaim("username", email)
                .withExpiresAt(Date(System.currentTimeMillis() + 60000))
                .sign(Algorithm.HMAC256(jwtSecret))

            call.respond(HttpStatusCode.OK, hashMapOf("token" to token))
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

            call.respond(HttpStatusCode.Created, hashMapOf("userId" to id))
        }

        authenticate {
            put("/user") {

            }

            delete("/user") {

            }
        }
    }
}
