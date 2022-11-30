package com.mobileprism.database.model.weather

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.UUID

class MainWeatherDTO(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<MainWeatherDTO>(MainWeather)

    internal var weatherTitle by MainWeather.weatherTitle


}