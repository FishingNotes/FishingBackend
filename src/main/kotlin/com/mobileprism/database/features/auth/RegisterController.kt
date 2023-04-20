package com.mobileprism.database.features.auth

import com.mobileprism.database.model.otps.OTPs
import com.mobileprism.database.model.tokens.Tokens
import com.mobileprism.database.model.users.Users
import com.mobileprism.database.receiveModel
import com.mobileprism.models.register.GoogleAuthRemote
import com.mobileprism.models.register.RegisterRemote
import com.mobileprism.models.register.RegisterRemoteResponse
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*

class RegisterController {

    suspend fun registerNewUser(call: ApplicationCall) {
        val registerRemote = call.receiveModel<RegisterRemote>()
        val userDTO = Users.getUserByEmail(registerRemote.email)

        when {
            userDTO != null -> {
                call.respond(HttpStatusCode.Conflict, "User with the same email is already registered")
            }

            else -> {
                val user = Users.createNewUser(registerRemote)
                val token = Tokens.createNewTokenForUser(user)

                // TODO: VerifyEmail
                call.respond(RegisterRemoteResponse(user = user.mapToUserResponse(), token.token))
            }
        }
    }

    suspend fun registerWithGoogle(call: ApplicationCall, registerRemote: GoogleAuthRemote) {
        val existedUser = Users.getUserByEmail(email = registerRemote.email)
        when {
            existedUser != null -> {
                LoginController().loginWithGoogle(call)
                // TODO:
//                call.respond(
//                    HttpStatusCode.Conflict,
//                    "User with the same email is already registered but google id is null"
//                )
            }
            else -> {
                val user = Users.createNewGoogleUser(registerRemote)
                val token = Tokens.createNewTokenForUser(user)

                call.respond(RegisterRemoteResponse(user = user.mapToUserResponse(), token.token))
            }
        }
    }
}