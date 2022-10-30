package com.mobileprism.database.model.tokens

import com.mobileprism.database.model.users.UserDTO
import com.mobileprism.database.model.users.Users
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.javatime.datetime
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalDateTime
import java.util.*

object Tokens : UUIDTable("tokens") {
    internal val token = varchar("token", 50)
    internal val user = reference("user", Users)
    internal val isActive = bool("is_active").default(true)
    internal val datetimeCreated = datetime("datetime_created").default(LocalDateTime.now())
    internal val datetimeLastUsed = datetime("datetime_last_used").default(LocalDateTime.now())

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
            TokenDTO.find(Tokens.token.eq(token)).firstOrNull()?.also {
                it.datetimeLastUsed = LocalDateTime.now()
            }
        }
    }

    fun getUserByToken(token: String): UserDTO {
        return transaction {
            TokenDTO.find(Tokens.token.eq(token)).first().user
        }
    }

}