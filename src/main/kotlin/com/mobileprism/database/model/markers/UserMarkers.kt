package com.mobileprism.database.model.markers

import com.mobileprism.database.features.markers.NewMarkerRemote
import com.mobileprism.database.model.users.UserDTO
import com.mobileprism.database.model.users.Users
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.javatime.datetime
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalDateTime
import java.util.UUID

object UserMarkers : UUIDTable("user_markers") {
    val user = reference("user", Users)
    val latitude = double("latitude")
    val longitude = double("longitude")
    val title = varchar("title", 100)
    val description = varchar("description", 500)
    val markerColor = integer("marker_color")
    val datetimeCreated = datetime("datetime_created").default(LocalDateTime.now())
    val datetimeChanged = datetime("datetime_changed").default(LocalDateTime.now())
    val visible = bool("is_visible")
    val private = bool("is_private")

    fun createNewMarker(currentUser: UserDTO, newMarkerRemote: NewMarkerRemote): MarkerDTO {
        return transaction {
            MarkerDTO.new {
                user = currentUser
                latitude = newMarkerRemote.latitude
                longitude = newMarkerRemote.longitude
                title = newMarkerRemote.title
                description = newMarkerRemote.description
                markerColor = newMarkerRemote.markerColor
                visible = newMarkerRemote.visible
                private = newMarkerRemote.private
            }
        }
    }

    fun getUserMarkers(currentUser: UserDTO): List<MarkerDTO> {
        return transaction {
            MarkerDTO.find { UserMarkers.user eq currentUser.id }.toList()
        }
    }

    fun deleteMarker(markerId: UUID) {
        transaction {
            MarkerDTO.findById(markerId)?.delete()
        }
    }

    fun getMarkerById(id: UUID): MarkerDTO? {
        return transaction {
            MarkerDTO.findById(id)
        }
    }

}