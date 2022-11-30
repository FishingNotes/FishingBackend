package com.mobileprism.database.features.notes

import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Route.markerNotesRoute() {
    route("notes") {
        get {
            MarkerNotesController().getMarkerNotes(call)
        }
        post("new") {
            MarkerNotesController().createNewNote(call)
        }
        delete("{noteId?}") {
            MarkerNotesController().deleteNote(call)
        }
    }
}