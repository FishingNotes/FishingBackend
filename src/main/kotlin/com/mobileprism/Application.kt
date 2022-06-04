package com.mobileprism

import io.ktor.server.engine.*
import io.ktor.server.cio.*
import com.mobileprism.plugins.*
import io.ktor.server.application.*
import io.ktor.server.plugins.callloging.*
import org.jetbrains.exposed.sql.Database

fun main() {
    Database.connect(
        "jdbc:postgresql://localhost:5432/fishingnotes",
        driver = "org.postgresql.Driver",
        user = "postgres",
        password = "1234"
    )

    embeddedServer(CIO, port = 8080, host = "0.0.0.0") {
        install(CallLogging)
        configureRouting()
        configureSerialization()
    }.start(wait = true)
}

