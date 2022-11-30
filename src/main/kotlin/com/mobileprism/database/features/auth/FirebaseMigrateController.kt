package com.mobileprism.database.features.auth


import com.mobileprism.database.features.catches.NewCatchRemote
import com.mobileprism.database.features.markers.NewMarkerRemote
import com.mobileprism.database.model.firebase_restoration.FirebaseRestoration
import com.mobileprism.database.model.firebase_restoration.FishingFirebaseUser
import com.mobileprism.database.model.firebase_restoration.UserCatch
import com.mobileprism.database.model.firebase_restoration.UserMapMarker
import com.mobileprism.database.model.users.UserDTO
import com.mobileprism.database.model.users.Users
import com.mobileprism.database.model.utils.FishingCodes
import com.mobileprism.database.model.utils.FishingResponse
import com.mobileprism.database.model.utils.PasswordBCrypt
import com.mobileprism.database.model.utils.toLocalDateTime
import com.mobileprism.database.receiveModel
import com.mobileprism.database.validateTokenWithUser
import com.mobileprism.isEmail
import com.mobileprism.models.login.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import org.jetbrains.exposed.sql.transactions.transaction


class FirebaseMigrateController {

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

    suspend fun migrate(call: ApplicationCall) {
        val firebaseRestorationRemote = call.receiveModel<FirebaseRestoration>()
        validateTokenWithUser(call) { token, user ->
            restoreUserFromFirebase(user, firebaseRestorationRemote.firebaseUser)
            restoreMarkersAndCatchesFromFirebase(
                user,
                firebaseRestorationRemote
            )
        }
    }

    private fun restoreMarkersAndCatchesFromFirebase(
        user: UserDTO,
        restoration: FirebaseRestoration,
    ) {
        restoration.userMarkers.forEach { firebaseMarker ->
            val marker = toNewMarkerRemote(firebaseMarker)
            val catches = restoration.userCatches.filter { it.markerId == firebaseMarker.id }.map {
                toNewCatchRemote(it)
            }

        }
        with(user) {
            googleAuthId = firebaseUser.photoUrl
            val name = firebaseUser.displayName.split(" ")
            when (name.size) {
                1 -> firstName = name.first()
                2 -> {
                    firstName = name.first()
                    lastName = name.last()
                }
            }
        }
    }

    private fun toNewCatchRemote(catch: UserCatch) =
        with(catch) {
            NewCatchRemote(

            )
        }


    private fun toNewMarkerRemote(firebaseMarker: UserMapMarker) =
        with(firebaseMarker) {
            NewMarkerRemote(
                latitude = latitude,
                longitude = longitude,
                title = title,
                description = description,
                markerColor = markerColor,
                datetimeCreated = dateOfCreation.toLocalDateTime().toString(),
                visible = visible,
                private = public.not()
            )
        }

    private fun restoreUserFromFirebase(user: UserDTO, firebaseUser: FishingFirebaseUser) {
        transaction {
            with(user) {
                googleAuthId = firebaseUser.photoUrl
                val name = firebaseUser.displayName.split(" ")
                when (name.size) {
                    1 -> firstName = name.first()
                    2 -> {
                        firstName = name.first()
                        lastName = name.last()
                    }
                }
            }
        }
    }


}