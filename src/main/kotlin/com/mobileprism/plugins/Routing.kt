package com.mobileprism.plugins

import com.mobileprism.database.features.catches.catchesRouting
import com.mobileprism.database.features.login.loginRouting
import com.mobileprism.database.features.catches.markersRouting
import com.mobileprism.database.features.markers.markersRouting
import com.mobileprism.database.features.register.registrationRouting
import io.ktor.server.routing.*
import io.ktor.server.application.*
import io.ktor.server.response.*

fun Application.configureRouting() {

    routing {
        get("/") {
            call.respondText("Hello World!")
        }

        loginRouting()
        registrationRouting()
        markersRouting()
        catchesRouting()

    }
}
