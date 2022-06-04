package com.mobileprism.database.features.login

import com.mobileprism.database.features.login.LoginController
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Routing.loginRouting() {

    post("/login") {
        LoginController().loginUser(call)
    }
}