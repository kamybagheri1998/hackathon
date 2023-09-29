package com.bs14.plugins

import kotlinx.serialization.Serializable

@Serializable
data class ExposedDeviceLoan(
    val id: String,
    val loanId: String,
    val deviceId: String

)