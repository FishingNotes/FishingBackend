package com.mobileprism.database.features.auth

import io.ktor.server.application.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Routing.restoreRouting() {

    val restoreService by inject<RestoreController>()

    route("/restore") {
        post("search") {
            restoreService.searchForAccount(call)
        }
        post("confirm") {
            restoreService.confirmOTP(call)
        }
        post("reset") {
            restoreService.restorePassword(call)
        }
    }
}