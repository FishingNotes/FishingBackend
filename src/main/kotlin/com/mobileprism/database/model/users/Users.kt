package com.mobileprism.database.model.users

import at.favre.lib.crypto.bcrypt.BCrypt
import com.mobileprism.models.register.GoogleAuthRemote
import com.mobileprism.models.register.RegisterRemote
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.javatime.datetime
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalDateTime
import kotlin.random.Random

object Users : UUIDTable("users") {
    internal val email = varchar("email", 50)
    internal val login = varchar("login", 20)

    internal val password = varchar("password", 100).nullable()

    internal val firstName = varchar("first_name", 50).default("")
    internal val secondName = varchar("second_name", 50).default("")

    internal val dateTimeRegistered = datetime("datetime_registered").default(LocalDateTime.now())

    internal val googleAuthId = varchar("google_auth_id", 100).nullable()
    internal val phoneNumber = varchar("phone_number", 16).nullable()

    fun createNewUser(registerRemote: RegisterRemote): UserDTO {
        return transaction {
            UserDTO.new {
                login = createLoginForUser()
                password = BCrypt.withDefaults().hashToString(6, registerRemote.password.toCharArray())
                email = registerRemote.email
            }
        }
    }

    private fun createLoginForUser(): String {
        var newLogin: String
        do {
            newLogin = generateLogin()
        } while (getUserByLogin(newLogin) != null)
        return newLogin
    }

    private fun generateLogin(): String {
        return "Fisher_" + Random.nextInt(10000, 999999)
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