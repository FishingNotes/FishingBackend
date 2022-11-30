package com.mobileprism.database.model.images

import com.mobileprism.database.model.users.UserDTO
import com.mobileprism.database.model.users.UserResponse
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.UUID

class UserImageDTO (id: EntityID<UUID>) : UUIDEntity(id) {

    companion object : UUIDEntityClass<UserImageDTO>(UserImages)

    internal var user by UserDTO referencedOn UserImages.user
    internal var datetimeCreated by UserImages.datetimeCreated

    fun mapToImageResponse() = transaction {
        UserImageResponse(
            imageId = this@UserImageDTO.id.toString() + ".png",
            datetimeCreated = this@UserImageDTO.datetimeCreated.toString(),
            user = this@UserImageDTO.user.mapToUserResponse(),
        )
    }

}

@Serializable
data class UserImageResponse(
    val imageId: String,
    val datetimeCreated: String,
    val user: UserResponse
)
