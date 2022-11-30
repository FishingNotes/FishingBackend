package com.mobileprism.database.model.weather

import at.favre.lib.crypto.bcrypt.BCrypt
import com.mobileprism.database.model.catches.Catches
import com.mobileprism.models.register.GoogleAuthRemote
import com.mobileprism.models.register.RegisterRemote
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.javatime.datetime
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalDateTime

object MainWeather : IntIdTable("weather_main") {

    internal val weatherTitle = varchar("weather_title", 100)
    //internal val weatherIcon = varchar("weather_img", 100)

}