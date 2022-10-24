package com.mobileprism.database.model.utils

import at.favre.lib.crypto.bcrypt.BCrypt

object PasswordBCrypt {
    val verifyer = BCrypt.verifyer()

    fun verifyPasswords(firstPassword: CharArray, secondPassword: String?): Boolean {
        return verifyer.verify(firstPassword, secondPassword).verified
    }

    fun encrypt(charArray: CharArray): String {
        return BCrypt.withDefaults().hashToString(6, charArray)
    }

}