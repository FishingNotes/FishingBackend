package com.mobileprism.database.model.utils

data class FishingResponse(
    val success: Boolean? = null,
    val fishingCode: FishingCodes,
    val httpCode: Int = 0,
    val description: String = "",
)

enum class FishingCodes {
    SUCCESS,
    USER_NOT_FOUND,
    USERNAME_NOT_FOUND,
    INVALID_CREDENTIALS,

}

