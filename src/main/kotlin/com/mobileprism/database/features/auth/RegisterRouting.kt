package com.mobileprism.database.features.auth

import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Routing.registrationRouting() {

    route("/register") {
        post {
            RegisterController().registerNewUser(call)
        }
    }

}