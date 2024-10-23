package com.example.todoapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.todoapp.model.Task
import com.example.todoapp.model.TaskUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableLiveData(TaskUIState())
    val uiState: LiveData<TaskUIState> get() = _uiState

    fun addTask(description: String) {
        val currentState = _uiState.value ?: TaskUIState()
        val newTask = Task(description, false)
        _uiState.value = currentState.copy(tasks = currentState.tasks + newTask)
    }

    fun completeTasks(tasksToComplete: List<Task>) {
        val currentState = _uiState.value ?: TaskUIState()
        val updatedTasks = currentState.tasks.filter { it !in tasksToComplete }
        val updatedCompletedTasks = currentState.completedTasks + tasksToComplete
        _uiState.value = currentState.copy(
            tasks = updatedTasks,
            completedTasks = updatedCompletedTasks
        )
    }

    fun deleteTasks(tasksToDelete: List<Task>) {
        val currentState = _uiState.value ?: TaskUIState()
        val updatedTasks = currentState.tasks.filter { it !in tasksToDelete }
        val updatedCompletedTasks = currentState.completedTasks.filter { it !in tasksToDelete }
        _uiState.value = currentState.copy(
            tasks = updatedTasks,
            completedTasks = updatedCompletedTasks
        )
    }
}
