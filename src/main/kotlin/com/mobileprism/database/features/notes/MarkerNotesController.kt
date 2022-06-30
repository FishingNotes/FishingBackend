package com.mobileprism.database.features.notes

import com.mobileprism.database.parameterUUIDRequired
import com.mobileprism.database.receiveModel
import com.mobileprism.database.validateToken
import com.mobileprism.database.model.markers.UserMarkers
import com.mobileprism.database.model.notes.MarkerNotes
import com.mobileprism.database.model.notes.NotesResponse
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*

class MarkerNotesController {

    suspend fun createNewNote(call: ApplicationCall) {
        validateToken(call) {
            parameterUUIDRequired(call, "markerId") { markerId ->
                val newNoteRemote = call.receiveModel<NewNoteRemote>()
                val marker = UserMarkers.getMarkerById(markerId)

                when (MarkerNotes.create(marker, newNoteRemote)) {
                    null -> call.respond(HttpStatusCode.NotFound)
                    else -> call.respond(HttpStatusCode.Created)
                }
            }
        }
    }

    suspend fun deleteNote(call: ApplicationCall) {
        validateToken(call) {
            parameterUUIDRequired(call, "noteId") { noteId ->
                when (MarkerNotes.delete(noteId)) {
                    null -> call.respond(HttpStatusCode.NotFound)
                    else -> call.respond(HttpStatusCode.Accepted)
                }
            }
        }
    }

    suspend fun getMarkerNotes(call: ApplicationCall) {
        validateToken(call) {
            parameterUUIDRequired(call, "markerId") { markerId ->
                when (val marker = UserMarkers.getMarkerById(markerId)?.mapToMarkerResponse()) {
                    null -> call.respond(HttpStatusCode.NotFound)
                    else -> call.respond(NotesResponse(marker.notes))
                }
            }
        }
    }
}