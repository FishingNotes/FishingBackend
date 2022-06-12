package com.mobileprism.database.features.auth

import com.mobileprism.database.model.tokens.Tokens
import com.mobileprism.database.model.users.Users
import com.mobileprism.models.register.RegisterRemote
import com.mobileprism.models.register.RegisterRemoteResponse
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import java.util.UUID

class RegisterController {

    suspend fun registerNewUser(call: ApplicationCall) {
        val registerRemote = kotlin.runCatching {
            call.receiveOrNull<RegisterRemote>()
        }.onFailure {
            call.respond(HttpStatusCode.BadRequest, it.message ?: "")
        }.getOrNull()

        registerRemote?.let {
            val userDTOlogin = Users.getUserByLogin(registerRemote.login)?.login
            val userDTOemail = Users.getUserByLogin(registerRemote.login)?.email

            when {
                userDTOlogin == registerRemote.login -> {
                    call.respond(HttpStatusCode.Conflict, "User with the same login is already registered")
                }
                userDTOemail == registerRemote.email -> {
                    call.respond(HttpStatusCode.Conflict, "User with the same email is already registered")
                }
                else -> {
                    val user = Users.createNewUser(registerRemote)
                    val token = Tokens.createNewTokenForUser(user)

                    call.respond(RegisterRemoteResponse(token.token))

                }
            }
        }


    }
}