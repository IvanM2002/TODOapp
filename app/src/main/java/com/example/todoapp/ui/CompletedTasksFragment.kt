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
import com.example.todoapp.databinding.FragmentCompletedTasksBinding
import com.example.todoapp.model.Task
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CompletedTasksFragment : Fragment() {

    private val viewModel: TaskViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentCompletedTasksBinding.inflate(inflater, container, false)

        val adapter = TaskAdapter(viewModel, ::navigateToDetail)
        binding.recyclerViewCompletedTasks.adapter = adapter
        binding.recyclerViewCompletedTasks.layoutManager = LinearLayoutManager(requireContext())

        viewModel.uiState.observe(viewLifecycleOwner) { state ->
            // Actualiza la lista de tareas completadas
            adapter.submitList(state.completedTasks)
        }

        return binding.root
    }

    private fun navigateToDetail(task: Task) {
        val action = CompletedTasksFragmentDirections
            .actionCompletedTasksFragmentToTaskDetailFragment(task.description)
        findNavController().navigate(action)
    }
}
