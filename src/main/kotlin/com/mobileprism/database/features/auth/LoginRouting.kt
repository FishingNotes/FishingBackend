package com.mobileprism.database.features.auth

import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Routing.loginRouting() {

    post("/login") {
        LoginController().loginUser(call)
    }
}