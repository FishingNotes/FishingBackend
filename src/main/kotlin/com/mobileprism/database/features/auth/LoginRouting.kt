package com.mobileprism.database.features.auth

import io.ktor.server.application.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Routing.loginRouting() {

    val loginService by inject<LoginController>()

    route("/login"){
        post("username") {
            loginService.loginWithUsername(call)
        }
        post("email") {
            loginService.loginWithEmail(call)
        }
        post("google") {
            loginService.loginWithGoogle(call)
        }
        post("search-account") {
            loginService.searchForAccount(call)
        }
        post("restore") {
            loginService.restorePassword(call)
        }
    }
}