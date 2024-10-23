package com.example.todoapp.model

data class TaskUIState(
    val tasks: List<Task> = emptyList(),
    val completedTasks: List<Task> = emptyList()
)
