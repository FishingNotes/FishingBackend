package com.mobileprism.models.register

import com.mobileprism.database.model.users.UserResponse
import kotlinx.serialization.Serializable

@Serializable
data class RegisterRemote(
    val email: String,
    val password: String
)

@Serializable
data class GoogleAuthRemote(
    val email: String,
    val googleAuthId: String,
    val googleAuthIdToken: String,
    val firebaseAuthId: String?,
)


@Serializable
data class RegisterRemoteResponse(
    val user: UserResponse,
    val token: String,
)
