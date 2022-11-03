package com.mobileprism.database.features.auth


import com.mobileprism.database.model.otps.OTPs
import com.mobileprism.database.model.users.Users
import com.mobileprism.database.model.utils.FishingCodes
import com.mobileprism.database.model.utils.FishingResponse
import com.mobileprism.database.model.utils.PasswordBCrypt
import com.mobileprism.database.receiveModel
import com.mobileprism.isEmail
import com.mobileprism.models.login.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import org.apache.commons.mail.DefaultAuthenticator
import org.apache.commons.mail.SimpleEmail
import org.jetbrains.exposed.sql.transactions.transaction

class RestoreController {

    private fun sendEmail(to: String, code: Int) {
        kotlin.runCatching {
            val email = SimpleEmail()
            email.hostName = "smtp.googlemail.com"
            email.setSmtpPort(465)
            email.setAuthenticator(
                DefaultAuthenticator("FishingNotesRU@gmail.com", "nfewwefdclgrgzzb")
            )
            email.isSSLOnConnect = true
            email.setFrom("fishingnotesru@gmail.com")
            email.subject = "FishingNotes password reset"
            email.setMsg("Your OTP is: $code \nIf you need any help, please contact us.")
            email.addTo(to)
            email.send()
        }
    }

    suspend fun searchForAccount(call: ApplicationCall) {
        val restoreRemote = call.receiveModel<RestoreRemoteFind>()

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

        val otpDTO = OTPs.createNewOtpForUser(userDTO)
        sendEmail(userDTO.email, otpDTO.otp)

        call.respond(
            FishingResponse(
                success = true,
                fishingCode = FishingCodes.SUCCESS,
                httpCode = HttpStatusCode.OK.value
            )
        )
        return
    }

    suspend fun confirmOTP(call: ApplicationCall) {
        val remoteConfirm = call.receiveModel<RestoreRemoteConfirm>()

        val userDTO =
            if (remoteConfirm.login.isEmail) Users.getUserByEmail(remoteConfirm.login)
            else Users.getUserByLogin(remoteConfirm.login)

        // TODO: Check for retries (unlimited for now)

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

        val otpDTO = OTPs.findLastUserOTP(userDTO)

        if (otpDTO?.otp == remoteConfirm.otp) {
            if (otpDTO.isActive) {
                call.respond(
                    FishingResponse(
                        success = true,
                        fishingCode = FishingCodes.SUCCESS,
                        httpCode = HttpStatusCode.OK.value
                    )
                )
            } else {
                call.respond(
                    FishingResponse(
                        success = false,
                        fishingCode = FishingCodes.OTP_ATTEMPTS_EXCEEDED,
                        httpCode = HttpStatusCode.Accepted.value
                    )
                )
            }
        } else {
            call.respond(
                FishingResponse(
                    success = false,
                    fishingCode = FishingCodes.OTP_NOT_FOUND,
                    httpCode = HttpStatusCode.Accepted.value
                )
            )
        }
    }

    suspend fun restorePassword(call: ApplicationCall) {
        val restoreRemote = call.receiveModel<RestoreRemoteReset>()

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