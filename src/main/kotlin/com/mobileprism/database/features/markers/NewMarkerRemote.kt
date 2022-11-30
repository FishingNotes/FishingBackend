package com.mobileprism.database.features.markers

import kotlinx.serialization.Serializable

@Serializable
data class NewMarkerRemote(
    internal val latitude: Double,
    internal val longitude: Double,
    internal val title: String,
    internal val description: String,
    internal val markerColor: Int,
    internal val datetimeCreated: String,
    internal val visible: Boolean,
    internal val private: Boolean,
)
