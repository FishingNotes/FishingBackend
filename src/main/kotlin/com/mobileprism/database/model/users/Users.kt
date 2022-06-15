package com.mobileprism.database.model.users

import at.favre.lib.crypto.bcrypt.BCrypt
import com.mobileprism.models.register.GoogleAuthRemote
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
    internal val email = varchar("email", 50)
    internal val login = varchar("login", 20).nullable()

    internal val password = varchar("password", 100).nullable()

    internal val firstName = varchar("first_name", 50).default("")
    internal val secondName = varchar("second_name", 50).default("")

    internal val dateTimeRegistered = datetime("datetime_registered").default(LocalDateTime.now())

    internal val googleAuthId = varchar("google_auth_id", 100).nullable()
    internal val phoneNumber = varchar("phone_number", 16).nullable()

    fun createNewUser(registerRemote: RegisterRemote): UserDTO {
        return transaction {
            UserDTO.new {
                password = BCrypt.withDefaults().hashToString(6, registerRemote.password.toCharArray())
                email = registerRemote.email
            }
        }
    }

    fun createNewUser(googleAuthRemote: GoogleAuthRemote): UserDTO {
        return transaction {
            UserDTO.new {
                email = googleAuthRemote.email
                googleAuthId = googleAuthRemote.googleAuthId
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

    fun getUserByGoogleAuthId(googleAuthId: String): UserDTO? {
        return transaction {
            UserDTO.find { Users.googleAuthId.eq(googleAuthId) }.firstOrNull()
        }
    }


}