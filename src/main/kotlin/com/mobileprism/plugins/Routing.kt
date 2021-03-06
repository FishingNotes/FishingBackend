package com.mobileprism.plugins

import com.mobileprism.database.features.auth.RegisterController
import com.mobileprism.database.features.catches.catchesRouting
import com.mobileprism.database.features.auth.loginRouting
import com.mobileprism.database.features.markers.markersRouting
import com.mobileprism.database.features.auth.registrationRouting
import com.mobileprism.database.features.fish.FishController
import io.ktor.server.routing.*
import io.ktor.server.application.*
import io.ktor.server.response.*

fun Application.configureRouting() {

    routing {
        get("/") {
            call.respondText("Hello Fishing!")
        }

        loginRouting()
        registrationRouting()
        markersRouting()
        catchesRouting()
        fishRouting()

    }
}

fun Routing.fishRouting() {
    route("/fish") {
        get {
            FishController().getAllFish(call)
        }
    }

}
