package com.mobileprism.database.model.catches.util_tables

import com.mobileprism.database.features.notes.NewNoteRemote
import com.mobileprism.database.model.markers.MarkerDTO
import com.mobileprism.database.model.markers.UserMarkers
import com.mobileprism.database.model.notes.MarkerNoteDTO
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.javatime.datetime
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalDateTime
import java.util.*

object FishType : IntIdTable("fish_type") {

    internal val latName = varchar("lat", 100)
    internal val engName = varchar("en", 100)
    internal val rusName = varchar("ru", 100)

    fun getFish(): List<FishTypeDTO> {
        return transaction {
            FishTypeDTO.all().toList()
        }
    }

}