package com.mobileprism.database.features.catches

import com.mobileprism.database.features.notes.markerNotesRoute
import io.ktor.server.application.*
import io.ktor.server.routing.*


fun Routing.catchesRouting() {
    route("/catches") {

        get {
            CatchesController().getUserCatches(call)
        }
        get("{catchId?}") {
            CatchesController().getCatchById(call)
        }
        delete("{catchId?}") {
            CatchesController().deleteCatch(call)
        }

        route("{markerId?}") {
            post("new") {
                CatchesController().createNewCatch(call)
            }
            get {
                CatchesController().getCatchesByMarkerId(call)
            }
            /*get("{count?}") {
                CatchesController().getCatchesByMarkerId(call)
            }*/
        }
    }

}




