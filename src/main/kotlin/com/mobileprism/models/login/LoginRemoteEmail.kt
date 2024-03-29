package com.mobileprism.models.login

import com.mobileprism.database.model.users.UserResponse
import kotlinx.serialization.Serializable

@Serializable
data class LoginRemoteEmail(
    val email: String,
    val password: String
)
// TODO: Change login routing with emailLogin

@Serializable
data class LoginRemoteUsername(
    val username: String,
    val password: String
)

@Serializable
data class RestoreRemoteFind(
    val login: String,
)

@Serializable
data class RestoreRemoteConfirm(
    val login: String,
    val otp: Int,
)

@Serializable
data class RestoreRemoteReset(
    val login: String,
    val newPassword: String,
)

@Serializable
data class LoginRemoteResponse(
    val user: UserResponse,
    val token: String,
)
