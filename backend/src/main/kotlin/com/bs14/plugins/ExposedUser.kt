package com.bs14.plugins

import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class ExposedUser(
        val id: String,
        val email: String,
        val institute: String,
        val activated: Boolean,
        val password: String
)