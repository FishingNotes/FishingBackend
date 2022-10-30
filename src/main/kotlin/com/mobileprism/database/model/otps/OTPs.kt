package com.mobileprism.database.model.otps

import com.mobileprism.database.model.users.UserDTO
import com.mobileprism.database.model.users.Users
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.javatime.datetime
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalDateTime
import kotlin.random.Random

object OTPs : UUIDTable("otps") {
    internal val otp = integer("otp_code")
    internal val user = reference("user", Users)
    internal val isActive = bool("is_active").default(true)
    internal val datetimeCreated = datetime("datetime_created").default(LocalDateTime.now())

    fun createNewOtpForUser(userDTO: UserDTO): OtpDTO {
        return transaction {
            OtpDTO.new {
                otp = Random.nextInt(100_000, 1_000_000)
                user = userDTO
            }
        }
    }

    fun findLastUserOTP(userDTO: UserDTO): OtpDTO? {
        return transaction {
            OtpDTO.find { user.eq(userDTO.id) }.maxByOrNull { it.datetimeCreated }
        }
    }

    /*fun getOtp(token: String): OtpDTO? {
        return transaction {
            OtpDTO.find(Otps.token.eq(token)).firstOrNull()?.also {
                it.datetimeLastUsed = LocalDateTime.now()
            }
        }
    }*/

}