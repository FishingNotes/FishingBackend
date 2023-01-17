package com.mobileprism.database.model.catches

import com.mobileprism.database.model.images.CatchImageDTO
import com.mobileprism.database.model.images.CatchImages
import com.mobileprism.database.model.markers.MarkerDTO
import com.mobileprism.database.model.markers.MarkerDTO.Companion.referrersOn
import com.mobileprism.database.model.notes.MarkerNotes
import com.mobileprism.database.model.users.UserDTO
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import java.util.UUID

class CatchDTO (id: EntityID<UUID>) : UUIDEntity(id) {

    companion object : UUIDEntityClass<CatchDTO>(Catches)

    internal var marker by MarkerDTO referencedOn Catches.marker
    internal var user by UserDTO referencedOn Catches.user
    internal var description by Catches.description
    internal var dateOfCatch by Catches.description
    internal var dateTimeCreated by Catches.dateTimeCreated
    internal var dateTimeChanged by Catches.dateTimeChanged

    internal val images by CatchImageDTO referrersOn CatchImages.catch

    internal var noteTitle by Catches.noteTitle
    internal var noteDescription by Catches.noteDescription
    internal var noteDateTimeCreated by Catches.noteDateTimeCreated

    internal var fishType by Catches.description
    internal var fishAmount by Catches.description
    internal var fishWeight by Catches.description
    internal var fishingRod by Catches.description
    internal var fishingBait by Catches.description
    internal var fishingLure by Catches.description
    //FISHING DATA

    internal val isPublic by Catches.description
    //private val downloadPhotoLinks: List<String> = listOf(),
    /*internal val weather_primary: String = "",
    internal val weather_icon: String = "01",*/
    internal val weatherTemperature by Catches.description
    internal val weatherWindSpeed by Catches.description
    internal val weatherWindDeg by Catches.description
    internal val weatherPressure by Catches.description
    internal val weatherMoonPhase by Catches.description
}

