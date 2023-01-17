package com.mobileprism.database.model.firebase_restoration

import kotlinx.serialization.Serializable

@Serializable
internal data class FirebaseRestoration(
    val firebaseUser: FishingFirebaseUser,
    val userMarkers: List<UserMapMarker>,
    val userCatches: List<UserCatch>
)

@Serializable
data class FishingFirebaseUser(
    val uid: String,
    val email: String,
    val displayName: String,
    val photoUrl: String?,
)

@Serializable
internal data class UserMapMarker(
    var id: String,
    var userId: String,
    var latitude: Double,
    var longitude: Double,
    var title: String,
    var description: String,
    var markerColor: Int,
    var catchesCount: Int = 0,
    var dateOfCreation: Long = 0,
    var visible: Boolean,
    var public: Boolean,
    var notes: List<Note> = listOf(),
)

@Serializable
internal data class Note(
    val id: String = "",
    val markerId: String = "",
    val title: String = "",
    val description: String = "",
    val dateCreated: Long = 0
)

@Serializable
internal data class UserCatch(
    var id: String,
    var markerId: String,
    var userId: String,
    var description: String,
    var note: Note,
    var date: Long,
    var dateOfCreation: Long,
    var fishType: String,
    var fishAmount: Int,
    var fishWeight: Double,
    var fishingRodType: String,
    var fishingBait: String,
    var fishingLure: String,
    var placeTitle: String,
    var isPublic: Boolean,
    var downloadPhotoLinks: List<String>,
    var weather: String,
    var weatherTemperature: Float,
    var weatherWindSpeed: Float,
    var weatherWindDeg: Int,
    var weatherPressure: Int,
    var weatherMoonPhase: Float
)