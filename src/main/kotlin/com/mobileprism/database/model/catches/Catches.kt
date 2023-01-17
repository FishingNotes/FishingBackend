package com.mobileprism.database.model.catches

import com.mobileprism.database.features.catches.NewCatchRemote
import com.mobileprism.database.model.catches.util_tables.FishType
import com.mobileprism.database.model.markers.MarkerDTO
import com.mobileprism.database.model.markers.MarkerDTO.Companion.referrersOn
import com.mobileprism.database.model.markers.UserMarkers
import com.mobileprism.database.model.notes.MarkerNoteDTO
import com.mobileprism.database.model.notes.MarkerNotes
import com.mobileprism.database.model.users.UserDTO
import com.mobileprism.database.model.users.Users
import com.mobileprism.database.model.utils.toLocalDate
import com.mobileprism.database.model.utils.toLocalDateTime
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.javatime.date
import org.jetbrains.exposed.sql.javatime.datetime
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.UUID

object Catches : UUIDTable("catches") {
    internal val marker = reference("marker", UserMarkers)
    internal val user = reference("user", Users)
    internal val description = varchar("description", 500)
    internal val dateOfCatch = date("date").default(LocalDate.now())
    internal val dateTimeCreated = datetime("datetime_created").default(LocalDateTime.now())
    internal val dateTimeChanged = datetime("datetime_changed").default(LocalDateTime.now())

    internal val noteTitle = varchar("note_title", 100)
    internal val noteDescription = varchar("note_description", 500)
    internal val noteDateTimeCreated = date("note_datetime_created").default(LocalDate.now())

    internal val fishType = reference("fish_type", FishType)

    internal val fishAmount = integer("fish_amount")
    internal val fishWeight = double("fish_weight")

    internal val fishingRodType = varchar("fishing_rod_type", 100)
    internal val fishingRod = varchar("fishing_rod", 100)
    internal val fishingBait = varchar("fishing_bait", 100)
    internal val fishingLure = varchar("fishing_lure", 100)

    internal val isPrivate = bool("is_private")

    // TODO: finish enum FishingWeather
    //internal val weatherMain = enumeration<>()

    internal val weatherTemperature = double("weather_temperature")
    internal val weatherWindSpeed = double("weather_wind_speed")
    internal val weatherWindDeg = integer("weather_wind_deg")
    internal val weatherPressure = integer("weather_pressure")
    internal val weatherMoonPhase = double("weather_moon_phase")

    fun createNewCatch(currentUser: UserDTO, markerDTO: MarkerDTO, newCatchRemote: NewCatchRemote) {
        transaction {
            /*CatchDTO.new {
                marker = markerDTO
                user = currentUser
                description = newCatchRemote.description
                dateOfCatch = newCatchRemote.dateOfCatch.toLocalDate().toString()
                dateCreated by Catches.description
                noteTitle = varchar("note_title, 100")
                noteDescription = varchar("note_description, 500")
                noteDateTimeCreated = date("datetime_created").default(LocalDate.now())
                fishType by Catches.description
                fishAmount by Catches.description
                fishWeight by Catches.description
                fishingRod by Catches.description
                fishingBait by Catches.description
                fishingLure by Catches.description
                isPublic by Catches.description
                //downloadPhotoLinks: List<String> = listOf(),
                weather_primary: String = "",
                weather_icon: String = "01",
                weatherTemperature by Catches.description
                weatherWindSpeed by Catches.description
                weatherWindDeg by Catches.description
                weatherPressure by Catches.description
                weatherMoonPhase by Catches.description
            }*/
        }
    }

    fun getCatchById(catchId: UUID): CatchDTO? {
        return transaction {
            CatchDTO.findById(catchId)
        }
    }

}

