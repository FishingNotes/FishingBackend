package com.mobileprism.database.model.users

import com.mobileprism.database.model.firebase_restoration.FishingFirebaseUser
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.UUID

class UserDTO(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<UserDTO>(Users)

    internal var login by Users.login
    internal var password by Users.password
    internal var firstName by Users.firstName
    internal var lastName by Users.lastName
    internal var email by Users.email
    internal var dateTimeRegistered by Users.dateTimeRegistered
    internal var googleAuthId by Users.googleAuthId
    internal var googlePhotoUrl by Users.googlePhotoUrl
    internal var firebaseAuthId by Users.firebaseAuthId
    internal var phoneNumber by Users.phoneNumber

    fun mapToUserResponse() = transaction {
        UserResponse(
            login = login,
            firstName = firstName,
            secondName = lastName,
            email = email,
            dateTimeRegistered = dateTimeRegistered.toString(),
            phoneNumber = phoneNumber
        )
    }



}

@Serializable
data class UserResponse(
    val login: String,
    val firstName: String?,
    val secondName: String?,
    val email: String,
    val dateTimeRegistered: String,
    val phoneNumber: String?
)