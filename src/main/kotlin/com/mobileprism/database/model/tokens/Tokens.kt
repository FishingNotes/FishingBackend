package com.mobileprism.database.model.tokens

import com.mobileprism.database.model.users.UserDTO
import com.mobileprism.database.model.users.Users
import com.mobileprism.database.model.users.Users.default
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.javatime.date
import org.jetbrains.exposed.sql.javatime.datetime
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.util.*

object Tokens : UUIDTable("tokens") {
    internal val token = varchar("token", 50)
    internal val user = reference("user", Users)
    internal val isActive = bool("is_active").default(true)
    internal val datetimeCreated = datetime("datetime_created").default(LocalDateTime.now())

    fun createNewTokenForUser(userDTO: UserDTO): TokenDTO {
        return transaction {
            TokenDTO.new {
                token = UUID.randomUUID().toString()
                user = userDTO
            }
        }
    }

    fun getToken(token: String): TokenDTO? {
        return transaction {
            TokenDTO.find(Tokens.token.eq(token)).firstOrNull()
        }
    }

    fun getUserByToken(token: String): UserDTO {
        return transaction {
            TokenDTO.find(Tokens.token.eq(token)).first().user
        }
    }

}