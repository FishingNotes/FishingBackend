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
            /*userDTOlogin == registerRemote.login -> {
                call.respond(HttpStatusCode.Conflict, "User with the same login is already registered")
            }*/
            userDTO != null -> {
                call.respond(HttpStatusCode.Conflict, "User with the same email is already registered")
            }
            else -> {
                val user = Users.createNewUser(registerRemote)
                val token = Tokens.createNewTokenForUser(user)

                call.respond(RegisterRemoteResponse(user = user.mapToUserResponse(), token.token))
            }
        }
    }

    suspend fun registerWithGoogle(call: ApplicationCall, registerRemote: GoogleAuthRemote) {

        val userDTOgoogleAuthId = Users.getUserByGoogleAuthId(googleAuthId = registerRemote.googleAuthId)?.googleAuthId
        val userDTOemail = Users.getUserByEmail(email = registerRemote.email)

        when {
            userDTOgoogleAuthId == registerRemote.googleAuthId -> {
                //call.respond(HttpStatusCode.Conflict, "User with the same login is already registered")
                LoginController().loginWithGoogle(call)
            }
            userDTOemail != null -> {
                call.respond(HttpStatusCode.Conflict, "User with the same email is already registered but google id is null")
            }
            else -> {
                val user = Users.createNewGoogleUser(registerRemote)
                val token = Tokens.createNewTokenForUser(user)

                call.respond(RegisterRemoteResponse(user = user.mapToUserResponse(), token.token))
            }
        }
    }
}