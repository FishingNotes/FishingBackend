package com.mobileprism.database.model.images

import com.mobileprism.database.model.catches.Catches
import com.mobileprism.database.model.users.UserDTO
import com.mobileprism.database.model.users.Users
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.javatime.datetime
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalDateTime
import java.util.UUID

object CatchImages : UUIDTable("catch_images") {

    internal val catch = reference("catch", Catches)
    internal val datetimeCreated = datetime("datetime_created").default(LocalDateTime.now())

    fun addNewImage(user: UserDTO, imageUUID: UUID): UserImageDTO {
        return transaction {
            UserImageDTO.new(imageUUID) {
                this.user = user
            }
        }
    }

    fun getAllImagesByUser(user: UserDTO): List<UserImageDTO> {
        return transaction {
            UserImageDTO.all().toList()
        }
    }

    fun getImageById(imageId: UUID): UserImageDTO? {
        return transaction {
            UserImageDTO.findById(imageId)
        }
    }

    fun delete(it: UserImageDTO) {
        return transaction {
            it.delete()
        }
    }

}

