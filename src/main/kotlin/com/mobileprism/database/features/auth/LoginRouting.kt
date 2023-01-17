package com.mobileprism.database.features.auth

import io.ktor.server.application.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Routing.loginRouting() {

    route("/login") {
        post("username") {
            LoginController().loginWithUsername(call)
        }
        post("email") {
            LoginController().loginWithEmail(call)
        }
        post("google") {
            LoginController().loginWithGoogle(call)
        }
        post("restore") {
            RestoreController().restorePassword(call)
        }
    }
}



