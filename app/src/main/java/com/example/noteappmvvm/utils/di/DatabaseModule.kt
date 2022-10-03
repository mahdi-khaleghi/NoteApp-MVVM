package com.example.noteappmvvm.utils.di

import android.content.Context
import androidx.room.Room
import com.example.noteappmvvm.data.database.NoteDatabase
import com.example.noteappmvvm.data.model.NoteEntity
import com.example.noteappmvvm.utils.NOTE_DATABASE
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, NoteDatabase::class.java, NOTE_DATABASE)
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    @Singleton
    fun provideDoa(db: NoteDatabase) = db.noteDao()

    @Provides
    @Singleton
    fun provideEntity() = NoteEntity()
}