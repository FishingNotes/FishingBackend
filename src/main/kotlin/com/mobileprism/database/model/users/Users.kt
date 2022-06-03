package com.mobileprism.database.model.users

import com.mobileprism.models.register.RegisterRemote
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.javatime.date
import org.jetbrains.exposed.sql.javatime.datetime
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalDate
import java.time.LocalDateTime

object Users : UUIDTable("users") {
    internal val login = varchar("login", 20)
    internal val password = varchar("password", 50)
    internal val firstName = varchar("first_name", 50).default("")
    internal val secondName = varchar("second_name", 50).default("")
    internal val email = varchar("email", 50)
    internal val dateTimeRegistered = datetime("datetime_registered").default(LocalDateTime.now())

    fun createNewUser(registerRemote: RegisterRemote): UserDTO {
        return transaction {
            UserDTO.new {
                login = registerRemote.login
                password = registerRemote.password
                email = registerRemote.email
            }
        }
    }

    fun getUserByLogin(login: String): UserDTO? {
        return transaction {
            UserDTO.find { Users.login.eq(login) }.firstOrNull()
        }
    }

    fun getUserByEmail(email: String): UserDTO? {
        return transaction {
            UserDTO.find { Users.email.eq(email) }.firstOrNull()
        }
    }


}