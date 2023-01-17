package com.mobileprism.database.model.catches.util_tables

import com.mobileprism.database.model.markers.MarkerDTO
import com.mobileprism.database.model.markers.MarkerResponse
import com.mobileprism.database.model.notes.NoteResponse
import com.mobileprism.database.model.users.UserDTO
import com.mobileprism.database.model.users.UserResponse
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.UUID

class FishTypeDTO(id: EntityID<Int>) : IntEntity(id) {

    companion object : IntEntityClass<FishTypeDTO>(FishType)

    internal val latName by FishType.latName
    internal val engName by FishType.engName
    internal val rusName by FishType.rusName

    fun mapToFishTypeResponse() = transaction {
        FishTypeResponse(
            id = this@FishTypeDTO.id.value,
            latName = latName,
            engName = engName,
            rusName = rusName,
        )
    }

}

@Serializable
data class FishTypeResponse(
    val id: Int,
    val latName: String,
    val engName: String,
    val rusName: String,
)
