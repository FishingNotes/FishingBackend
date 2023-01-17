package com.mobileprism.database.features.auth

import io.ktor.server.application.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Routing.firebaseMigrateRouting() {

    val migrateService by inject<FirebaseMigrateController>()

    route("/firebase") {
        post("migration") {
            migrateService.migrate(call)
        }
    }
}