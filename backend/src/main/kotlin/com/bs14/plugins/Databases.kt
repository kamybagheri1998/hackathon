package com.bs14.plugins

import DeviceLoanService
import DeviceService
import LoanService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.sql.*
import java.util.*

fun Application.configureDatabases() {
    val database = Database.connect(
        url = "jdbc:postgresql://127.0.0.1:5432/postgres",
        user = "postgres",
        driver = "org.postgresql.Driver",
        password = "postgres"
    )
    val userService = UserService(database)
    val loanService = LoanService(database)
    val deviceService = DeviceService(database)
    val deviceLoanService = DeviceLoanService(database)
    routing {
        // Create user
        post("/user") {
            val email = call.request.queryParameters["email"]
            val institute = call.request.queryParameters["institute"]
            val password = call.request.queryParameters["password"]
            val id = userService.create(email.toString(),institute.toString(),password.toString())
            call.respond(HttpStatusCode.Created, id)
        }
        /*
        // Read user
        get("/users/{id}") {
            val id = call.parameters["id"]?.toInt() ?: throw IllegalArgumentException("Invalid ID")
            val user = userService.read(id)
            if (user != null) {
                call.respond(HttpStatusCode.OK, user)
            } else {
                call.respond(HttpStatusCode.NotFound)
            }
        }
        // Update user
        put("/users/{id}") {
            val id = call.parameters["id"]?.toInt() ?: throw IllegalArgumentException("Invalid ID")
            val user = call.receive<ExposedUser>()
            userService.update(id, user)
            call.respond(HttpStatusCode.OK)
        }
        // Delete user
        delete("/users/{id}") {
            val id = call.parameters["id"]?.toInt() ?: throw IllegalArgumentException("Invalid ID")
            userService.delete(id)
            call.respond(HttpStatusCode.OK)
        }*/
    }
}
