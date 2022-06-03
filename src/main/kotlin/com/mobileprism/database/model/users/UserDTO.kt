package com.mobileprism.database.model.users

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalDate
import java.util.UUID

class UserDTO(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<UserDTO>(Users)

    internal var login by Users.login
    internal var password by Users.password
    internal var firstName by Users.firstName
    internal var secondName by Users.secondName
    internal var email by Users.email
    internal var dateTimeRegistered by Users.dateTimeRegistered

    fun mapToUserResponse() = transaction {
        UserResponse(
            login = login,
            firstName = firstName,
            secondName = secondName,
            email = email,
        )
    }

}

@Serializable
data class UserResponse(
    val login: String,
    val firstName: String,
    val secondName: String,
    val email: String
)