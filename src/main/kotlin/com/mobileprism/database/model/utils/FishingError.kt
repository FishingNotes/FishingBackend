package com.mobileprism.database.model.utils

import kotlinx.serialization.Serializable

@Serializable
data class FishingResponse(
    val success: Boolean? = null,
    val fishingCode: FishingCodes,
    val httpCode: Int = 0,
    val description: String = "",
)

enum class FishingCodes(val description: String = "") {
    SUCCESS("success query"),
    UNKNOWN_ERROR,
    USER_NOT_FOUND,
    USERNAME_NOT_FOUND,
    INVALID_CREDENTIALS,
    OTP_NOT_FOUND,
    NETWORK_ERROR,
    OTP_ATTEMPTS_EXCEEDED,
    EMAIL_ALREADY_EXISTS

}

