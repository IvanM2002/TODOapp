package com.example.todoapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todoapp.TaskViewModel
import com.example.todoapp.databinding.FragmentTaskListBinding
import com.example.todoapp.model.Task
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TaskListFragment : Fragment() {

    private val viewModel: TaskViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentTaskListBinding.inflate(inflater, container, false)

        val adapter = TaskAdapter(viewModel, ::navigateToDetail)
        binding.recyclerViewTasks.adapter = adapter
        binding.recyclerViewTasks.layoutManager = LinearLayoutManager(requireContext())

        viewModel.uiState.observe(viewLifecycleOwner) { state ->
            adapter.submitList(state.tasks)
        }

        binding.buttonAddTask.setOnClickListener {
            val task = binding.editTextTask.text.toString()
            if (task.isNotEmpty()) {
                viewModel.addTask(task)
                binding.editTextTask.text.clear()
            }
        }

        binding.buttonCompleteSelected.setOnClickListener {
            val selectedTasks = adapter.getSelectedTasks()
            if (selectedTasks.isNotEmpty()) {
                viewModel.completeTasks(selectedTasks)
            }
        }

        binding.buttonDeleteSelected.setOnClickListener {
            val selectedTasks = adapter.getSelectedTasks()
            if (selectedTasks.isNotEmpty()) {
                viewModel.deleteTasks(selectedTasks)
            }
        }

        binding.buttonCompletedTasks.setOnClickListener {
            val action = TaskListFragmentDirections
                .actionTaskListFragmentToCompletedTasksFragment()
            findNavController().navigate(action)
        }

        return binding.root
    }

    private fun navigateToDetail(task: Task) {
        val action = TaskListFragmentDirections
            .actionTaskListFragmentToTaskDetailFragment(task.description)
        findNavController().navigate(action)
    }
}
