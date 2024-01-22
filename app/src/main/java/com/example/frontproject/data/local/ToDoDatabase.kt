package com.example.frontproject.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.frontproject.data.local.model.ToDO

@Database(entities = [ToDO::class], version = 1, exportSchema = false)
abstract class ToDoDatabase : RoomDatabase(){
    abstract fun toDoDao() : ToDoDao
}