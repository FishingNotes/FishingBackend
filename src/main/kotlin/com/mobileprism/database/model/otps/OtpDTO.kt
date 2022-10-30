package com.mobileprism.database.model.otps

import com.mobileprism.database.model.users.UserDTO
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import java.util.*

class OtpDTO(id: EntityID<UUID>) : UUIDEntity(id) {

    companion object : UUIDEntityClass<OtpDTO>(OTPs)

    internal var otp by OTPs.otp
    internal var user by UserDTO referencedOn OTPs.user
    internal var isActive by OTPs.isActive
    internal var datetimeCreated by OTPs.datetimeCreated

}