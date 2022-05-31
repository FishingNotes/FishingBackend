package com.mobileprism.database.users

import org.jetbrains.exposed.sql.IColumnType
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.date
import java.time.LocalDate

object UserModel: Table("users") {
    private val id = UserModel.varchar("id", 50)
    private val login = UserModel.varchar("login", 50)
    private val password = UserModel.varchar("password", 50)
    private val first_name = UserModel.varchar("first_name", 50)
    private val second_name = UserModel.varchar("second_name", 50)
    private val email = UserModel.varchar("email", 50)
    private val date_register = UserModel.date("date_register").default(LocalDate.now())

    fun insert

}