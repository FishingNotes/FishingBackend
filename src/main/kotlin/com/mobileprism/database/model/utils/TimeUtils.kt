package com.mobileprism.database.model.utils

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

fun Long.toLocalDateTime() {
    LocalDateTime.ofInstant(Instant.ofEpochMilli(this), ZoneId.systemDefault())
}