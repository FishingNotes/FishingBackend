package com.mobileprism.database.features.catches

import com.mobileprism.database.model.catches.Catches
import com.mobileprism.database.parameterUUIDRequired
import com.mobileprism.database.receiveModel
import com.mobileprism.database.validateToken
import com.mobileprism.database.model.markers.UserMarkers
import com.mobileprism.database.model.otps.OTPs
import com.mobileprism.database.model.tokens.Tokens
import com.mobileprism.database.validateTokenWithUser
import com.mobileprism.models.markers.MarkersResponse
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*


class CatchesController {

    suspend fun createNewCatch(call: ApplicationCall) {
        validateTokenWithUser(call) { token, currentUser ->
            parameterUUIDRequired(call, "markerId") { markerId ->
                val newCatchRemote = call.receiveModel<NewCatchRemote>()
                val marker = UserMarkers.getMarkerById(markerId)

                if (marker == null) {
                    call.respond(HttpStatusCode.NotFound)
                    return
                }

                Catches.createNewCatch(currentUser, marker, newCatchRemote)
                call.respond(HttpStatusCode.Created)
            }
        }
    }

    suspend fun deleteCatch(call: ApplicationCall) {
        validateToken(call) { token ->
            parameterUUIDRequired(call, "markerId") { markerId ->
                UserMarkers.deleteMarker(markerId)
                call.respond(HttpStatusCode.Accepted)
            }
        }
    }

    suspend fun getUserCatches(call: ApplicationCall) {
        validateToken(call) { token ->
            val currentUser = Tokens.getUserByToken(token)
            val markers = UserMarkers.getUserMarkers(currentUser)
                .map { it.mapToMarkerResponse() }
            call.respond(MarkersResponse(markers))
        }
    }

    suspend fun getCatchById(call: ApplicationCall) {
        validateToken(call) { token ->
            parameterUUIDRequired(call, "catchId") { markerId ->
                val marker = UserMarkers.getMarkerById(markerId)?.mapToMarkerResponse()
                call.respond(marker ?: "")
            }
        }
    }

    suspend fun getCatchesByMarkerId(call: ApplicationCall) {
        validateToken(call) { token ->
            parameterUUIDRequired(call, "markerId") { markerId ->
                val marker = UserMarkers.getMarkerById(markerId)?.mapToMarkerResponse()
                call.respond(marker ?: "")
            }
        }
    }
}
