package com.example.todoapp.model

import androidx.room.*

@Dao
interface TaskDao {

    @Query("SELECT * FROM tasks WHERE isCompleted = 0")
    suspend fun getActiveTasks(): List<TaskEntity>

    @Query("SELECT * FROM tasks WHERE isCompleted = 1")
    suspend fun getCompletedTasks(): List<TaskEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: TaskEntity)

    @Update
    suspend fun updateTask(task: TaskEntity)

    @Delete
    suspend fun deleteTask(task: TaskEntity)
}
