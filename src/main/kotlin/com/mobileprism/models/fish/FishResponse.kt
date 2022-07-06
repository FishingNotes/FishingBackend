package com.mobileprism.models.fish

import com.mobileprism.database.model.catches.util_tables.FishTypeResponse
import kotlinx.serialization.Serializable

@Serializable
class FishResponse(
    val fish: List<FishTypeResponse>
)
