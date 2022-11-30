package com.mobileprism.database.model.users

import at.favre.lib.crypto.bcrypt.BCrypt
import com.mobileprism.database.model.firebase_restoration.FishingFirebaseUser
import com.mobileprism.database.model.utils.PasswordBCrypt
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

    internal val firstName = varchar("first_name", 50).nullable()
    internal val lastName = varchar("last_name", 50).nullable()

    internal val dateTimeRegistered = datetime("datetime_registered").default(LocalDateTime.now())

    internal val googleAuthId = varchar("google_auth_id", 50).nullable()
    internal val googlePhotoUrl = varchar("google_photo_url", 150).nullable()

    internal val firebaseAuthId = varchar("firebase_auth_id", 50).nullable()

    internal val phoneNumber = varchar("phone_number", 16).nullable()

    fun createNewUser(registerRemote: RegisterRemote): UserDTO {
        return transaction {
            UserDTO.new {
                login = createLoginForUser()
                password = PasswordBCrypt.encrypt(registerRemote.password.toCharArray())
                email = registerRemote.email.lowercase()
            }
        }
    }

    fun createNewGoogleUser(googleAuthRemote: GoogleAuthRemote): UserDTO {
        return transaction {
            UserDTO.new {
                login = createLoginForUser()
                email = googleAuthRemote.email.lowercase()
                googleAuthId = googleAuthRemote.googleAuthId
                firebaseAuthId = googleAuthRemote.firebaseAuthId
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
        return "Fisher_" + Random.nextInt(0, 1_000_000)
    }

    fun getUserByLogin(login: String): UserDTO? {
        return transaction {
            UserDTO.find { Users.login.eq(login) }.firstOrNull()
        }
    }

    fun getUserByEmail(email: String): UserDTO? {
        return transaction {
            UserDTO.find { Users.email.eq(email.lowercase()) }.firstOrNull()
        }
    }

    fun getUserByGoogleAuthId(googleAuthId: String): UserDTO? {
        return transaction {
            UserDTO.find { Users.googleAuthId.eq(googleAuthId) }.firstOrNull()
        }
    }

    fun restoreFromFirebase(firebaseUser: FishingFirebaseUser) {
        return transaction {
            UserDTO.find { Users.googleAuthId.eq(googleAuthId) }.firstOrNull()
        }
    }


}