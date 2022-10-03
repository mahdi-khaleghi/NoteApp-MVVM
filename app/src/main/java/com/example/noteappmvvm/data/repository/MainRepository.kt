package com.example.noteappmvvm.data.repository

import com.example.noteappmvvm.data.database.NoteDao
import com.example.noteappmvvm.data.model.NoteEntity
import javax.inject.Inject

class MainRepository @Inject constructor(private val dao: NoteDao) {
    fun allNotes() = dao.getAllNotes()
    fun searchNotes(search: String) = dao.searchNote(search)
    fun filterNotes(filter: String) = dao.filterNote(filter)
    suspend fun deleteNote(entity: NoteEntity) = dao.deleteNote(entity)
}