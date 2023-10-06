package com.bs14

import com.bs14.plugins.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlin.test.*

class ApplicationTest {
    @Test
    fun testRoot() = testApplication {
        application {

            configureSecurity()
            configureMonitoring()
            configureSerialization()
            configureDatabases()
            configureRouting()
        }
        client.post("/user?institute=t&email=ddddd@ffffd.de&password=1234").apply {
            assertEquals(HttpStatusCode.Created, status)
        }
    }
}
