package com.mobileprism.database.features.markers

import com.mobileprism.database.model.tokens.Tokens

class TokenValidator {

    fun isTokenValid(token: String?): Boolean {
        return when {
            token.isNullOrBlank() -> false
            Tokens.getToken(token)?.isActive == true -> true
            else -> false
        }
    }

}
