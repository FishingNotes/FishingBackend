package com.mobileprism.database.features.markers

import com.mobileprism.database.features.notes.markerNotesRoute
import io.ktor.server.application.*
import io.ktor.server.routing.*


fun Routing.markersRouting() {
    route("/markers") {

        post("new") {
            MarkersController().createNewMarker(call)
        }
        get {
            MarkersController().getUserMarkers(call)
        }

        route("{markerId?}") {
            get {
                MarkersController().getMarkerById(call)
            }
            delete("{markerId?}") {
                MarkersController().deleteMarker(call)
            }
            markerNotesRoute()
        }
    }

}




