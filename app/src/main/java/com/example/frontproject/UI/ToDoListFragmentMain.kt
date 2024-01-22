package com.example.frontproject.UI

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.frontproject.UI.adapters.ToDoAdapter
import com.example.frontproject.databinding.FragmentToDoListMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class ToDoListFragmentMain : Fragment(), SearchView.OnQueryTextListener {
    private var _binding : FragmentToDoListMainBinding? = null
    private val  binding get() = _binding!!
    private val viewModel : TodoListViewModel by viewModels()
    private var _recyclerView: RecyclerView? = null
    private val recyclerView get() = _recyclerView!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentToDoListMainBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRcView()
        fabClick()

        binding.searchView.setOnQueryTextListener(this)
        binding.deleteAllBtn.setOnClickListener {
           viewModel.clear()
        }
    }



    private fun initRcView() = with(binding){
        val adapter = ToDoAdapter{
            val action = ToDoListFragmentMainDirections.actionToDoListFragmentMainToEditToDoListFragment()
            val bundle = bundleOf("id" to it.id)
            findNavController().navigate(action.actionId,bundle)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.todoS.collect{
                adapter.submitList(it)
            }
        }

        rcView.adapter = adapter
        rcView.layoutManager = StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL)
        rcView.setHasFixedSize(true)

    }

   private fun fabClick(){
        binding.floatingActionButton.setOnClickListener{
            val action = ToDoListFragmentMainDirections.actionToDoListFragmentMainToEditToDoListFragment()
            findNavController().navigate(action)
        }
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        viewModel.onSearchQueryChanged(query)
        return true
    }

    override fun onQueryTextChange(query: String?): Boolean {
        viewModel.onSearchQueryChanged(query)
        return true
    }

}