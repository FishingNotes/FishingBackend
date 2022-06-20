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
import java.time.LocalDateTime
import java.util.*


class MarkerDTO (id: EntityID<UUID>) : UUIDEntity(id) {

    companion object : UUIDEntityClass<MarkerDTO>(Markers)

    internal var user by UserDTO referencedOn Markers.user
    internal var latitude by Markers.latitude
    internal var longitude by Markers.longitude
    internal var title by Markers.title
    internal var description by Markers.description
    internal var markerColor by Markers.markerColor
    internal var datetimeCreated by Markers.datetimeCreated
    internal var visible by Markers.visible
    internal var public by Markers.public
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
            visible = visible,
            public = public,
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
    val visible: Boolean,
    val public: Boolean,
    val notes: List<NoteResponse>,
)
