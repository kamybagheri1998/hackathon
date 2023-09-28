package com.bs14.plugins

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class ExposedLoan(
    val id: String,
    val userid: String,
    val createDate: LocalDateTime,
    val startDate: LocalDateTime?,
    val endDate: LocalDateTime?,
    val status: Int
)