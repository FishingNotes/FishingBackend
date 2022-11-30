package com.mobileprism.plugins

import com.mobileprism.database.features.auth.*
import com.mobileprism.database.features.catches.catchesRouting
import com.mobileprism.database.features.markers.markersRouting
import com.mobileprism.database.features.fish.FishController
import com.mobileprism.database.features.images.imagesRouting
import io.ktor.server.routing.*
import io.ktor.server.application.*
import io.ktor.server.response.*

fun Application.configureRouting() {

    routing {
        get("/") {
            call.respondText("Hello Fishing!")
        }

        loginRouting()
        restoreRouting()
        registrationRouting()
        firebaseMigrateRouting()

        markersRouting()
        catchesRouting()
        fishRouting()
        imagesRouting()
    }
}

fun Routing.fishRouting() {
    route("/fish") {
        get {
            FishController().getAllFish(call)
        }
    }

}
