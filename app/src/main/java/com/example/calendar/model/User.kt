package com.example.calendar.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity
data class User(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val userId: String = UUID.randomUUID().toString(),
    @ColumnInfo(name = "name") val userName: String,
    @ColumnInfo(name = "email") val userEmail: String,
    @ColumnInfo(name = "password") val userPassword: String
)