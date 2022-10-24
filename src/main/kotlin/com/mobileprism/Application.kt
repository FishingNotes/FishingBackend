package com.mobileprism

import com.mobileprism.database.DatabaseFactory
import com.mobileprism.database.di.authModule
import io.ktor.server.engine.*
import io.ktor.server.cio.*
import com.mobileprism.plugins.*
import io.ktor.network.tls.certificates.*
import io.ktor.server.application.*
import io.ktor.server.config.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.callloging.*
import io.ktor.util.pipeline.*
import org.jetbrains.exposed.sql.Database
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger
import java.io.File

/*fun main() {
    DatabaseFactory.init(environment.config)

    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
        install(CallLogging)
        configureRouting()
        configureSerialization()
    }.start(wait = true)
}*/


fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.mainModule() {

    DatabaseFactory.init(environment.config)

    install(Koin) {
        slf4jLogger()
        modules(authModule)
    }

    install(CallLogging)
    configureRouting()
    configureSerialization()
}
