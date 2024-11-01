package com.example.todoapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todoapp.databinding.FragmentTaskListBinding
import com.example.todoapp.TaskViewModel
import com.example.todoapp.model.TaskEntity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TaskListFragment : Fragment() {

    private val viewModel: TaskViewModel by viewModels()
    private lateinit var binding: FragmentTaskListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTaskListBinding.inflate(inflater, container, false)

        val adapter = TaskAdapter(::navigateToDetail)
        binding.recyclerViewTasks.adapter = adapter
        binding.recyclerViewTasks.layoutManager = LinearLayoutManager(requireContext())

        viewModel.uiState.observe(viewLifecycleOwner) { state ->
            adapter.submitList(state.tasks)
        }

        binding.buttonAddTask.setOnClickListener {
            val taskDescription = binding.editTextTask.text.toString()
            if (taskDescription.isNotEmpty()) {
                viewModel.addTask(taskDescription)
                binding.editTextTask.text.clear()
            }
        }

        return binding.root
    }

    private fun navigateToDetail(task: TaskEntity) {
        val action = TaskListFragmentDirections
            .actionTaskListFragmentToTaskDetailFragment(task.description)
        findNavController().navigate(action)
    }
}
