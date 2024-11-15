package com.example.samsungnotesclone.model

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Note::class], version = 1)
abstract class NotesDb : RoomDatabase() {
    abstract fun noteDao(): NoteDao
}