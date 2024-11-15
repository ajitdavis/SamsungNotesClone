package com.example.samsungnotesclone.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.samsungnotesclone.model.DatabaseProvider
import com.example.samsungnotesclone.model.Note
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NotesViewmodel(application: Application) : AndroidViewModel(application) {

    private val notesDao = DatabaseProvider.getInstance(application).noteDao()
    val notesList = notesDao.getAllNotes()

    fun deleteNotes(notes: List<Note>) {
        Log.d("checkkk", "deleteNotes: ${notes.size}")
        viewModelScope.launch(Dispatchers.IO) {
            notes.toList().forEach { note ->
                notesDao.delete(note)
            }
        }
    }
}