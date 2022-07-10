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

    private suspend fun loginWithEmail(call: ApplicationCall, loginRemote: LoginRemote) {
        val userDTO = Users.getUserByEmail(loginRemote.loginOrEmail)
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
                call.respond(HttpStatusCode.ExpectationFailed, "Invalid email/password")
            }
        }
    }

    private suspend fun loginWithUsername(call: ApplicationCall, loginRemote: LoginRemote) {
        val userDTO = Users.getUserByLogin(loginRemote.loginOrEmail)
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
                call.respond(HttpStatusCode.ExpectationFailed, "Invalid login/password")
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

    suspend fun login(call: ApplicationCall) {
        val loginRemote = call.receiveModel<LoginRemote>()
        when (loginRemote.loginOrEmail.isEmail) {
            true -> {
                loginWithEmail(call, loginRemote)
            }
            else -> loginWithUsername(call, loginRemote)
        }
    }
}