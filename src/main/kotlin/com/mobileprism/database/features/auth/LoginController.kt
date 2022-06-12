package com.mobileprism.database.features.auth

import com.mobileprism.database.model.tokens.Tokens
import com.mobileprism.database.model.users.Users
import com.mobileprism.models.login.LoginRemote
import com.mobileprism.models.login.LoginRemoteResponse
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*

class LoginController {

    suspend fun loginUser(call: ApplicationCall) {
        val loginRemote = call.receiveOrNull<LoginRemote>()

        if (loginRemote == null) {
            call.respond(HttpStatusCode.BadRequest)
            return
        }

        val userDTO = Users.getUserByEmail(loginRemote.email)

        if (userDTO == null) {
            call.respond(HttpStatusCode.NotFound)
            return
        }

        when (userDTO.password) {
            loginRemote.password -> {
                val newToken = Tokens.createNewTokenForUser(userDTO)
                call.respond(LoginRemoteResponse(newToken.token))
            }
            else -> {
                call.respond(HttpStatusCode.ExpectationFailed)
            }
        }
    }
}