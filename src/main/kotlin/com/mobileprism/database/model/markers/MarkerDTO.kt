package com.mobileprism.database.model.markers

import com.mobileprism.database.model.notes.MarkerNoteDTO
import com.mobileprism.database.model.notes.MarkerNotes
import com.mobileprism.database.model.notes.NoteResponse
import com.mobileprism.database.model.users.UserDTO
import com.mobileprism.database.model.users.UserResponse
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*


class MarkerDTO (id: EntityID<UUID>) : UUIDEntity(id) {

    companion object : UUIDEntityClass<MarkerDTO>(UserMarkers)

    internal var user by UserDTO referencedOn UserMarkers.user
    internal var latitude by UserMarkers.latitude
    internal var longitude by UserMarkers.longitude
    internal var title by UserMarkers.title
    internal var description by UserMarkers.description
    internal var markerColor by UserMarkers.markerColor
    internal var datetimeCreated by UserMarkers.datetimeCreated
    internal var datetimeChanged by UserMarkers.datetimeChanged
    internal var visible by UserMarkers.visible
    internal var private by UserMarkers.private
    internal val notes by MarkerNoteDTO referrersOn MarkerNotes.marker

    fun mapToMarkerResponse() = transaction {
        MarkerResponse(
            id = this@MarkerDTO.id.value.toString(),
            user = user.mapToUserResponse(),
            latitude = latitude,
            longitude = longitude,
            title = title,
            description = description,
            markerColor = markerColor,
            datetimeCreated = datetimeCreated.toString(),
            datetimeChanged = datetimeChanged.toString(),
            visible = visible,
            private = private,
            notes = notes.map { it.mapToNoteResponse() }
        )
    }
}

@kotlinx.serialization.Serializable
data class MarkerResponse(
    val id: String,
    val user: UserResponse,
    val latitude: Double,
    val longitude: Double,
    val title: String,
    val description: String,
    val markerColor: Int,
    val datetimeCreated: String,
    val datetimeChanged: String,
    val visible: Boolean,
    val private: Boolean,
    val notes: List<NoteResponse>,
)
