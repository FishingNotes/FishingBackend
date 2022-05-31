package com.mobileprism

import com.mobileprism.login.configureLoginRouting
import io.ktor.server.engine.*
import io.ktor.server.cio.*
import com.mobileprism.plugins.*
import org.jetbrains.exposed.sql.Database

fun main() {
    Database.connect(
        "jdbc:postgresql://localhost:5432/fishingnotes",
        driver = "org.postgresql.Driver",
        user = "postgres",
        password = "1234"
    )

    embeddedServer(CIO, port = 8080, host = "0.0.0.0") {
        configureLoginRouting()
        configureRouting()
        configureSerialization()
    }.start(wait = true)
}

