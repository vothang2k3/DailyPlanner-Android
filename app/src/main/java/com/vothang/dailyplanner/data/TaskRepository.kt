package com.vothang.dailyplanner.data

import com.vothang.dailyplanner.model.Task
import kotlinx.coroutines.flow.Flow


interface TaskRepository {
    // Trả về Flow để lắng nghe danh sách Task trả về liên tục
    fun getTask(): Flow<List<Task>>

    suspend fun insertTask(task: Task)
    suspend fun updateTask(task: Task)
    suspend fun deleteTask(task: Task)
}
