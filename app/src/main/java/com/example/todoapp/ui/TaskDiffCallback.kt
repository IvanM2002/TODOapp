package com.example.todoapp.ui

import androidx.recyclerview.widget.DiffUtil
import com.example.todoapp.model.Task

class TaskDiffCallback : DiffUtil.ItemCallback<Task>() {

    override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
        return oldItem.description == newItem.description
    }

    override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
        return oldItem == newItem
    }
}
