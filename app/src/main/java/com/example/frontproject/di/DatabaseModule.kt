package com.example.frontproject.di

import android.content.Context
import androidx.room.Room
import com.example.frontproject.data.local.ToDoDao
import com.example.frontproject.data.local.ToDoDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped


@Module
@InstallIn(ViewModelComponent::class)
object DatabaseModule {

    @Provides
    @ViewModelScoped
    fun provideLocalDatabase(
        @ApplicationContext context : Context) : ToDoDatabase =
        Room.databaseBuilder(context,ToDoDatabase::class.java,"todo_database").build()

    @Provides
    @ViewModelScoped
    fun provideToDoDao(toDoDatabase: ToDoDatabase) : ToDoDao = toDoDatabase.toDoDao()
}