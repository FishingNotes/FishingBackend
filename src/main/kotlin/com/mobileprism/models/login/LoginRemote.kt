package com.mobileprism.models.login

import com.mobileprism.database.model.users.UserDTO
import kotlinx.serialization.Serializable

@Serializable
data class LoginRemote(
    val email: String,
    val password: String
)

@Serializable
data class LoginRemoteResponse(
    val token: String,
)
