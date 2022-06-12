package com.mobileprism.models.register

import kotlinx.serialization.Serializable

@Serializable
data class RegisterRemote(
    val login: String,
    val email: String,
    val password: String
)

@Serializable
data class RegisterRemoteResponse(
    val token: String,
)
