package com.mobileprism.database.model.tokens

import com.mobileprism.database.model.users.UserDTO
import com.mobileprism.database.model.users.Users
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.util.*

class TokenDTO(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<TokenDTO>(Tokens)

    internal var token by Tokens.token
    internal var user by UserDTO referencedOn Tokens.user
    var isActive by Tokens.isActive
    internal var datetimeCreated by Tokens.datetimeCreated
}