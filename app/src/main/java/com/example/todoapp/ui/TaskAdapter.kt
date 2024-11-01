package com.example.todoapp.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.databinding.ItemTaskBinding
import com.example.todoapp.model.TaskEntity

class TaskAdapter(
    private val onTaskClick: (TaskEntity) -> Unit
) : ListAdapter<TaskEntity, TaskAdapter.TaskViewHolder>(TaskDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val binding = ItemTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TaskViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = getItem(position)
        holder.bind(task, onTaskClick)
    }

    class TaskViewHolder(private val binding: ItemTaskBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(task: TaskEntity, onTaskClick: (TaskEntity) -> Unit) {
            binding.textViewTask.text = task.description
            binding.checkBoxCompleted.isChecked = task.isCompleted

            binding.checkBoxCompleted.setOnCheckedChangeListener { _, isChecked ->
                onTaskClick(task.copy(isCompleted = isChecked))
            }

            binding.root.setOnClickListener {
                onTaskClick(task)
            }
        }
    }
}