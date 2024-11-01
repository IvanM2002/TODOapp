package com.example.todoapp.model

data class TaskUIState(
    val tasks: List<TaskEntity> = emptyList(),
    val completedTasks: List<TaskEntity> = emptyList()
)
