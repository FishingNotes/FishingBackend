package com.mobileprism.database.model.notes

import com.mobileprism.database.model.markers.MarkerDTO
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import kotlinx.serialization.Serializable
import java.util.UUID

class MarkerNoteDTO(id: EntityID<UUID>) : UUIDEntity(id) {

    companion object : UUIDEntityClass<MarkerNoteDTO>(MarkerNotes)

    internal var title by MarkerNotes.title
    internal var description by MarkerNotes.description
    private val datetimeCreated by MarkerNotes.datetimeCreated
    private val datetimeChanged by MarkerNotes.datetimeChanged

    internal var marker by MarkerDTO referencedOn MarkerNotes.marker


    fun mapToNoteResponse() =
        NoteResponse(
            id = id.value.toString(),
            title = title,
            description = description,
            datetimeCreated = datetimeCreated.toString(),
            datetimeChanged = datetimeChanged.toString(),
            markerId = marker.id.value.toString()
        )
}

@Serializable
data class NoteResponse(
    val id: String,
    val title: String,
    val description: String,
    val datetimeCreated: String,
    val datetimeChanged: String,
    val markerId: String,
)

@Serializable
data class NotesResponse(
    val notes: List<NoteResponse>
)
