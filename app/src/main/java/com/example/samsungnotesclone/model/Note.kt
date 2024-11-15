package com.example.samsungnotesclone.model

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey

@Keep
@Entity(tableName = "Note")
data class Note(
    @PrimaryKey(autoGenerate = false) val id: String = "",
    val title: String?,
    val content: String?,
    val timeStamp: Long = System.currentTimeMillis()
)
