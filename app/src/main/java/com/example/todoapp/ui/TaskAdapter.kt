package com.example.todoapp.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.TaskViewModel
import com.example.todoapp.databinding.ItemTaskBinding
import com.example.todoapp.model.Task
import com.example.todoapp.ui.TaskDiffCallback

class TaskAdapter(
    private val viewModel: TaskViewModel,
    private val onTaskClick: (Task) -> Unit
) : ListAdapter<Task, TaskAdapter.TaskViewHolder>(TaskDiffCallback()) {

    private val selectedTasks = mutableListOf<Task>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val binding = ItemTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TaskViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = getItem(position)
        holder.bind(task, viewModel, onTaskClick, selectedTasks)
    }

    fun getSelectedTasks(): List<Task> {
        return selectedTasks.toList()
    }

    class TaskViewHolder(private val binding: ItemTaskBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(task: Task, viewModel: TaskViewModel, onTaskClick: (Task) -> Unit, selectedTasks: MutableList<Task>) {
            binding.textViewTask.text = task.description
            binding.checkBoxCompleted.isChecked = selectedTasks.contains(task)

            binding.checkBoxCompleted.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    selectedTasks.add(task)
                } else {
                    selectedTasks.remove(task)
                }
            }

            binding.root.setOnClickListener {
                onTaskClick(task)
            }
        }
    }
}
