package com.example.frontproject.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.frontproject.data.local.model.ToDO
import kotlinx.coroutines.flow.Flow

@Dao
interface ToDoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(todo : ToDO)

    @Update
    suspend fun update(todo : ToDO)

    @Delete
    suspend fun delete(todo: ToDO)


    @Query("SELECT * FROM todo WHERE id = :id ")
    fun getToDO(id : Int): Flow<ToDO>

    @Query("SELECT * FROM todo ORDER BY title ASC")
    fun getToDOs(): Flow<List<ToDO>>

    @Query("SELECT * FROM todo ORDER BY id ASC")
    fun getAllToDoAsc(): LiveData<List<ToDO>>

    @Query("Delete from todo")
   suspend fun deleteAll()

}