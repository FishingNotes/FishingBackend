package com.mobileprism.database.model.images

import com.mobileprism.database.model.catches.util_tables.FishTypeResponse
import com.mobileprism.database.model.markers.MarkerDTO
import com.mobileprism.database.model.users.UserDTO
import com.mobileprism.database.model.users.UserResponse
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.UUID

class ImageDTO (id: EntityID<UUID>) : UUIDEntity(id) {

    companion object : UUIDEntityClass<ImageDTO>(Images)

    internal var user by UserDTO referencedOn Images.user
    internal var datetimeCreated by Images.datetimeCreated

    fun mapToImageResponse() = transaction {
        ImageResponse(
            imageId = this@ImageDTO.id.toString(),
            datetimeCreated = this@ImageDTO.datetimeCreated.toString(),
            user = this@ImageDTO.user.mapToUserResponse(),
        )
    }

}

@kotlinx.serialization.Serializable
data class ImageResponse(
    val imageId: String,
    val datetimeCreated: String,
    val user: UserResponse
)
