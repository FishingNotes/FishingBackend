package com.mobileprism

import java.util.*

val String.toUUID: UUID
    get() = UUID.fromString(this)

val String.isEmail: Boolean
    get() = this.contains('@')