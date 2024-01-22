package com.example.frontproject.UI

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MenuHost
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.frontproject.R
import com.example.frontproject.data.local.model.ToDO
import com.example.frontproject.databinding.FragmentEditToDoListBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch



@AndroidEntryPoint
class EditToDoListFragment : Fragment() {
       private var _binding : FragmentEditToDoListBinding? = null
    private val  binding get() = _binding!!
    lateinit var todo : ToDO
    private val viewModel: TodoListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditToDoListBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


            initLogic()
        val id = requireArguments().getInt("id")
        viewModel.retrieveToDo(id)

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.todo.collect{ selectedTodo ->
                selectedTodo?.let {
                    todo = selectedTodo
                    bind(todo)
                }
            }
        }


    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun initLogic() = with(binding){
        binding.toolbarEditTodo.deleteClick = {
            if (todoIsInitialized()) {
                viewModel.deleteToDo(todo)
            }
        }
        binding.toolbarEditTodo.saveClick = { saveEntry() }


    }

    private fun bind(todo : ToDO){ binding.apply {
        todoTitle.setText(todo.title)
        todoDescription.setText(todo.description)
    }
    }

    private fun saveEntry() = with(binding){
        val todoTitle = todoTitle.text.toString()
        val todoDescription = todoDescription.text.toString()

        if(viewModel.isEntryValid(todoTitle,todoDescription)){
            if(todoIsInitialized()){
                viewModel.updateToDO(todo.id,todoTitle,todoDescription)
            } else {
                viewModel.addNewToDo(todoTitle,todoDescription)
            }
        }
    }


    private fun todoIsInitialized() = ::todo.isInitialized
}