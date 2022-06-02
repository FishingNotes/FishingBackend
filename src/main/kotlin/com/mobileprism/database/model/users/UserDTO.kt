package com.mobileprism.database.model.users

import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
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
}