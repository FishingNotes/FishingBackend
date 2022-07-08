package com.mobileprism.database.model.images

import com.mobileprism.database.model.catches.util_tables.FishType
import com.mobileprism.database.model.markers.UserMarkers
import com.mobileprism.database.model.users.UserDTO
import com.mobileprism.database.model.users.Users
import com.mobileprism.database.model.weather.MainWeather
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.Table.Dual.autoGenerate
import org.jetbrains.exposed.sql.javatime.date
import org.jetbrains.exposed.sql.javatime.datetime
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.UUID

object Images : UUIDTable("images") {
    //internal val name = uuid("name")
    //override val id = uuid("id").autoGenerate()
    internal val user = reference("user", Users)
    internal val datetimeCreated = datetime("datetime_created").default(LocalDateTime.now())

    fun addNewImage(user: UserDTO, imageUUID: UUID): ImageDTO {
        return transaction {
            ImageDTO.new(imageUUID) {
                this.user = user
            }
        }
    }

    fun getImageById(imageId: UUID): ImageDTO? {
        return transaction {
            ImageDTO.findById(imageId)
        }
    }

    fun delete(it: ImageDTO) {
        return transaction {
            it.delete()
        }
    }

    /*fun getImageById(imageId: UUID): CatchDTO? {
        return transaction {
            CatchDTO.findById(catchId)
        }
    }*/
}

