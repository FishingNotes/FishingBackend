package com.mobileprism.database.model.notes

import com.mobileprism.database.features.notes.NewNoteRemote
import com.mobileprism.database.model.markers.MarkerDTO
import com.mobileprism.database.model.markers.UserMarkers
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.javatime.datetime
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalDateTime
import java.util.UUID

object MarkerNotes : UUIDTable("marker_notes") {
    internal val title = varchar("title", 100)
    internal val description = varchar("description", 500)
    internal val datetimeCreated = datetime("datetime_created").default(LocalDateTime.now())
    internal val datetimeChanged = datetime("datetime_changed").default(LocalDateTime.now())

    internal val marker = reference("marker", UserMarkers)

    fun create(marker: MarkerDTO?, markerNoteDTO: NewNoteRemote): MarkerNoteDTO? {
        return transaction {
            marker?.let {
                MarkerNoteDTO.new {
                    title = markerNoteDTO.title
                    description = markerNoteDTO.description
                    this.marker = marker
                }
            }
        }
    }

   fun getAllNotesByMarkerId(markerId: UUID): List<MarkerNoteDTO>  {
        return transaction {
            MarkerNoteDTO.find { UserMarkers.id eq markerId }.toList()
        }
    }

    fun delete(noteId: UUID) {
        return transaction {
            MarkerNoteDTO.findById(noteId)?.delete()
        }
    }

    fun update(noteId: UUID, markerNoteDTO: NewNoteRemote) {
        return transaction {
            //MarkerNoteDTO.findById(noteId)?
        }
    }

}


