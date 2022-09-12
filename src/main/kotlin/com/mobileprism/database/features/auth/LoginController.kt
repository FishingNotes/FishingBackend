package com.mobileprism.database.features.auth

import at.favre.lib.crypto.bcrypt.BCrypt
import com.mobileprism.database.model.tokens.Tokens
import com.mobileprism.database.model.users.Users
import com.mobileprism.database.receiveModel
import com.mobileprism.isEmail
import com.mobileprism.models.login.*
import com.mobileprism.models.register.GoogleAuthRemote
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*

class LoginController {

    suspend fun loginWithEmail(call: ApplicationCall) {
        val loginRemoteEmail = call.receiveModel<LoginRemoteEmail>()
        val userDTO = Users.getUserByEmail(loginRemoteEmail.email)
        if (userDTO == null) {
            call.respond(HttpStatusCode.NotFound)
            return
        }

        when {
            BCrypt.verifyer().verify(loginRemoteEmail.password.toCharArray(), userDTO.password).verified -> {
                val newToken = Tokens.createNewTokenForUser(userDTO)
                call.respond(LoginRemoteResponse(userDTO.mapToUserResponse(), newToken.token))
            }
            else -> {
                call.respond(HttpStatusCode.ExpectationFailed, "Invalid email/password")
            }
        }
    }

    suspend fun loginWithUsername(call: ApplicationCall) {
        val loginRemote = call.receiveModel<LoginRemoteUsername>()

        val userDTO = Users.getUserByLogin(loginRemote.username)
        if (userDTO == null) {
            call.respond(HttpStatusCode.NotFound)
            return
        }

        when {
            BCrypt.verifyer().verify(loginRemote.password.toCharArray(), userDTO.password).verified -> {
                val newToken = Tokens.createNewTokenForUser(userDTO)
                call.respond(LoginRemoteResponse(userDTO.mapToUserResponse(), newToken.token))
            }
            else -> {
                call.respond(HttpStatusCode.ExpectationFailed, "Invalid username/password")
            }
        }
    }

    suspend fun loginWithGoogle(call: ApplicationCall) {
        val googleLoginRemote = call.receiveModel<GoogleAuthRemote>()
        val userDTO = Users.getUserByGoogleAuthId(googleLoginRemote.googleAuthId)

        when {
            userDTO != null -> {
                val newToken = Tokens.createNewTokenForUser(userDTO)
                call.respond(LoginRemoteResponse(userDTO.mapToUserResponse(), newToken.token))
            }
            else -> {
                RegisterController().registerWithGoogle(call, googleLoginRemote)
            }
        }

    }

}