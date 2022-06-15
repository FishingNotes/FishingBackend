package com.mobileprism.models.login

import com.mobileprism.database.model.users.UserDTO
import com.mobileprism.database.model.users.UserResponse
import kotlinx.serialization.Serializable

/*@Serializable
data class EmailLoginRemote(
    val email: String,
    val password: String
)

@Serializable
data class UsernameLoginRemote(
    val login: String,
    val password: String
)*/

@Serializable
data class LoginRemote(
    val loginOrEmail: String,
    val password: String
)

@Serializable
data class LoginRemoteResponse(
    val user: UserResponse,
    val token: String,
)
