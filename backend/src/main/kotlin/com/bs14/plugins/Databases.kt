package com.bs14.plugins

import DeviceLoanService
import DeviceService
import LoanService
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.github.oshai.kotlinlogging.KLogger
import io.github.oshai.kotlinlogging.KotlinLogging
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*
import org.jetbrains.exposed.sql.Database
import java.util.*

private val LOG: KLogger = KotlinLogging.logger {}
private val DB_URL: String = System.getenv("HARDLOAN_DB_URL") ?: "127.0.0.1"

fun Application.configureDatabases() {
    val database = Database.connect(
        url = "jdbc:postgresql://$DB_URL:5432/postgres",
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
            val email: String = call.request.queryParameters["email"]!!
            val password: String = call.request.queryParameters["password"]!!
            val passwordHash: Int = password.hashCode()

            if (!userService.login(email, passwordHash)) {
                call.respond(HttpStatusCode.Unauthorized)
                return@get
            }

            val token = JWT.create()
                .withAudience(jwtAudience)
                .withIssuer(jwtAudience)
                .withClaim("username", email)
                .withExpiresAt(Date(System.currentTimeMillis() + 60000))
                .sign(Algorithm.HMAC256(jwtSecret))

            call.sessions.set("JWT", JwtSession("Bearer $token"))
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

            call.respond(HttpStatusCode.Created, hashMapOf("userId" to id))
        }

        get("/test1") {
            val jwt = call.sessions.get("JWT")
            LOG.info { jwt }
        }

        authenticate {
            put("/user") {

            }

            delete("/user") {

            }
        }
    }
}
