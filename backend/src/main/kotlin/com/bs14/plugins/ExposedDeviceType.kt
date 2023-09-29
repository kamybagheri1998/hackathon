package com.bs14.plugins

import kotlinx.serialization.Serializable

@Serializable
data class ExposedDeviceType (
    val id: String,
    val name: String,

)