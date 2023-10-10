package com.bs14.plugins

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.http.auth.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*

const val jwtAudience: String = "heartware_user"
const val jwtDomain  : String = "http://127.0.0.1/"
const val jwtRealm   : String = "heartware_realm"
const val jwtSecret  : String = "27e58cf8036a0bec7842bcfa741b8abea436352aa23a15acc95b4ab8da0e783b138f9641e948f42c21a95df663584670234b0f09478c360e3159ed694417e4b49477389f789cd0365171d177ece78819808cca8e244f8e29a4fa4db791dde34a9d899e79453e67d26b79c75bf3b0c68997edd025f45a94e97f240a9f9e246ef9"

fun Application.configureAuthentication() {
    authentication {
        jwt("auth-jwt") {
            realm = jwtRealm
            verifier(
                JWT
                    .require(Algorithm.HMAC256(jwtSecret))
                    .withAudience(jwtAudience)
                    .withIssuer(jwtDomain)
                    .build()
            )

            authHeader { call ->
                val authCookie = call.request.cookies["JWT"] ?: return@authHeader null
                if (authCookie.isBlank()) {
                    return@authHeader null
                }

                try {
                    parseAuthorizationHeader(authCookie)
                } catch (ex: Exception) {
                    null
                }
            }

            validate { credential ->
                if (credential.payload.audience.contains(jwtAudience)) JWTPrincipal(credential.payload) else null
            }

            challenge {_, _ ->
                call.respondRedirect("/sign-in.html")
            }
        }
    }
}