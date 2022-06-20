package com.mobileprism.models.markers

import com.mobileprism.database.model.markers.MarkerDTO
import com.mobileprism.database.model.markers.MarkerResponse
import com.mobileprism.database.model.users.UserDTO
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Serializable
class MarkersResponse(
    val markers: List<MarkerResponse>
)

