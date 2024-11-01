package com.example.todoapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.model.TaskDao
import com.example.todoapp.model.TaskEntity
import com.example.todoapp.model.TaskUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val taskDao: TaskDao
) : ViewModel() {

    private val _uiState = MutableLiveData(TaskUIState())
    val uiState: LiveData<TaskUIState> get() = _uiState

    init {
        loadTasks()
    }

    private fun loadTasks() {
        viewModelScope.launch {
            val activeTasks = taskDao.getActiveTasks()
            val completedTasks = taskDao.getCompletedTasks()
            _uiState.value = TaskUIState(
                tasks = activeTasks,
                completedTasks = completedTasks
            )
        }
    }

    fun addTask(description: String) {
        viewModelScope.launch {
            val newTask = TaskEntity(description = description, isCompleted = false)
            taskDao.insertTask(newTask)
            loadTasks()
        }
    }

    fun completeTasks(tasksToComplete: List<TaskEntity>) {
        viewModelScope.launch {
            tasksToComplete.forEach { task ->
                val updatedTask = task.copy(isCompleted = true)
                taskDao.updateTask(updatedTask)
            }
            loadTasks()
        }
    }

    fun deleteTasks(tasksToDelete: List<TaskEntity>) {
        viewModelScope.launch {
            tasksToDelete.forEach { taskDao.deleteTask(it) }
            loadTasks()
        }
    }
}
