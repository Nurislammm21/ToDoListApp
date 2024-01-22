package com.example.frontproject.repository

import androidx.lifecycle.LiveData
import com.example.frontproject.data.local.ToDoDao
import com.example.frontproject.data.local.model.ToDO
import dagger.Provides
import dagger.hilt.android.components.ViewModelComponent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

interface ToDoRepository{
    suspend fun insert(todo : ToDO)
    suspend fun update(todo : ToDO)
    suspend fun delete(todo : ToDO)
    suspend fun getToDo(id : Int): Flow<ToDO>
    fun getToDOs(): Flow<List<ToDO>>
    fun getAllToDoAsc(): LiveData<List<ToDO>>
  suspend fun deleteAll()

}





class ToDoRepositoryImpl @Inject constructor(private val toDoDao: ToDoDao): ToDoRepository{
    override suspend fun insert(todo: ToDO) = withContext(Dispatchers.IO) {
        toDoDao.insert(todo)
    }

    override suspend fun update(todo: ToDO) = withContext(Dispatchers.IO) {
        toDoDao.update(todo)
    }

    override suspend fun delete(todo: ToDO) = withContext(Dispatchers.IO) {
        toDoDao.delete(todo)
    }

    override suspend fun getToDo(id: Int): Flow<ToDO> = withContext(Dispatchers.IO) {
        toDoDao.getToDO(id)
    }

    override fun getToDOs(): Flow<List<ToDO>> = toDoDao.getToDOs()

    override fun getAllToDoAsc(): LiveData<List<ToDO>> {
        return toDoDao.getAllToDoAsc()
    }

    override suspend fun deleteAll() {
        toDoDao.deleteAll()
    }


}