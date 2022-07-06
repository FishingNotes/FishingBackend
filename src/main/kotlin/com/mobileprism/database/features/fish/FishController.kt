package com.mobileprism.database.features.fish

import com.mobileprism.database.model.catches.util_tables.FishType
import com.mobileprism.database.validateToken
import com.mobileprism.models.fish.FishResponse
import io.ktor.server.application.*
import io.ktor.server.response.*

class FishController {

    suspend fun getAllFish(call: ApplicationCall) {
        validateToken(call) { token ->
            val fish = FishType.getFish().map { it.mapToFishTypeResponse() }
            call.respond(FishResponse(fish))
        }
    }

}
