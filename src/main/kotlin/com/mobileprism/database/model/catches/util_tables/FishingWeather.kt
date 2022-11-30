package com.mobileprism.database.model.catches.util_tables

import org.jetbrains.exposed.dao.id.UUIDTable

object FishingWeather : UUIDTable("fishing_weather") {
    internal val title = varchar("title", 100)
    internal val imageId = varchar("imageId", 100)


}