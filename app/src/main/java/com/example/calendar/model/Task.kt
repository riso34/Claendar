package com.example.calendar.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity
data class Task(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val userId: String,
    val name: String,
    val title: String,
    val startTime: Long,
    val endTime: Long,
    val context: String?,
    val notification: String?

)