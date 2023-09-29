package com.bs14.plugins

import kotlinx.serialization.Serializable

@Serializable
data class ExposedDevice(
    val id: String,
    val type: Int,
    val donor: String?,
    val model: String,
    val serialNumber: String,

    )