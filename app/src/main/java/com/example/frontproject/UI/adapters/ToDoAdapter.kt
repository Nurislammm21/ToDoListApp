package com.example.frontproject.UI.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.frontproject.data.local.model.ToDO
import com.example.frontproject.databinding.TodoListItemBinding


class ToDoAdapter(private val onItemClicked: (ToDO) -> Unit) : ListAdapter<ToDO,ToDoAdapter.ToDoViewHolder>(
    DIFF_CALLBACK) {

    inner class ToDoViewHolder(private val binding: TodoListItemBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(todo: ToDO) = with(binding){
            titleItem.text = todo.title
            descItem.text = todo.description
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoViewHolder {
       return ToDoViewHolder(TodoListItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ToDoViewHolder, position: Int) {
        val todo = currentList[position]
        holder.bind(todo)
        holder.itemView.setOnClickListener{
            onItemClicked(todo)
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ToDO>() {
            override fun areItemsTheSame(oldItem: ToDO, newItem: ToDO): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: ToDO, newItem: ToDO): Boolean {
                return oldItem == newItem
            }
        }
    }


}