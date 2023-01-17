package com.mobileprism.database.model.images

import com.mobileprism.database.model.catches.CatchDTO
import com.mobileprism.database.model.users.UserResponse
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.UUID

class CatchImageDTO (id: EntityID<UUID>) : UUIDEntity(id) {

    companion object : UUIDEntityClass<CatchImageDTO>(CatchImages)

    internal var catch by CatchDTO referencedOn CatchImages.catch

    internal var datetimeCreated by CatchImages.datetimeCreated

    fun mapToImageResponse() = transaction {
//        UserImageResponse(
//            imageId = this@CatchImageDTO.id.toString() + ".png",
//            datetimeCreated = this@CatchImageDTO.datetimeCreated.toString(),
//            user = this@CatchImageDTO.,
//        )
    }

}

@Serializable
data class CatchImageResponse(
    val imageId: String,
    val datetimeCreated: String,
    val user: UserResponse
)
