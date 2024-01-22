package com.example.frontproject.repository

import com.example.frontproject.data.local.ToDoDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import javax.inject.Singleton


@InstallIn(ViewModelComponent::class)
@Module
object RepositoryModule {


    @Provides
    fun provideTodoRepository(toDoDao: ToDoDao): ToDoRepository = ToDoRepositoryImpl(toDoDao)

}