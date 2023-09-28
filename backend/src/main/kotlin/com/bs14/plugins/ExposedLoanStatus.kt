package com.bs14.plugins

import kotlinx.serialization.Serializable

@Serializable
data class ExposedLoanStatus(
    val id: Int,
    val name: String
)