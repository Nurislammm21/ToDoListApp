package com.example.frontproject.UI

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.frontproject.UI.adapters.ToDoItem
import com.example.frontproject.data.local.model.ToDO
import com.example.frontproject.repository.ToDoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoListViewModel @Inject constructor(private val toDoRepository: ToDoRepository) : ViewModel() {
    val todoS: Flow<List<ToDO>> = toDoRepository.getToDOs()
    val todo = MutableStateFlow<ToDO?>(null)

    private val allToDo: LiveData<List<ToDO>> = toDoRepository.getAllToDoAsc()

    private val _todos = MediatorLiveData<List<ToDoItem>>().apply {
        addSource(allToDo) { notes ->
            val noteItems = notes.map { todo ->
                // Convert NotesEntity to NoteItem
                ToDoItem(todo.id, todo.title, todo.description)
            }
            postValue(noteItems)
        }
    }
    val todos: LiveData<List<ToDoItem>> = _todos

    private fun insertToDo(todo: ToDO) {
        viewModelScope.launch {
            toDoRepository.insert(todo)
        }
    }

    private fun getNewToDoEntry(toDoTitle: String, todoDescription: String): ToDO{
        return ToDO(
            title = toDoTitle,
            description = todoDescription
        )
    }

     fun addNewToDo(toDoTitle: String, todoDescription: String){
        val newToDo = getNewToDoEntry(toDoTitle,todoDescription)
        insertToDo(newToDo)

    }

    private fun updateToDo(todo: ToDO){
        viewModelScope.launch{
            toDoRepository.update(todo)
        }
    }


    private fun getUpdateToDoEntry(todoId: Int, toDoTitle: String, todoDescription: String): ToDO{
        return ToDO(
            id = todoId,
            title = toDoTitle,
            description = todoDescription
        )
    }

    fun updateToDO(todoId: Int, toDoTitle: String, todoDescription: String){
        val updateTodo = getUpdateToDoEntry(todoId,toDoTitle,todoDescription)
        updateToDo(updateTodo)

    }

    fun deleteToDo(todo: ToDO){
        viewModelScope.launch {
            toDoRepository.delete(todo)
        }
    }


    fun retrieveToDo(id: Int){
        viewModelScope.launch {
            toDoRepository.getToDo(id).collect{
                todo.value = it
            }
        }
    }

      fun clear(){
       viewModelScope.launch {
           toDoRepository.deleteAll()
       }
    }

    fun isEntryValid(toDoTitle: String, todoDescription: String): Boolean{
        return !(toDoTitle.isBlank() || todoDescription.isBlank())
    }



    fun onSearchQueryChanged(query: String?) {
        val filteredNotes = allToDo.value?.filter { todo ->
            todo.title.contains(query.toString(), ignoreCase = true) || todo.description.contains(query.toString(), ignoreCase = true)
        }?.map { note ->
            // Convert NotesEntity to NoteItem
            ToDoItem(note.id, note.title, note.description)
        }
        filteredNotes?.let {
            _todos.postValue(it)
        }
    }

}