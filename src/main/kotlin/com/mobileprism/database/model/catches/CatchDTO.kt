package com.mobileprism.database.model.catches

import com.mobileprism.database.model.markers.MarkerDTO
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
    internal val date by Catches.description
    internal val dateTimeCreated by Catches.dateTimeCreated
    internal val dateTimeChanged by Catches.dateTimeChanged

    internal val noteTitle by Catches.noteTitle
    internal val noteDescription by Catches.noteDescription
    internal val noteDateTimeCreated by Catches.noteDateTimeCreated

    /*internal val fishType by Catches.description
    internal val fishAmount by Catches.description
    internal val fishWeight by Catches.description
    internal val fishingRod by Catches.description
    internal val fishingBait by Catches.description
    internal val fishingLure by Catches.description*/
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

