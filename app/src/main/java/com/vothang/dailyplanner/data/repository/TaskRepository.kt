package com.vothang.dailyplanner.data.repository

import com.vothang.dailyplanner.model.Task
import com.vothang.dailyplanner.model.TaskList
import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    // Trả về Flow để lắng nghe danh sách Task trả về liên tục
    fun getTasks(): Flow<List<Task>>
    fun getTasksByListId(listId: Int): Flow<List<Task>>

    suspend fun insertTask(task: Task)
    suspend fun updateTask(task: Task)
    suspend fun deleteTask(task: Task)
}