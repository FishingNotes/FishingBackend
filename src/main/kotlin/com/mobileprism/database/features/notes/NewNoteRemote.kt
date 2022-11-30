package com.mobileprism.database.features.notes

import kotlinx.serialization.Serializable

@Serializable
data class NewNoteRemote(
    val title: String,
    val description: String,
)

