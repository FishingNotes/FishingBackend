package com.mobileprism.database.features.auth

import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Routing.loginRouting() {
    route("/login"){
        post {
            LoginController().login(call)
        }
        post("google") {
            LoginController().loginWithGoogle(call)
        }
        post("restore") {
            // TODO: 10.07.2022
        }
    }
}