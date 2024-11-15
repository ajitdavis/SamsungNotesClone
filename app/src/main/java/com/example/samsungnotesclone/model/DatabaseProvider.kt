package com.example.samsungnotesclone.model

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

object DatabaseProvider {

    @Volatile
    private var dbInstance: NotesDb? = null

    fun getInstance(context: Context): NotesDb {
        return dbInstance ?: synchronized(this) {
            dbInstance ?: Room.databaseBuilder(
                context.applicationContext,
                NotesDb::class.java,
                "notes_db"
            ).build().also {
                dbInstance = it
            }
        }
    }
}