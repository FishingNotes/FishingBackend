package com.mobileprism.database.features.auth

import io.ktor.server.application.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Routing.loginRouting() {

    val loginService by inject<LoginController>()
    val restoreService by inject<RestoreController>()

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
            restoreService.searchForAccount(call)
        }
        post("restore") {
            restoreService.restorePassword(call)
        }
    }
}