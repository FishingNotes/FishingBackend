package com.mobileprism.database.features.catches

import kotlinx.serialization.Serializable


@Serializable
data class NewCatchRemote(
    var description: String,
    var note: String,
    var dateOfCatch: String,
    var dateOfCreation: String,
    var fishTypeId: Int?,
    var fishAmount: Int,
    var fishWeight: Double,
    var fishingRodType: String,
    var fishingBait: String,
    var fishingLure: String,
    var isPublic: Boolean,
    var downloadPhotoLinks: List<String> = listOf(),
//    var weatherPrimary: MainWeatherType,
    var weather: String = "",
    var weatherTemperature: Float = 0.0f,
    var weatherWindSpeed: Float = 0.0f,
    var weatherWindDeg: Int = 0,
    var weatherPressure: Int = 0,
    var weatherMoonPhase: Float = 0.0f
)

