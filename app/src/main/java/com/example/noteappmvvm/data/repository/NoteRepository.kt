package com.example.noteappmvvm.data.repository

import com.example.noteappmvvm.data.database.NoteDao
import com.example.noteappmvvm.data.model.NoteEntity
import javax.inject.Inject

class NoteRepository @Inject constructor(private val dao: NoteDao) {
    suspend fun saveNote(entity: NoteEntity) = dao.saveNote(entity)
    suspend fun updateNote(entity: NoteEntity) = dao.updateNote(entity)
    fun getNote(id: Int) = dao.getNote(id)
}