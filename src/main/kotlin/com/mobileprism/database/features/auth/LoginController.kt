package com.mobileprism.database.features.auth

import com.mobileprism.database.model.tokens.Tokens
import com.mobileprism.database.model.users.Users
import com.mobileprism.database.model.utils.FishingCodes
import com.mobileprism.database.model.utils.FishingResponse
import com.mobileprism.database.model.utils.PasswordBCrypt
import com.mobileprism.database.receiveModel
import com.mobileprism.isEmail
import com.mobileprism.models.login.*
import com.mobileprism.models.register.GoogleAuthRemote
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import org.jetbrains.exposed.sql.transactions.transaction

class LoginController() {

    suspend fun loginWithEmail(call: ApplicationCall) {
        val loginRemoteEmail = call.receiveModel<LoginRemoteEmail>()
        val userDTO = Users.getUserByEmail(loginRemoteEmail.email)
        if (userDTO == null) {
            call.respond(
                FishingResponse(
                    success = false,
                    fishingCode = FishingCodes.INVALID_CREDENTIALS,
                    httpCode = HttpStatusCode.ExpectationFailed.value
                )
            )
            return
        }

        when {
            PasswordBCrypt.verifyPasswords(loginRemoteEmail.password.toCharArray(), userDTO.password) -> {
                val newToken = Tokens.createNewTokenForUser(userDTO)
                call.respond(LoginRemoteResponse(userDTO.mapToUserResponse(), newToken.token))
            }

            else -> {
                call.respond(
                    FishingResponse(
                        success = false,
                        fishingCode = FishingCodes.INVALID_CREDENTIALS,
                        httpCode = HttpStatusCode.ExpectationFailed.value
                    )
                )
            }
        }
    }

    suspend fun loginWithUsername(call: ApplicationCall) {
        val loginRemote = call.receiveModel<LoginRemoteUsername>()

        val userDTO = Users.getUserByLogin(loginRemote.username)
        if (userDTO == null) {
            call.respond(
                FishingResponse(
                    success = false,
                    fishingCode = FishingCodes.USERNAME_NOT_FOUND,
                    httpCode = HttpStatusCode.NotFound.value
                )
            )
            return
        }

        when {
            PasswordBCrypt.verifyPasswords(loginRemote.password.toCharArray(), userDTO.password) -> {
                val newToken = Tokens.createNewTokenForUser(userDTO)
                call.respond(LoginRemoteResponse(userDTO.mapToUserResponse(), newToken.token))
            }

            else -> {
                call.respond(
                    FishingResponse(
                        success = false,
                        fishingCode = FishingCodes.INVALID_CREDENTIALS,
                        httpCode = HttpStatusCode.ExpectationFailed.value
                    ),
                )
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

    suspend fun restorePassword(call: ApplicationCall) {
        val restoreRemote = call.receiveModel<LoginRemoteRestore>()

        val userDTO =
            if (restoreRemote.login.isEmail) Users.getUserByEmail(restoreRemote.login)
            else Users.getUserByLogin(restoreRemote.login)

        if (userDTO == null) {
            call.respond(
                FishingResponse(
                    success = false,
                    fishingCode = FishingCodes.USERNAME_NOT_FOUND,
                    httpCode = HttpStatusCode.Accepted.value
                )
            )
            return
        }

        //Set new password for user
        transaction { userDTO.password = PasswordBCrypt.encrypt(restoreRemote.newPassword.toCharArray()) }
        call.respond(
            FishingResponse(
                success = true,
                fishingCode = FishingCodes.SUCCESS,
                httpCode = HttpStatusCode.OK.value
            )
        )
        return

    }

}